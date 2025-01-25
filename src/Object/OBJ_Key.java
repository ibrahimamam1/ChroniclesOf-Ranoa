package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_Key extends Entity{

  public OBJ_Key(GamePanel gp) {
    super(gp);
    name = "Key";
    type = entityType.CONSUMABLE;
    image = setup("/assets/Object/key" , gp.tileSize , gp.tileSize);
    description = "[" + name + "]\n This Opens a Door";
    stackable = true;
  }

  
}
