package monster;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import Object.OBJ_Bronze_Coin;
import Object.OBJ_Red_Potion;
import entity.Entity;
import entity.Fireball;
import game.GamePanel;

public class Mon_Skeleton extends Entity{
    
  public Mon_Skeleton(GamePanel gp) {

    super(gp);
    
    name = "Skeleton";
    speed = 2;
    maxlife = 50;
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

    up1 = setup("/assets/Monster/skeletonlord_up_1" , gp.tileSize , gp.tileSize);
    up2 = setup("/assets/Monster/skeletonlord_up_2" , gp.tileSize , gp.tileSize);
    left1 = setup("/assets/Monster/skeletonlord_left_1" , gp.tileSize , gp.tileSize);
    left2 = setup("/assets/Monster/skeletonlord_left_2" , gp.tileSize , gp.tileSize);
    down1 = setup("/assets/Monster/skeletonlord_down_1" , gp.tileSize , gp.tileSize);
    down2 = setup("/assets/Monster/skeletonlord_down_2" , gp.tileSize , gp.tileSize);
    right1 = setup("/assets/Monster/skeletonlord_right_1" , gp.tileSize , gp.tileSize);
    right2 = setup("/assets/Monster/skeletonlord_right_2" , gp.tileSize , gp.tileSize);

  }

  public void getAttackImages() {
    attack_up1 = setup("/assets/Monster/skeletonlord_attack_up_1", gp.tileSize * 2, gp.tileSize * 2);
    attack_up2 = setup("/assets/Monster/skeletonlord_attack_up_2", gp.tileSize * 2, gp.tileSize * 2);
    attack_down1 = setup("/assets/Monster/skeletonlord_attack_down_1", gp.tileSize * 2, gp.tileSize * 2);
    attack_down2 = setup("/assets/Monster/skeletonlord_attack_down_2", gp.tileSize * 2, gp.tileSize * 2);
    attack_left1 = setup("/assets/Monster/skeletonlord_attack_left_1", gp.tileSize * 2, gp.tileSize * 2);
    attack_left2 = setup("/assets/Monster/skeletonlord_attack_left_2", gp.tileSize * 2, gp.tileSize * 2);
    attack_right1 = setup("/assets/Monster/skeletonlord_attack_right_1", gp.tileSize * 2, gp.tileSize * 2);
    attack_right2 = setup("/assets/Monster/skeletonlord_attack_right_2", gp.tileSize * 2, gp.tileSize * 2);
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

  public void draw(Graphics2D g2) {
    //position of tile on screen
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    //only draw tiles within visible range
    if(worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize&&
      worldY > gp.player.worldY - gp.player.screenY - gp.tileSize && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize)
      {
       switch(direction){

         case "up":
           if(spriteNum == 1)
           {
             if(attacking == false) { image = up1; }
             else { image = attack_up1; }
           } 
             
           else if(spriteNum == 2)
           {
             if(attacking == false) { image = up2; }
             else { image = attack_up2; }
           }
   
           break;
   
         case "idle_up":
           if(spriteNum == 1)
           {
             if(attacking == false) { image = idle_up; }
             else { image = attack_up1; }
           } 
             
           else if(spriteNum == 2)
           {
             if(attacking == false) { image = idle_up; }
             else { image = attack_up2; }
           }
   
           break;
   
         case "down":
           if(spriteNum == 1)
           {
             if(attacking == false) { image = down1; }
             else { image = attack_down1; }
           } 
             
           else if(spriteNum == 2)
           {
             if(attacking == false) { image = down2; }
             else { image = attack_down2; }
           }
           
           break;
   
         case "idle_down":
           if(spriteNum == 1)
           {
             if(attacking == false) { image = idle_down; }
             else { image = attack_down1; }
           } 
             
           else if(spriteNum == 2)
           {
             if(attacking == false) { image = idle_down; }
             else { image = attack_down2; }
           }
           
           break;
   
         case "left":
         if(spriteNum == 1)
         {
           if(attacking == false) { image = left1; }
           else { image = attack_left1; }
         } 
           
         else if(spriteNum == 2)
         {
           if(attacking == false) { image = left2; }
           else { image = attack_left2; }
         }
         
         break;
   
         case "idle_left":
         if(spriteNum == 1)
         {
           if(attacking == false) { image = idle_left; }
           else { image = attack_left1; }
         } 
           
         else if(spriteNum == 2)
         {
           if(attacking == false) { image = idle_left; }
           else { image = attack_left2; }
         }
         
         break;
   
         case "right":
         if(spriteNum == 1)
           {
             if(attacking == false) { image = right1; }
             else { image = attack_right1; }
           } 
             
           else if(spriteNum == 2)
           {
             if(attacking == false) { image = right2; }
             else { image = attack_right2; }
           }
           
           break;
   
         case "idle_right":
   
         if(spriteNum == 1)
           {
             if(attacking == false) { image = idle_right; }
             else { image = attack_right1; }
           } 
             
           else if(spriteNum == 2)
           {
             if(attacking == false) { image = idle_right; }
             else { image = attack_right2; }
           }
           
           break;
       }

         //MOSTER HEALTHBAR
         if(type == entityType.MONSTER && hpBarOn) {
           double oneHealthUnit = (double)gp.tileSize/maxlife;
           double hpBarValue = oneHealthUnit * life;

           g2.setColor(new Color(35 , 35 , 35));
           g2.fillRect(screenX-1, screenY-16, gp.tileSize+2 , 10);

           g2.setColor(new Color(255 , 0 , 30));
           g2.fillRect(screenX, screenY-15,(int)hpBarValue, 12);

           hpBarCounter++;
           if(hpBarCounter >= 600) {
             hpBarOn = false;
             hpBarCounter = 0;
           }
         }
         
         if(invincible == true) {
           hpBarOn = true;
           hpBarCounter = 0;
           g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 0.4f));
         }
         if(dying == true) {
           dyingAnimation(g2);
         }
        g2.drawImage(image, screenX, screenY , gp.tileSize*2 , gp.tileSize*2, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1f));
      }
 }

}


