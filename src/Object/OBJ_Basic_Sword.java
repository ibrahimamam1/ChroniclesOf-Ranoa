package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_Basic_Sword extends Entity{

  public OBJ_Basic_Sword(GamePanel gp) {
    
    super(gp);
    name = "Basic Sword";
    image = setup("/assets/Object/sword_normal", gp.tileSize, gp.tileSize);
    attack = 1;
    defense = 1;
    description = "[" + name + "]\n Basic Knight Sword";
    type = entityType.SWORD;
  }
  
}
