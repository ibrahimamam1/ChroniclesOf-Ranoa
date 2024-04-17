package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_Hearth extends Entity{

  GamePanel gp;

  public OBJ_Hearth(GamePanel gp) {
    super(gp);
    name = "Hearth";
    image = setup("/assets/Object/heart_blank" , gp.tileSize , gp.tileSize);
    image2 = setup("/assets/Object/heart_half" , gp.tileSize , gp.tileSize);
    image3 = setup("/assets/Object/heart_full" , gp.tileSize , gp.tileSize);
    
  }
  
}
