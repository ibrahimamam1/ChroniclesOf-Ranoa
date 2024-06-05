package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_King_Sword extends Entity{

  public OBJ_King_Sword(GamePanel gp) {
    
    super(gp);
    name = "Red King Sword";
    image = setup("/assets/Object/sword_normal", gp.tileSize, gp.tileSize);
    down1 = setup("/assets/Object/sword_normal", gp.tileSize, gp.tileSize);
    attack = 5;
    defense = 1;
    description = "[" + name + "]\n A king once used this Sword\nto save the land";
    direction = "down";
    type = entityType.SWORD;
  }

}
