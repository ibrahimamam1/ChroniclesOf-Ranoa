package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.UtilityTool;

public class Entity {
  GamePanel gp;
  public int worldX , worldY; //Position of the entity in the game map
  public int speed;

  public BufferedImage idle , up1 , up2 , down1 , down2 , left1 , left2 , right1 , right2;
  public String direction;
  public String dialogues[] = new String[20];
  public int dialogueIndex = 0;

  public Rectangle solidArea = new Rectangle(0 , 0 , 48 , 48);
  public int solidAreaDefaultX;
  public int solidAreaDefaultY;
  public boolean colisionOn;

  public int spriteCounter = 0;
  public int spriteNum = 1; 

  public int actionLockCounter = 0;

  Entity(GamePanel gp) {
    this.gp = gp;
  }

  public BufferedImage setup(String imagePath){
      UtilityTool uTool = new UtilityTool();
      BufferedImage scaledImage = null;

      try {
        scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        //scaledImage = uTool.scaleImage(scaledImage, gp.tileSize*2, gp.tileSize*2);
      }
      catch(IOException e) {
        e.printStackTrace();
      }

      return scaledImage;
  }

  public void setAction() {}
  public void update() {
    setAction();
    colisionOn = false;
    gp.colisionDetector.checkTile(this);
    gp.colisionDetector.checkObject(this, false);
    gp.colisionDetector.checkPlayer(this);

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
  
  public void speak() {
    if(dialogueIndex > 4)
    dialogueIndex = 0;
    gp.uiManager.currentDialogue = dialogues[dialogueIndex++];

    switch (direction) {
      case "up":
          direction = "down";
        break;
      case "down":
          direction = "up";
        break;
      case "left":
          direction = "right";
        break;
      case "right":
          direction = "left";
        break;
    
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
          BufferedImage image = null;
          switch(direction){
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
         g2.drawImage(image, screenX, screenY , gp.tileSize , gp.tileSize, null);
       }
  }
}
