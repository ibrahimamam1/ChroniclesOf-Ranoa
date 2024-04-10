package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.KeyHandler;
import game.UtilityTool;

public class Player extends Entity{
  
  KeyHandler keyH;
  public int screenX;
  public int screenY; //Players positio on screen

  public Player(GamePanel gp , KeyHandler keyH){
    super(gp);
    this.keyH = keyH;

    maxlife = 6;
    life = maxlife;

    worldX = 1100;
    worldY = 1000;
    speed = 4;
    screenX = (gp.screenWidth/2) - (gp.tileSize/2);
    screenY = (gp.screenHeight/2) - (gp.tileSize/2);

    getPlayerImage();
    solidArea = new Rectangle(8 ,  16 , 32  , 32); //solid are dimesion is smaller than actual character
    solidAreaDefaultX = 8;
    solidAreaDefaultY =  32;
    direction = "idle";
  }

  public void update()
  {
    if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)
    {
      if(keyH.upPressed == true){
        direction = "up";
      }
      if(keyH.downPressed == true){
        direction = "down";
      }
      if(keyH.leftPressed == true){
        direction = "left";
      }
      if(keyH.rightPressed == true){
        direction = "right";
      }
      
      //CHECK Tile COLLISIONS
      gp.colisionDetector.checkTile(this);
      int objIndex = gp.colisionDetector.checkObject(this, true);
      pickUpObject(objIndex);

      //CHECK NPC COLLISIONS
      int npcIndex = gp.colisionDetector.checkEntity(this , gp.npc);
      interactNpc(npcIndex);

      //CHECK MONSTER COLLISIONS
      int monsterIndex = gp.colisionDetector.checkEntity(this, gp.monster);
      contactMonster(monsterIndex);
      
      //CHECK EVENT
      gp.eventhandler.checKEvent();
      keyH.enterPressed = false;

      if(this.colisionOn ==  false) {
        switch(direction) {
          case "up" : 
            worldY -= speed;  
            break;
          case "down" : 
            worldY += speed;
            break;
          case "left" : 
            worldX-= speed;
            break;
          case "right" : 
            worldX += speed;
            break;
        }
      }
      //simple sprite changer , the sprite will change after every 10 frames
      spriteCounter++;
      if(spriteCounter>10){
        if(spriteNum == 1) spriteNum = 2;
        else if(spriteNum == 2) spriteNum = 1;
  
        spriteCounter = 0;
      }
    }
    else direction = "idle";
    
    if(invincible == true) {
      invincibleCounter++;
      if(invincibleCounter == 60) {
        invincible = false;
        invincibleCounter = 0;
      }
    }
  }
  public void pickUpObject(int i) {
    if(i != -1) {
      
      
    }
  }

  public void interactNpc(int i) {
    if(i != -1) {
      if(keyH.enterPressed == true) {
        gp.gameState = gp.dialogueState;
        gp.npc[i].speak();
      }
    }
  }

  public void contactMonster(int i) {
    if(i != -1 && invincible == false) {
      life -= 1;
      invincible = true;
    }
  }

  public void getPlayerImage(){
   
    idle = setup("/assets/boy_idle");
    up1 = setup("/assets/boy_up_1");
    up2 = setup("/assets/boy_up_2");
    down1 = setup("/assets/boy_down_1");
    down2 = setup("/assets/boy_down_2");
    left1 = setup("/assets/boy_left_1");
    left2 = setup("/assets/boy_left_2");
    right1 = setup("/assets/boy_right_1");
    right2 = setup("/assets/boy_right_2");
  }


  public void draw(Graphics2D g2)
  {
    BufferedImage image = null;
    switch(direction){
      case "idle":
        image = idle;
        break;
      case "up":
        if(spriteNum == 1)
          image = up1;
        else if(spriteNum == 2)
          image = up2;
        break;
      case "down":
        if(spriteNum == 1)
          image = down1;
        else if(spriteNum == 2)
          image = down2;
        break;
      case "left":
        if(spriteNum == 1)
          image = left1;
        else if(spriteNum == 2)
          image = left2;
        break;
      case "right":
      if(spriteNum == 1)
        image = right1;
      else if(spriteNum == 2)
          image = right2;
        break;
    }
    if(invincible == true) {
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 0.3f));
    }
    g2.drawImage(image, screenX, screenY , null);
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1f));
  }
}
