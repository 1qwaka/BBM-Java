import java.util.Locale.Category;

import content.ModBlocks;
import mindustry.content.Items;
import mindustry.mod.Mod;
import mindustry.type.ItemStack;

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
