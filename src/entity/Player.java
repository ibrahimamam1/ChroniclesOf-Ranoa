package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.KeyHandler;

public class Player extends Entity{
  
  GamePanel gp;
  KeyHandler keyH;
  public int screenX;
  public int screenY; //Players positio on screen

  public Player(GamePanel gp , KeyHandler keyH){
    this.gp = gp;
    this.keyH = keyH;

    worldX = 500;
    worldY = 500;
    speed = 4;
    screenX = (gp.screenWidth/2) - (gp.tileSize/2);
    screenY = (gp.screenHeight/2) - (gp.tileSize/2);

    getPlayerImage();
    solidArea = new Rectangle(8 ,  16 , 32  , 32); //solid are dimesion is smaller than actual character
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
      
      gp.colisionDetector.checkTile(this);
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
    g2.drawImage(image, screenX, screenY , gp.tileSize*3 , gp.tileSize*3 , null);
  }

  public void getPlayerImage(){
    try{
      idle = ImageIO.read(getClass().getResourceAsStream("/assets/boy_idle.png"));
      up1 = ImageIO.read(getClass().getResourceAsStream("/assets/boy_up_1.png"));
      up2 = ImageIO.read(getClass().getResourceAsStream("/assets/boy_up_2.png"));
      down1 = ImageIO.read(getClass().getResourceAsStream("/assets/boy_down_1.png"));
      down2 = ImageIO.read(getClass().getResourceAsStream("/assets/boy_down_2.png"));
      left1 = ImageIO.read(getClass().getResourceAsStream("/assets/boy_left_1.png"));
      left2 = ImageIO.read(getClass().getResourceAsStream("/assets/boy_left_2.png"));
      right1 = ImageIO.read(getClass().getResourceAsStream("/assets/boy_right_1.png"));
      right2 = ImageIO.read(getClass().getResourceAsStream("/assets/boy_right_2.png"));
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}
