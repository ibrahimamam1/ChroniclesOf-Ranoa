package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

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
  public int immobileCounter = 0;

  //SOLID AREA
  public Rectangle solidArea = new Rectangle(0 , 0 , 48 , 48);
  public Rectangle attackArea = new Rectangle(0 , 0 , 0 , 0);
  public int solidAreaDefaultX;
  public int solidAreaDefaultY;
  public boolean colisionOn;

  //ENTITY Attributes
  public BufferedImage image , image2 , image3;
  public BufferedImage idle , idle_right , idle_left , idle_up , idle_down , up1 , up2 , down1 , down2 , left1 , left2 , right1 , right2;
  public BufferedImage attack_up1 , attack_up2 ,attack_down1 , attack_down2 , attack_left1 , attack_left2 , attack_right1 , attack_right2;
  public String direction = "idle"; //default direction  idle
  public boolean onPath = false; // when true entity follows a pathfinding algorithm to some location
  public boolean immobile = false;
  public String name;
  public String dialogues[] = new String[20];
  public int dialogueIndex = 0;
  public int dialogueLength;
  public int value;
  public boolean walkable = false;
  public boolean attacking = false;
  public enum entityType {
    PLAYER,
    NPC,
    MONSTER,
    SWORD,
    CONSUMABLE,
    PICKUP_ONLY,
    OBSTACLE
  };
  public entityType type;


  //ITEM ATTRIBUTES
  public String description = "";
  public boolean stackable = false;
  public int amount = 1;
  

  public Entity(GamePanel gp) {
    this.gp = gp;
  }

  //---------GETTER FUNCTIONS FOR RETREIVING ENTITT'S POSITION INFORMATION------------------
  public int getLeftX() {
    return worldX + solidArea.x;
  }

  public int getRightX() {
    return worldX + solidArea.x + solidArea.width;
  } 

  public int getTopY() {
    return worldY + solidArea.y;
  }

  public int getBottomY() {
    return worldY + solidArea.y + solidArea.height;
  }

  public int getRow() {
    return (worldY + solidArea.y) / gp.tileSize;
  }

  public int getCol() {
    return (worldX + solidArea.x) / gp.tileSize;
  }

  //----------//
  public BufferedImage setup(String imagePath , int width , int height){

      UtilityTool uTool = new UtilityTool();
      BufferedImage scaledImage = null;

      try {
        scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        //scaledImage = uTool.scaleImage(scaledImage, width, height);
      }
      catch(IOException e) {
        e.printStackTrace();
      }

      return scaledImage;
  }

  public void setAction() {}

  public void damageReaction() {}

  public boolean use(Entity entity) { return false;}

  public void checkDrop() {};

  public void dropItem(Entity droppedItem) {

    for(int i=0; i<gp.obj.length; i++) {
      if(gp.obj[i] == null) {
        gp.obj[i] = droppedItem;
        gp.obj[i].worldX = worldX;
        gp.obj[i].worldY = worldY;
        break;
      }
    }

  }

  public void checkColission() {
    //CHECK CONTACT WITH PLAYER
    boolean contact = gp.colisionDetector.checkPlayer(this);
    if(contact && type == entityType.MONSTER) {
      if(name == "Green Slime" ) {
        damagePlayer(attack);
      }
      
    }

    //CHECK OTHER COLLISIONS
    colisionOn = false;
    gp.colisionDetector.checkTile(this);
    gp.colisionDetector.checkObject(this, false);
    gp.colisionDetector.checkEntity(this , gp.npc);
    gp.colisionDetector.checkEntity(this , gp.monster);
  }
  public void update() {

    if(attacking == true) {
      attacking();
    }

    else {
      setAction();
      checkColission();
      if(immobile == false) {
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
        if(spriteCounter>24){
          if(spriteNum == 1) spriteNum = 2;
          else if(spriteNum == 2) spriteNum = 1;

          spriteCounter = 0;
        }
      }
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
  
  public void attacking() {

    System.out.println("Orc Attacked");
    spriteCounter++;

    if(spriteCounter <= 5) {
      spriteNum = 1;
    }
    else if(spriteCounter <= 25) {

      spriteNum = 2;

      //save area occupied by player
      int currentWorldX = worldX;
      int currentWorldY = worldY;
      int solidAreaWidth = solidArea.width;
      int solidAreaHeight = solidArea.height;

      //adjust area for launching attack
      switch(direction) {

        case "up": worldY -= attackArea.height; break;
        case "idle_up": worldY -= attackArea.height; break;
        case "down": worldY += attackArea.height; break;
        case "idle_down": worldY += attackArea.height; break;
        case "left": worldY -= attackArea.width; break;
        case "idle_left": worldY -= attackArea.width; break;
        case "right": worldY += attackArea.width; break;
        case "idle_right": worldY += attackArea.width; break;

      }

      solidArea.width = attackArea.width;
      solidArea.height = attackArea.height;

      if(type == entityType.MONSTER) {
        if(gp.colisionDetector.checkPlayer(this) == true) {
          damagePlayer(attack);
        }
      }

      else //it is player
      {
         //check monster collison
        int monsterIndex = gp.colisionDetector.checkEntity(this, gp.monster);
        if(monsterIndex != -1) { gp.player.damageMonster(monsterIndex , attack) ;}

        //CHEK ATTACK ON INTERACTIVE TILE
        int interactiveTileIndex = gp.colisionDetector.checkEntity(this, gp.interactiveTile);
        if(interactiveTileIndex != -1) { gp.player.damageInteractiveTile(interactiveTileIndex) ; }

      } 

        //Restore original area occupied by player
        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;
     

    }

    else if(spriteCounter > 25) {
      spriteNum = 1;
      spriteCounter = 0;
      attacking = false;
    }

  }

  //check attack : checks if monster can attack the player
  public void checkAttack(int rate , int straight , int horizontal) {

    boolean targetInRange = false;
    int xDis = getXDistance(gp.player);
    int yDis = getYDistance(gp.player);

    switch(direction) {
      case "up":
        if(gp.player.worldY < worldY && yDis < straight && xDis < horizontal) {
          targetInRange = true;
        }
        break;
      
      case "down":
        if(gp.player.worldY > worldY && yDis < straight && xDis < horizontal) {
          targetInRange = true;
        }
        break;

      case "left":
        if(gp.player.worldX < worldX && xDis < straight && yDis < horizontal) {
          targetInRange = true;
        }
        break;

      case "right":
        if(gp.player.worldX > worldX && xDis < straight && yDis < horizontal) {
          targetInRange = true;
        }
      break;
    }

    if(targetInRange == true) {

      System.out.println("In Orc's Range");
      int i = new Random().nextInt(rate);
      if(i == 0) {
        attacking = true;
        spriteNum = 1;
        spriteCounter = 0;
        shotAvailableCounter = 0;
      }
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
  
  public void speak() {}

  public void facePlayer() {

    switch (gp.player.direction) {

      case "up": direction = "down"; break;
      case "down": direction = "up"; break;
      case "left": direction = "right"; break;
      case "right": direction = "left"; break;
    
    }
  }

  public void interact(int i) {}
  // public int getDetected(Entity user , Entity target[] , String targetName) { //Returns the index of a target if the player is in contact with it, return -1 if there is not

  //   int index = -1;

  //   //Check Sorrounding Object
  //   int nextWorldX = 0;
  //   int nextWorldY = 0;;

  //   switch (user.direction) {
  //     case "up":
  //       nextWorldY = user.getTopY()-1;
  //       break;

  //     case "down":
  //       nextWorldY = user.getBottomY()+1;
  //       break;
    
  //     case "right":
  //       nextWorldX = user.getRightX()+1;
  //       break;

  //     case "left":
  //       nextWorldX = user.getLeftX()-1;
  //       break;
  //   }

  //   int col = nextWorldX / gp.tileSize;
  //   int row = nextWorldY / gp.tileSize;

  //   //TRAVERSE TARGET ARRAY AND CHECK IF THERE IS ANY CONTACT WITH THE THE TARGET NAME
  //   for(int i=0; i<target.length; i++) {
  //     if(target[i] != null) {
  //       if(target[i].getCol() == col && target[i].getRow() == row && target[i].name.equals(targetName)) {
  //         index = i;
  //         break;
  //       }
  //     }
  //   }
  //   return index;  
  // } 

  //-- ENTITY PARTICLE GENERATION. ONLY FOR ENTITIES THAT NEED TO GENERATE PARTICLES FOR VISUAL EFFECT
  public Color getParticleColor() { return null; }
  public int getparticleSize() { return 0; }
  public int getParticleSpeed() { return 0; }
  public int getParticleMaxlife() { return 0; } 

  public void generateParticle(Entity generator , Entity target) { 

    Color color = generator.getParticleColor();
    int size = generator.getparticleSize();
    int speed = generator.getParticleSpeed();
    int maxlife = generator.getParticleMaxlife();


    Particle p1 = new Particle(gp, generator, color, size, speed, maxlife, -1, -1);
    gp.particleList.add(p1);
    Particle p2 = new Particle(gp, generator, color, size, speed, maxlife, -1, 1);
    gp.particleList.add(p2);
    Particle p3 = new Particle(gp, generator, color, size, speed, maxlife, 1, -1);
    gp.particleList.add(p3);
    Particle p4 = new Particle(gp, generator, color, size, speed, maxlife, 1, 1);
    gp.particleList.add(p4);

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

public void searchPath(int goalCol , int goalRow) {

    int startCol = (worldX + solidArea.x)/gp.tileSize;
    int startRow = (worldY + solidArea.y)/gp.tileSize;
    gp.pathFinder.setNodes(startCol, startRow, goalCol, goalRow, this);

    if(gp.pathFinder.search() == true) {

      int nextX = gp.pathFinder.pathList.get(0).col * gp.tileSize;
      int nextY = gp.pathFinder.pathList.get(0).row * gp.tileSize;

      //ENTITY'S SOLID AREA POSITION
      int enLeftX = worldX + solidArea.x;
      int enRightX = worldX + solidArea.x + solidArea.width;
      int enTopY = worldY + solidArea.y;
      int enBottomY = worldY + solidArea.y + solidArea.height;

      if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX+gp.tileSize) {
        direction = "up";
      }
      else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX+gp.tileSize) {
        direction = "down";
      }
      else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
        if(enLeftX > nextX) { direction = "left"; }
        if(enLeftX < nextX) { direction = "right"; }
      }
      else if(enTopY > nextY && enLeftX > nextX) {
        direction = "up";
        checkColission();
        if(colisionOn == true) { direction = "left";}
      }

      else if(enTopY > nextY && enLeftX < nextX) {
        direction = "up";
        checkColission();
        if(colisionOn == true) { direction = "right";}
      }

      else if(enTopY < nextY && enLeftX > nextX) {
        direction = "down";
        checkColission();
        if(colisionOn == true) { direction = "left";}
      }
      else if(enTopY < nextY && enLeftX < nextX) {
        direction = "down";
        checkColission();
        if(colisionOn == true) { direction = "right";}
      }

      System.out.println(direction);
      // int nextCol = gp.pathFinder.pathList.get(0).col;
      // int nextRow = gp.pathFinder.pathList.get(0).row;
      // if(nextCol == goalCol && nextRow == goalRow) { onPath = false;}
    }
  }

  public int getXDistance(Entity target) {
    int xDis = Math.abs(worldX - target.worldX);

    return xDis;
  }
  public int getYDistance(Entity target) {
    int yDis = Math.abs(worldY - target.worldY);

    return yDis;
  }
  public int getTileDistance(Entity target) {
    int tileDistance =  (getXDistance(target) + getYDistance(target) ) / gp.tileSize;
    return tileDistance;
  }
  public int getGoalCol(Entity target) {
    int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
    return goalCol;
  }
  public int getGoalRow(Entity target) {
    int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
    return goalRow;
  }

  public void checkStopMonsterAggro(Entity target , int distance , int rate) {

    if(getTileDistance(target) > distance) {
      int i = new Random().nextInt(rate);
      if(i == 0) {
        onPath = false;
      }
    }
  }

  public void checkStartMonsterAggro(Entity target , int distance) {

    if(getTileDistance(target) < distance) {
        onPath = true;
      }
  }

  public void getRandomDirection() {

    actionLockCounter++;
    if(actionLockCounter == 120) {
      Random random = new Random();
      int i = random.nextInt(100)+1;

      if(i <= 25 ) {
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

    else {

    immobileCounter++;
    if(immobileCounter > 120) {
      immobileCounter = 0;
      immobile = false;
    }
  }

}
}


