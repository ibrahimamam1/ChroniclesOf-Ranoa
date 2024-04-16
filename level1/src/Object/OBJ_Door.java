package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_Door extends Entity{
  GamePanel gp;
  public OBJ_Door(GamePanel gp) {
    super(gp);
    name = "Door";
    image = setup("/assets/Object/door" , gp.tileSize , gp.tileSize);
  }
}
