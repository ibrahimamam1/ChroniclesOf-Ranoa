package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_Red_Potion extends Entity{


  public OBJ_Red_Potion(GamePanel gp) {
    
    super(gp);
    name = "Red Potion";
    image = setup("/assets/Object/potion_red", gp.tileSize, gp.tileSize);
    down1 = setup("/assets/Object/potion_red", gp.tileSize, gp.tileSize);
    description = "[" + name + "]\n SlightLy Restore Hp";
    direction = "down";
    type = entityType.CONSUMABLE;
    value = 2;
    stackable = true;

  }

  public boolean use(Entity entity) {

    entity.life += value;
    if(gp.player.life > gp.player.maxlife) {
      gp.player.life = gp.player.maxlife;
    }
    gp.playSoundEffect(2);
    return true;
  }
  
}
