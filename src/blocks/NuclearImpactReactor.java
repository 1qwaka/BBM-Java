package blocks;

import arc.Events;
import arc.math.Mathf;
import content.FxMod;
import mindustry.Vars;
import mindustry.entities.Damage;
import mindustry.game.EventType;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.blocks.power.ImpactReactor;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.consumers.ConsumeType;

public class NuclearImpactReactor extends ImpactReactor {


    public float heating = 0.01f;
    public int itemDuration = 240;
    public final int timerFuel = timers++;
    public float coolantPower = 0.5f;
    public float smokeThreshold = 0.3f;
    public int explosionDamage = 2000;
    public int explosionRadius = 40;


    public NuclearImpactReactor(String name) {
        super(name);
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("heat", (NuclearImpactReactorBuild e) -> new Bar("bar.heat", Pal.accent, () -> e.heat));
    }

    public class NuclearImpactReactorBuild extends ImpactReactorBuild{
        public float heat;

        @Override
        public void update() {
            super.update();
            ConsumeLiquid consumeLiquid = consumes.get(ConsumeType.liquid);
            Item item = consumes.getItem().items[0].item;
            int fuel = items.get(item);
            float t = size * 8 / 2f;
            float fullness = (float) fuel / itemCapacity;

            if(fuel > 0 && enabled){
                heat += fullness * heating * Math.min(delta(), 4f);

                if(timer(timerFuel, itemDuration / timeScale)){
                    consume();
                }
            }
            else {
                productionEfficiency = 0f;
            }
            Liquid liquid = consumeLiquid.liquid;
            if(heat > 0){
                float maxUsed = Math.min(liquids.get(liquid), heat / coolantPower);
                heat -= maxUsed * coolantPower;
                liquids.remove(liquid, maxUsed);
            }
            if(heat > smokeThreshold){
                float smoke = 1.0f + (heat - smokeThreshold) / (1f - smokeThreshold); //ranges from 1.0 to 2.0
                if(Mathf.chance(smoke / 20.0 * delta())){

                }
            }

            heat = Mathf.clamp(heat);

            if(heat >= 0.999f){
                Events.fire(EventType.Trigger.thoriumReactorOverheat);
                kill();
                FxMod.nucImpSmoEffect.at(x + t, y + t);
            }
        }
        @Override
        public void onDestroyed(){
            super.onDestroyed();
            Sounds.explosionbig.at(tile);

            int fuel = items.get(consumes.<ConsumeItems>get(ConsumeType.item).items[0].item);

            if((fuel < 5 && heat < 0.5f) && !Vars.state.rules.reactorExplosions) return;

            Damage.damage(x, y, explosionRadius * size*8, explosionDamage * 4);

            FxMod.nucImpDesEffect.at(x, y);
        }
    }
}
