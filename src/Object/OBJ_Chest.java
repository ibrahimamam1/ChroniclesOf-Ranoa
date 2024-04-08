package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_Chest extends Entity{
  GamePanel gp;
  public OBJ_Chest(GamePanel gp) {
    super(gp);
    name = "Chest";
    image  = setup("/assets/Object/chest");
  }
}
