package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_Bronze_Coin extends Entity{

  public OBJ_Bronze_Coin(GamePanel gp) {
    
    super(gp);
    name = "Bronze Coin";
    down1 = setup("/assets/Object/coin_bronze", gp.tileSize, gp.tileSize);
    description = "[" + name + "]\n Allow Player Buy Things";
    direction = "down";
    type = entityType.PICKUP_ONLY;
    value = 1;
  }

  public boolean use(Entity user) {
    gp.playSoundEffect(1);
    user.coin += value;
    return true;
  }
  
}
