package monster;

import java.util.Random;

import entity.Entity;
import game.GamePanel;

public class Mon_Green_Slime extends Entity{

  public Mon_Green_Slime(GamePanel gp) {
    super(gp);
    
    name = "Green Slime";
    speed = 1;
    maxlife = 3;
    life = maxlife;
    type = entityType.Monster;

    solidArea.x = 3;
    solidArea.y =  18;
    solidArea.width = 42;
    solidArea.height = 30;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    getImage();
  }

  public void getImage() {
    up1 = setup("/assets/Monster/greenslime_down_1");
    up2 = setup("/assets/Monster/greenslime_down_2");
    left1 = setup("/assets/Monster/greenslime_down_1");
    left2 = setup("/assets/Monster/greenslime_down_2");
    down1 = setup("/assets/Monster/greenslime_down_1");
    down2 = setup("/assets/Monster/greenslime_down_2");
    right1 = setup("/assets/Monster/greenslime_down_1");
    right2 = setup("/assets/Monster/greenslime_down_2");
  }

  public void setAction() {
    actionLockCounter++;
    if(actionLockCounter == 120) {
      Random random = new Random();
      int i = random.nextInt(100)+1;

      if(i <= 25) {
        direction = "up";
      }
      else if(i > 25 && i <= 50) {
        direction = "down";
      }
      else if(i > 50 && i <= 75) {
        direction = "left";
      }
      else if(i > 75) {
        direction = "right";
      }
      actionLockCounter = 0;
    }
    
  }
  
}
