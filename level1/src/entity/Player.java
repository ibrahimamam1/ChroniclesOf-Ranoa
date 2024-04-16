package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Object.OBJ_Basic_Sword;
import Object.OBJ_Key;
import Object.OBJ_Mana_Crystal;
import Object.OBJ_Red_Potion;
import game.GamePanel;
import game.KeyHandler;
import game.UtilityTool;

public class Player extends Entity{
  
  //PLAYER'S DRAWING POSITION ON SCREEN
  public int screenX;
  public int screenY; 

  public boolean attackCancel = false;
  public ArrayList<Entity>inventory = new ArrayList<>();
  public final int maxInventory = 20;

  public Player(GamePanel gp){

    super(gp);

    currentWeapon = new OBJ_Basic_Sword(gp);
    projectile = new Fireball(gp);

    //DEFAULT ATTRIBUTES
    maxlife = 20;
    life = maxlife;
    maxMana = 10;
    mana = maxMana;
    speed = 4;
    level = 1;
    strength = 1;
    dexterity  =1;
    attack = strength * currentWeapon.attack;
    defense = dexterity * currentWeapon.defense;
    exp = 0;
    nextLevelExp = 50;
    coin = 0;
    
    
    //DEFAULT POSITION
    worldX = 1100;
    worldY = 1000;
    screenX = (gp.screenWidth/2) - (gp.tileSize/2);
    screenY = (gp.screenHeight/2) - (gp.tileSize/2);

    //PLAYER SPRITES AND ANIMATIONS
    getPlayerImage();
    getPlayerAttackImages();

    //PLAYER"S HITABLE AREA
    solidArea = new Rectangle(8 ,  16 , 32  , 32); //solid area dimesions is smaller than actual character
    solidAreaDefaultX = 8;
    solidAreaDefaultY =  32;
    direction = "idle_down";

    //PLAYERS ATTACK RANGE
    attackArea.width = 36;
    attackArea.height = 36;

    //PLAYER"S DEFAULT INVENTORY ITEMS
    inventory.add(currentWeapon);
    inventory.add(new OBJ_Red_Potion(gp));
    inventory.add(new OBJ_Mana_Crystal(gp));

  }

  public void getPlayerImage(){
   
    idle_right = setup("/assets/characters/idle_right1"  , gp.tileSize * 2 , gp.tileSize * 2);
    idle_left = setup("/assets/characters/idle_left1"  , gp.tileSize * 2 , gp.tileSize * 2);
    idle_up = setup("/assets/characters/idle_up1"  , gp.tileSize * 2 , gp.tileSize * 2);
    idle_down = setup("/assets/characters/idle_down1"  , gp.tileSize * 2 , gp.tileSize * 2);
    up1 = setup("/assets/characters/run_up_1"  , gp.tileSize * 2 , gp.tileSize * 2 );
    up2 = setup("/assets/characters/run_up_2"  , gp.tileSize * 2 , gp.tileSize * 2);
    down1 = setup("/assets/characters/run_down_1" , gp.tileSize * 2 , gp.tileSize * 2);
    down2 = setup("/assets/characters/run_down_2" , gp.tileSize * 2 , gp.tileSize * 2);
    left1 = setup("/assets/characters/run_left_1" , gp.tileSize * 2 , gp.tileSize * 2);
    left2 = setup("/assets/characters/run_left_2" , gp.tileSize * 2 , gp.tileSize * 2);
    right1 = setup("/assets/characters/run_right_1" , gp.tileSize * 2 , gp.tileSize * 2);
    right2 = setup("/assets/characters/run_right_2" , gp.tileSize * 2 , gp.tileSize * 2);

  }

  public void getPlayerAttackImages() {
    attack_up1 = setup("/assets/characters/attack_up1", gp.tileSize * 2, gp.tileSize * 2);
    attack_up2 = setup("/assets/characters/attack_up2", gp.tileSize * 2, gp.tileSize * 2);
    attack_down1 = setup("/assets/characters/attack_down1", gp.tileSize * 2, gp.tileSize * 2);
    attack_down2 = setup("/assets/characters/attack_down2", gp.tileSize * 2, gp.tileSize * 2);
    attack_left1 = setup("/assets/characters/attack_left1", gp.tileSize * 2, gp.tileSize * 2);
    attack_left2 = setup("/assets/characters/attack_left2", gp.tileSize * 2, gp.tileSize * 2);
    attack_right1 = setup("/assets/characters/attack_right1", gp.tileSize * 2, gp.tileSize * 2);
    attack_right2 = setup("/assets/characters/attack_right2", gp.tileSize * 2, gp.tileSize * 2);
  }

  
  public void update()
  {

    if (attacking == true) {
      attacking();
    }
    
    else if(gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed)
    {
      if(gp.keyH.upPressed == true){
        direction = "up";
      }
      if(gp.keyH.downPressed == true){
        direction = "down";
      }
      if(gp.keyH.leftPressed == true){
        direction = "left";
      }
      if(gp.keyH.rightPressed == true){
        direction = "right";
      }
      
      //CHECK Tile COLLISIONS
      gp.colisionDetector.checkTile(this);

      //CHECK OBJECT COLLISIONS
      int objIndex = gp.colisionDetector.checkObject(this, true);
      pickUpObject(objIndex);

      //CHECK NPC COLLISIONS
      int npcIndex = gp.colisionDetector.checkEntity(this , gp.npc);
      if( npcIndex != -1 && gp.keyH.enterPressed == true) { interactNpc(npcIndex); }


      //CHECK MONSTER COLLISIONS
      int monsterIndex = gp.colisionDetector.checkEntity(this, gp.monster);
      contactMonster(monsterIndex);
      
      //CHECK EVENTS
      gp.eventhandler.checKEvent();
      

      //No Collision with non Walkable Entity
      if(this.colisionOn ==  false && gp.keyH.enterPressed == false) {
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

      if(gp.keyH.enterPressed == true && attackCancel == false) {
        attacking = true;
        spriteCounter = 0;
      }
      attackCancel = false;
      gp.keyH.enterPressed = false;
      
      //simple sprite changer , the sprite will change after every 10 frames
      spriteCounter++;
      if(spriteCounter>10){
        if(spriteNum == 1) spriteNum = 2;
        else if(spriteNum == 2) spriteNum = 1;
  
        spriteCounter = 0;
      }
    }
    
    //SHOOTING PROJECTILE
    if(gp.keyH.shootKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30
        && projectile.haveResource(this) == true) {

      projectile.set(worldX , worldY , direction , true , this);
      projectile.substractResources(this);
      gp.projectileList.add(projectile);
      shotAvailableCounter = 0;
      gp.playSoundEffect(9);

    }

    if(invincible == true) {
      invincibleCounter++;
      if(invincibleCounter == 60) {
        invincible = false;
        invincibleCounter = 0;
      }
    }
    if(shotAvailableCounter < 30) {
      shotAvailableCounter++;
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

      //check monster collison
      int monsterIndex = gp.colisionDetector.checkEntity(this, gp.monster);
      if(monsterIndex != -1) { damageMonster(monsterIndex , attack) ;}


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

  public void damageMonster(int i , int attack) {

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

      //PICKUP_ONLY OBJECTS
      if(gp.obj[i].type == entityType.PICKUP_ONLY) {

        gp.obj[i].use(this);
        gp.obj[i] = null;

      }
      
      //INVENTORY OBJECTS
      else {

        String text;
        if(inventory.size() < maxInventory) {

          inventory.add(gp.obj[i]);
          gp.playSoundEffect(1);
          text = "Picked Up " + gp.obj[i].name;

          gp.obj[i] = null;

        }
        else { text = "Inventory Full!!"; }

        gp.uiManager.addMessage(text);
      }
    }
  }

  public void interactNpc(int i) {
    
          attackCancel = true;
          gp.gameState = gp.dialogueState;
          gp.npc[i].speak();

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
    
    if(invincible == true) {
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 0.3f));
    }
    g2.drawImage(image, screenX, screenY , null);
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1f));
  }
}
