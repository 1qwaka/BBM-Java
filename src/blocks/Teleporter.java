package blocks;

import java.util.Arrays;

import arc.struct.Seq;
import arc.util.Log;
import mindustry.gen.Building;
import mindustry.world.Block;

public class Teleporter extends Block{

	public Teleporter(String name) {
		super(name);
		config(Integer.class, (TelepolterBuild tile, Integer point) ->{
			tile.addLink(point);
		});
		  };
	public class TelepolterBuild extends Building{
		public Integer link;
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
		public void update(){
			Log.info(link);
			Log.info(Arrays.toString(b.items));
	}
	}
}
