import content.ModBlocks;
import mindustry.mod.Mod;

public class ModLoad extends Mod{
	public ModLoad(){
		
	}
	@Override
	public void loadContent(){
		super.loadContent();
		ModBlocks mb = new ModBlocks();
		mb.load();
	}
}
