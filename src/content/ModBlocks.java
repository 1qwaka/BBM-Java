package content;


import blocks.Teleporter;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;

public class ModBlocks implements ContentList{
	public static Block teleporter;
	@Override
	public void load() {
		Teleporter teleporter = new Teleporter("teleporter") {{
			health = 100;
			update = true;
			requirements(Category.distribution, ItemStack.with(Items.phaseFabric, 5, Items.silicon, 7, Items.lead, 10, Items.graphite, 10));
		}};
		
	}

}
