package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.UtilityTool;

public class Entity {
  //GENERAL ATTRIBUTES
  public GamePanel gp;
  public int worldX , worldY; //Position of the entity in the game map
  public boolean hpBarOn = false;
  public int spriteNum = 1; 
  
  //ENTITY Stats
  public int maxlife;
  public int life;
  public int speed;
  public int level;
  public int strength;
  public int dexterity;
  public int attack;
  public int defense;
  public int maxMana;
  public int mana;
  public int exp;
  public int nextLevelExp;
  public int coin;
  public Entity currentWeapon;
  public Projectile projectile;
  public boolean invincible = false;
  public boolean alive = true;
  public boolean dying = false;
  
  
  //COUNTERS
  public int hpBarCounter = 0; // count how long monster hPBar will be displayed
  public int invincibleCounter = 0; //count for how long entity will be invincible 
  public int spriteCounter = 0; //count the sprite changinging ibnterval
  public int actionLockCounter = 0; // count what actions NPC does for how long
  public int dyingCounter = 0;
  public int shotAvailableCounter = 0;

  //SOLID AREA
  public Rectangle solidArea = new Rectangle(0 , 0 , 48 , 48);
  public Rectangle attackArea = new Rectangle(0 , 0 , 0 , 0);
  public int solidAreaDefaultX;
  public int solidAreaDefaultY;
  public boolean colisionOn;

  //ENTITY Attributes
  public BufferedImage image , image2 , image3;
  public BufferedImage idle , up1 , up2 , down1 , down2 , left1 , left2 , right1 , right2;
  public BufferedImage attack_up1 , attack_up2 ,attack_down1 , attack_down2 , attack_left1 , attack_left2 , attack_right1 , attack_right2;
  public String direction = "idle"; //default direction should be idle
  public String name;
  public String dialogues[] = new String[20];
  public int dialogueIndex = 0;
  public boolean walkable = false;
  boolean attacking = false;
  public enum entityType {
    PLAYER,
    NPC,
    MONSTER,
    SWORD,
    CONSUMABLE
  };
  public entityType type;


  //ITEM ATTRIBUTES
  public String description = "";
  

  public Entity(GamePanel gp) {
    this.gp = gp;
  }

  public BufferedImage setup(String imagePath , int width , int height){
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
  public void damageReaction() {}
  public void use(Entity entity) {}

  public void update() {
    setAction();
    colisionOn = false;
    gp.colisionDetector.checkTile(this);
    gp.colisionDetector.checkObject(this, false);
    gp.colisionDetector.checkEntity(this , gp.npc);
    gp.colisionDetector.checkEntity(this , gp.monster);
    boolean contact = gp.colisionDetector.checkPlayer(this);

    if(contact && type == entityType.MONSTER) {
      damagePlayer(attack);
    }

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

    if(invincible == true) {
      invincibleCounter++;
      if(invincibleCounter > 40) {
        invincible = false;
        invincibleCounter = 0;
      }
    }

    if(shotAvailableCounter < 30) {
      shotAvailableCounter++;
    }
  }
  
  public void damagePlayer(int attack) {
    if(gp.player.invincible == false) {
      int damage = attack - gp.player.defense;
      if(damage < 0) damage = 0;
      gp.player.life -= damage;
      gp.player.invincible = true;
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

          //monsetr healthbar
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
          if(dying) {
            dyingAnimation(g2);
          }
         g2.drawImage(image, screenX, screenY , gp.tileSize , gp.tileSize, null);
         g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1f));
       }
  }

  public void dyingAnimation(Graphics2D g2) {
    dyingCounter++;
    if(dyingCounter <= 5 || (dyingCounter>10 && dyingCounter <=15) || (dyingCounter > 20 && dyingCounter <= 25)) {
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 0.4f));
    }
    else if((dyingCounter > 5 && dyingCounter <= 10) || (dyingCounter>15 && dyingCounter <=20) || (dyingCounter > 25 && dyingCounter <= 30)) {
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1f));
    }

    if(dyingCounter > 30) {
      dying = false; alive = false;
    }
  }
}
