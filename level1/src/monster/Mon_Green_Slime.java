package monster;

import java.util.Random;

import Object.OBJ_Bronze_Coin;
import Object.OBJ_Red_Potion;
import entity.Entity;
import entity.Fireball;
import game.GamePanel;

public class Mon_Green_Slime extends Entity{

  public Mon_Green_Slime(GamePanel gp) {
    super(gp);
    
    name = "Green Slime";
    speed = 1;
    maxlife = 3;
    life = maxlife;
    attack = 5;
    defense = 0;
    exp = 2;
    type = entityType.MONSTER;
    projectile = new Fireball(gp);

    solidArea.x = 3;
    solidArea.y =  18;
    solidArea.width = 42;
    solidArea.height = 30;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    getImage();
  }

  public void getImage() {
    up1 = setup("/assets/Monster/greenslime_down_1" , gp.tileSize , gp.tileSize);
    up2 = setup("/assets/Monster/greenslime_down_2" , gp.tileSize , gp.tileSize);
    left1 = setup("/assets/Monster/greenslime_down_1" , gp.tileSize , gp.tileSize);
    left2 = setup("/assets/Monster/greenslime_down_2" , gp.tileSize , gp.tileSize);
    down1 = setup("/assets/Monster/greenslime_down_1" , gp.tileSize , gp.tileSize);
    down2 = setup("/assets/Monster/greenslime_down_2" , gp.tileSize , gp.tileSize);
    right1 = setup("/assets/Monster/greenslime_down_1" , gp.tileSize , gp.tileSize);
    right2 = setup("/assets/Monster/greenslime_down_2" , gp.tileSize , gp.tileSize);
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

    int i = new Random().nextInt(100)+1;
    if(i > 99 && projectile.alive == false && shotAvailableCounter == 30) {
      projectile.set(worldX, worldY, direction, true, this);
      gp.projectileList.add(projectile);
      shotAvailableCounter = 0;
    }
    
  }
  public void damageReaction() {
    actionLockCounter = 0;
    direction = gp.player.direction;
  }

  public void checkDrop() {
    int i = new Random().nextInt(100) + 1;

    if(i < 50) {
      dropItem(new OBJ_Bronze_Coin(gp));
    }
    if(i >= 50) {
      dropItem(new OBJ_Red_Potion(gp));
    }
  }
  
}