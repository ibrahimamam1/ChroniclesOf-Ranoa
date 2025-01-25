package monster;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import Object.OBJ_Bronze_Coin;
import Object.OBJ_Red_Potion;
import entity.Entity;
import entity.Fireball;
import game.GamePanel;

public class Mon_Orc extends Entity{
    
  public Mon_Orc(GamePanel gp) {

    super(gp);
    
    name = "Orc";
    speed = 1;
    maxlife = 15;
    life = maxlife;
    attack = 8;
    defense = 2;
    exp = 10;
    type = entityType.MONSTER;

    solidArea.x = 4;
    solidArea.y =  4;
    solidArea.width = 40;
    solidArea.height = 44;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    attackArea.width = 48;
    attackArea.height = 48;

    getImage();
    getAttackImages();

  }

  public void getImage() {

    up1 = setup("/assets/Monster/orc_up_1" , gp.tileSize , gp.tileSize);
    up2 = setup("/assets/Monster/orc_up_2" , gp.tileSize , gp.tileSize);
    left1 = setup("/assets/Monster/orc_left_1" , gp.tileSize , gp.tileSize);
    left2 = setup("/assets/Monster/orc_left_2" , gp.tileSize , gp.tileSize);
    down1 = setup("/assets/Monster/orc_down_1" , gp.tileSize , gp.tileSize);
    down2 = setup("/assets/Monster/orc_down_2" , gp.tileSize , gp.tileSize);
    right1 = setup("/assets/Monster/orc_right_1" , gp.tileSize , gp.tileSize);
    right2 = setup("/assets/Monster/orc_right_2" , gp.tileSize , gp.tileSize);

  }

  public void getAttackImages() {
    attack_up1 = setup("/assets/Monster/orc_attack_up_1", gp.tileSize * 2, gp.tileSize * 2);
    attack_up2 = setup("/assets/Monster/orc_attack_up_2", gp.tileSize * 2, gp.tileSize * 2);
    attack_down1 = setup("/assets/Monster/orc_attack_down_1", gp.tileSize * 2, gp.tileSize * 2);
    attack_down2 = setup("/assets/Monster/orc_attack_down_2", gp.tileSize * 2, gp.tileSize * 2);
    attack_left1 = setup("/assets/Monster/orc_attack_left_1", gp.tileSize * 2, gp.tileSize * 2);
    attack_left2 = setup("/assets/Monster/orc_attack_left_2", gp.tileSize * 2, gp.tileSize * 2);
    attack_right1 = setup("/assets/Monster/orc_attack_right_1", gp.tileSize * 2, gp.tileSize * 2);
    attack_right2 = setup("/assets/Monster/orc_attack_right_2", gp.tileSize * 2, gp.tileSize * 2);
  }

  public void setAction() {

    if(attacking == false) {
        checkAttack(30, gp.tileSize*4, gp.tileSize);
    }
    if(onPath == true) {
      
      checkStopMonsterAggro(gp.player, 15, 100);
      searchPath(getGoalCol(gp.player) , getGoalRow(gp.player));
    }
    else {

      checkStartMonsterAggro(gp.player, 5);
      getRandomDirection();  
    }
  }

  public void damageReaction() {

    actionLockCounter = 0;
    onPath = true;

  }

  public void checkDrop() {

    int i = new Random().nextInt(100) + 1;

    if(i <= 80) {
      dropItem(new OBJ_Bronze_Coin(gp));
    }
    if(i >= 80) {
      dropItem(new OBJ_Red_Potion(gp));
    }
  }

}


