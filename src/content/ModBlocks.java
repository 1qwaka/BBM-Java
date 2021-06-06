package content;

import blocks.Teleporter;
import mindustry.ctype.ContentList;

public class ModBlocks implements ContentList{

	@Override
	public void load() {
		Teleporter teleporter = new Teleporter("teleporter") {{
			health = 100;
			update = true;
		}};
		
	}

}
