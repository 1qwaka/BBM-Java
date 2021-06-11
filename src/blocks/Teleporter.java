package blocks;


import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.world.Block;

public class Teleporter extends Block {
	public int linkRotation = 0;
	public Teleporter(String name) {
		super(name);
		config(Integer.class, (TelepolterBuild tile, Integer point) ->{
			tile.addLink(point);
		});
		  };
	public class TelepolterBuild extends Building {
		Seq<Integer> b = new Seq<Integer>();
		public void addLink(int point){
			if(b.contains(point)){
				b.remove(b.indexOf(point));
				return;
			}
			b.add(point);
		}
		
		@Override
		public boolean onConfigureTileTapped(Building other) {
			if(this.pos() != other.pos()){
				this.configure(other.pos());
				return false;
			} else if(this.block.name == other.block.name) {
				return false;
			}
				return true;
		}
		@Override
		public void update() {
			for (int i = 0; i < b.size; i++) {
				linkRotation = (linkRotation + 1) % b.size;
				Building build = Vars.world.build(b.get(linkRotation));
				if(items.first() != null) {
					Log.info(build.items.first());
					Log.info(items.get(build.items.first()));
				} else { Log.info("Null");}
				if (items.first() != null && build != null) {
					if (build.acceptItem(this, items.first())) {
						build.items.add(items.first(), 1);
						items.remove(items.first(), 1);
                                                Log.info("True");
					}
				}
			}
		}
		@Override
		public void drawConfigure(){
			try{
				super.drawConfigure();
				float sin = Mathf.absin(Time.time, 6, 1);
				Drawf.circles(this.x, this.y, (this.block.size / 2 + 1) * Vars.tilesize + sin - 2);
				for(int i = 0; i< b.size; i++) {
					Building build = Vars.world.build(b.get(i));
					Drawf.circles(build.x, build.y, (build.block.size / 2 + 1) * Vars.tilesize + sin - 2, Pal.place);
				}
			} catch (Exception e){

			}

		}
		@Override
		public void handleItem(Building source, Item item) {
			if (b.size == 0) {
				super.handleItem(source, item);
				return;
			}
			Vars.world.build(b.get(linkRotation)).handleItem(this, item);
			linkRotation = (linkRotation + 1) % b.size;
		}

		@Override
		public boolean acceptItem(Building source, Item item){
			return items.get(item) < getMaximumAccepted(item);
		}
	}
}
