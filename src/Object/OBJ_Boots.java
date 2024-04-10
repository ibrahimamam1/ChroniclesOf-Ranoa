package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_Boots extends Entity{
  GamePanel gp;
  public OBJ_Boots(GamePanel gp) {
    super(gp);
    name = "Boots";
    image = setup("/assets/Object/boots" , gp.tileSize , gp.tileSize);
  }
}
