package content;


import blocks.NuclearImpactReactor;
import blocks.Teleporter;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;

public class ModBlocks implements ContentList{
	public static Block teleporter, nuclearImpactReactor;
	@Override
	public void load() {
		Teleporter teleporter = new Teleporter("teleporter") {{
			health = 100;
			update = true;
			hasItems = true;
			configurable = true;
			itemCapacity = 100;
			requirements(Category.distribution, ItemStack.with(Items.phaseFabric, 5, Items.silicon, 7, Items.lead, 10, Items.graphite, 10));
		}};
		NuclearImpactReactor nuclearImpactReactor = new NuclearImpactReactor("nuclearImpactReactor") {{
			health = 1000;
			consumes.item(Items.thorium);
			consumes.power(12);
			requirements(Category.power, ItemStack.with(Items.phaseFabric, 5, Items.silicon, 7, Items.lead, 10, Items.graphite, 10));
			consumes.liquid(Liquids.cryofluid, heating / coolantPower).update(false);
			powerProduction = 15;
			size = 6;
		}};
	}

}
