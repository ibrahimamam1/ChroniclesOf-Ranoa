package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Object.OBJ_Basic_Sword;
import Object.OBJ_Key;
import game.GamePanel;
import game.KeyHandler;
import game.UtilityTool;

public class Player extends Entity{
  
  KeyHandler keyH;
  public int screenX;
  public int screenY; //Players positio on screen

  public boolean attackCancel = false;
  public ArrayList<Entity>inventory = new ArrayList<>();
  public final int maxInventory = 20;

  public Player(GamePanel gp , KeyHandler keyH){
    super(gp);
    this.keyH = keyH;

    maxlife = 6;
    life = maxlife;
    speed = 4;
    level = 1;
    strength = 1;
    dexterity  =1;
    exp = 0;
    nextLevelExp = 5;
    coin = 0;
    currentWeapon = new OBJ_Basic_Sword(gp);
    attack = strength * currentWeapon.attack;
    defense = dexterity * currentWeapon.defense;

    worldX = 1100;
    worldY = 1000;
    screenX = (gp.screenWidth/2) - (gp.tileSize/2);
    screenY = (gp.screenHeight/2) - (gp.tileSize/2);

    getPlayerImage();
    getPlayerAttackImages();
    setItems();

    solidArea = new Rectangle(8 ,  16 , 32  , 32); //solid are dimesion is smaller than actual character
    solidAreaDefaultX = 8;
    solidAreaDefaultY =  32;
    direction = "idle";

    attackArea.width = 36;
    attackArea.height = 36;
  }

  public void getPlayerImage(){
   
    idle = setup("/assets/boy_idle"  , gp.tileSize , gp.tileSize);
    up1 = setup("/assets/boy_up_1"  , gp.tileSize , gp.tileSize );
    up2 = setup("/assets/boy_up_2"  , gp.tileSize , gp.tileSize);
    down1 = setup("/assets/boy_down_1" , gp.tileSize , gp.tileSize);
    down2 = setup("/assets/boy_down_2" , gp.tileSize , gp.tileSize);
    left1 = setup("/assets/boy_left_1" , gp.tileSize , gp.tileSize);
    left2 = setup("/assets/boy_left_2" , gp.tileSize , gp.tileSize);
    right1 = setup("/assets/boy_right_1" , gp.tileSize , gp.tileSize);
    right2 = setup("/assets/boy_right_2" , gp.tileSize , gp.tileSize);
  }

  public void getPlayerAttackImages() {
    attack_up1 = setup("/assets/characters/attack_up1", gp.tileSize, gp.tileSize);
    attack_up2 = setup("/assets/characters/attack_up2", gp.tileSize, gp.tileSize);
    attack_down1 = setup("/assets/characters/attack_down1", gp.tileSize, gp.tileSize);
    attack_down2 = setup("/assets/characters/attack_down2", gp.tileSize, gp.tileSize);
    attack_left1 = setup("/assets/characters/attack_left1", gp.tileSize, gp.tileSize);
    attack_left2 = setup("/assets/characters/attack_left2", gp.tileSize, gp.tileSize);
    attack_right1 = setup("/assets/characters/attack_right1", gp.tileSize, gp.tileSize);
    attack_right2 = setup("/assets/characters/attack_right2", gp.tileSize, gp.tileSize);
  }

  public void setItems() {
    inventory.add(currentWeapon);
    inventory.add(new OBJ_Key(gp));
  }
  public void update()
  {
    if (attacking == true) {
      attacking();
    }
    else if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)
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
      

      if(this.colisionOn ==  false && keyH.enterPressed == false) {
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

      if(keyH.enterPressed == true && attackCancel == false) {
        attacking = true;
        spriteCounter = 0;
      }
      attackCancel = false;
      keyH.enterPressed = false;
      //simple sprite changer , the sprite will change after every 10 frames
      spriteCounter++;
      if(spriteCounter>10){
        if(spriteNum == 1) spriteNum = 2;
        else if(spriteNum == 2) spriteNum = 1;
  
        spriteCounter = 0;
      }
    }
    
    if(invincible == true) {
      invincibleCounter++;
      if(invincibleCounter == 60) {
        invincible = false;
        invincibleCounter = 0;
      }
    }

  }

  public void attacking() {
    spriteCounter++;
    if(spriteCounter <= 5) {
      spriteNum = 1;
    }
    else if(spriteCounter <= 25) {
      spriteNum = 2;

      //save current data
      int currentWorldX = worldX;
      int currentWorldY = worldY;
      int solidAreaWidth = solidArea.width;
      int solidAreaHeight = solidArea.height;

      //adjust data for attack
      switch(direction) {
        case "up": worldY -= attackArea.height; break;
        case "down": worldY += attackArea.height; break;
        case "left": worldY -= attackArea.width; break;
        case "right": worldY += attackArea.width; break;
      }
      solidArea.width = attackArea.width;
      solidArea.height = attackArea.height;

      //check monster collison
      int monsterIndex = gp.colisionDetector.checkEntity(this, gp.monster);
      if(monsterIndex != -1) { damageMonster(monsterIndex) ;}


      //Restore original data
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

  public void damageMonster(int i) {

    if(gp.monster[i].invincible == false) {

      gp.playSoundEffect(5);

      int damage = attack - gp.monster[i].defense;
      if(damage < 0) damage = 0;

      gp.monster[i].life -= damage;
      gp.monster[i].invincible = true;
      gp.monster[i].damageReaction();

      if(gp.monster[i].life <= 0) { 
        gp.monster[i].dying = true;
        exp += gp.monster[i].exp;
        checkLevelUp();
       };
    }

  }

  public void checkLevelUp() {

    if(exp >= nextLevelExp) {
      level++;
      nextLevelExp *= 2;
      maxlife += 2;
      strength++;
      dexterity++;
      attack = strength * currentWeapon.attack;
      defense = dexterity * currentWeapon.defense;

      gp.playSoundEffect(7);
      gp.uiManager.addMessage("Level Up!!!");
    }

  }

  public void selectItem() {
    int itemIndex = gp.uiManager.getItemIndexOnSlot();
    if(itemIndex < inventory.size()) {
      Entity selectedItem = inventory.get(itemIndex);
      if(selectedItem.type == entityType.SWORD) {
        currentWeapon = selectedItem;
        attack = strength * currentWeapon.attack;
        defense = dexterity * currentWeapon.defense;
      }
      if(selectedItem.type == entityType.CONSUMABLE) {
        selectedItem.use(this);
        inventory.remove(itemIndex);
      }
    }
  }

  public void pickUpObject(int i) {
    if(i != -1) {
      String text;
      if(inventory.size() < maxInventory) {
        inventory.add(gp.obj[i]);
        gp.playSoundEffect(1);
        text = "Picked Up " + gp.obj[i].name;

        gp.obj[i] = null;
      }
      else {
        text = "Inventory Full!!";
      }
      gp.uiManager.addMessage(text);
    }
  }

  public void interactNpc(int i) {
    if(keyH.enterPressed == true) {
      if(i != -1) {
          attackCancel = true;
          gp.gameState = gp.dialogueState;
          gp.npc[i].speak();
        }
      }
      
  }

  public void contactMonster(int i) {
    if(i != -1 && invincible == false) {
      gp.playSoundEffect(4);
      int damage = gp.monster[i].attack - defense;
      if(damage < 0) damage = 0;

      life -= damage;
      invincible = true;
    }

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
    }
    
    if(invincible == true) {
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 0.3f));
    }
    g2.drawImage(image, screenX, screenY , null);
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1f));
  }
}
