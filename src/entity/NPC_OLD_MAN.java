package entity;

import java.util.Random;

import game.GamePanel;

public class NPC_OLD_MAN extends Entity{

  public NPC_OLD_MAN(GamePanel gp) {
    super(gp);
    direction = "down";
    speed = 1;
    getImage();
  }

  public void getImage(){
   
    up1 = setup("/assets/NPC/oldman_up_1");
    up2 = setup("/assets/NPC/oldman_up_2");
    down1 = setup("/assets/NPC/oldman_down_1");
    down2 = setup("/assets/NPC/oldman_down_2");
    left1 = setup("/assets/NPC/oldman_left_1");
    left2 = setup("/assets/NPC/oldman_left_2");
    right1 = setup("/assets/NPC/oldman_right_1");
    right2 = setup("/assets/NPC/oldman_right_2");
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