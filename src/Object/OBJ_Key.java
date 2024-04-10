package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_Key extends Entity{
  GamePanel gp;
  public OBJ_Key(GamePanel gp) {
    super(gp);
    name = "Key";
    image = setup("/assets/Object/key.png" , gp.tileSize , gp.tileSize);
  }
}
