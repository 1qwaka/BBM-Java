package blocks;

import arc.math.Mathf; 
import arc.struct.Seq; 
import arc.util.Time;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.Draw;
import mindustry.Vars; 
import mindustry.gen.Building; 
import mindustry.graphics.Drawf; 
import mindustry.graphics.Pal; 
import mindustry.type.Item; 
import mindustry.world.Block;
 
 
public class Teleporter extends Block { 
 public Teleporter(String name) { 
 super(name); 
 config(Integer.class, (TeleporterBuild tile, Integer point) -> tile.addLink(point)); 
 } 
 
 public class TeleporterBuild extends Building { 
 public int linkRotation = 0; 
 Seq<Integer> b = new Seq<>(); 
 
 public void addLink(int point) { 
 if (b.contains(point)) { 
 b.remove(b.indexOf(point)); 
 return; 
 } 
 b.add(point); 
 } 
 
 @Override
 public boolean onConfigureTileTapped(Building other) { 
 if (this.pos() != other.pos()) { 
 this.configure(other.pos()); 
 return false; 
 } else if (this.block.name.equals(other.block.name)) { 
 return false; 
 } 
 return true; 
 } 
 
 @Override
 public void update() { 
// if(items.first()!=null)print(items.first(),items.get(items.first())); 
// print("proximity:", proximity.size); 
// print("links:", b.size); 
 if(b.size == 0){ 
 int size = Math.min(Mathf.ceil(items.total()/4f), 4); 
 for(int i = 0; i < size; i++){ 
 if(!dump(null)) break; 
 } 
 return; 
 } 
 if(items.first() == null){ 
 return; 
 } 
 
 int rot = this.linkRotation; 
 for (int i = 0; i < b.size; i++) { 
 Building build = Vars.world.build(b.get((i + rot) % b.size));
 if(build != null && items.first() != null){ 
 if (build.acceptItem(this, items.first())) { 
 build.handleItem(this, items.first()); 
 items.remove(items.first(),1); 
 linkRotation = (linkRotation+1)%b.size;
}
 } 
 } 
 linkRotation = (linkRotation+1)%b.size; 
 
 } 
 
 @Override
 public void drawConfigure() { 
 try { 
 super.drawConfigure(); 
 float sin = Mathf.absin(Time.time, 3, 1); 
 Draw.color(Pal.accent); 
 Lines.square(this.x, this.y, (this.block.size / 2f + 1) * Vars.tilesize + sin - 2); 
 for (int i = 0; i < b.size; i++) { 
 Building build = Vars.world.build(b.get(i));
 Draw.color(Pal.place);
 Lines.square(build.x, build.y, (build.block.size / 2f + 1) * Vars.tilesize + sin - 2); 
 } 
 } catch (Exception e) { 
 
 } 
 
 } 
 
 @Override
 public void handleItem(Building source, Item item) { 
 if(b.size == 0 || this == source){ 
 items.add(item,1); 
 return; 
 } 
 if(Vars.world.build(b.get(linkRotation%b.size)).acceptItem(source, item)){ 
 Vars.world.build(b.get(linkRotation%b.size)).handleItem(source instanceof TeleporterBuild ? source : this, item); 
 } else { 
 items.add(item,1); 
 } 
 linkRotation = (linkRotation+1)%b.size; 
 } 
 
 @Override
 public boolean acceptItem(Building source, Item item) { 
// print("acceptItem:", item, items.get(item)); 
 return items.get(item) < getMaximumAccepted(item); 
 } 
 } 
}
