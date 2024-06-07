package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Object.OBJ_Basic_Sword;
import Object.OBJ_King_Sword;
import Object.OBJ_Mana_Crystal;
import Object.OBJ_Red_Potion;
import game.GamePanel;


public class Player extends Entity{
  
  //PLAYER'S DRAWING POSITION ON SCREEN
  public int screenX;
  public int screenY; 

  public boolean attackCancel = false;
  public int hasKey = 0;

  public ArrayList<Entity>inventory = new ArrayList<>();
  public final int maxInventory = 20;

  public Player(GamePanel gp){

    super(gp);

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

  }

  public void setDefaultStats() {
    currentWeapon = new OBJ_Basic_Sword(gp);
    projectile = new Fireball(gp);

    //DEFAULT ATTRIBUTES
    maxlife = 20;
    life = maxlife;
    maxMana = 10;
    mana = maxMana;
    speed = 4;
    level = 1;
    strength = 2;
    dexterity  =1;
    attack = strength * currentWeapon.attack;
    defense = dexterity * currentWeapon.defense;
    exp = 0;
    nextLevelExp = 50;
    coin = 0;
    
    if(gp.currentLevel == 3) { //just for demo
      dexterity = 5;
      defense = dexterity * currentWeapon.defense;
    }
    //DEFAULT POSITION
    screenX = (gp.screenWidth/2) - (gp.tileSize/2);
    screenY = (gp.screenHeight/2) - (gp.tileSize/2);
  }

  public void setDefaultItems() {

    System.out.println("Called set defaut items");
    //PLAYER"S DEFAULT INVENTORY ITEMS
    inventory.clear();
    inventory.add(currentWeapon);
    inventory.add(new OBJ_Red_Potion(gp));
    inventory.add(new OBJ_Mana_Crystal(gp));
    System.out.println();
  }
  public void getPlayerImage(){
   
    idle_right = setup("/assets/characters/boy_idle_right"  , gp.tileSize * 2 , gp.tileSize * 2);
    idle_left = setup("/assets/characters/boy_idle_left"  , gp.tileSize * 2 , gp.tileSize * 2);
    idle_up = setup("/assets/characters/boy_idle_up"  , gp.tileSize * 2 , gp.tileSize * 2);
    idle_down = setup("/assets/characters/boy_idle_down"  , gp.tileSize * 2 , gp.tileSize * 2);
    up1 = setup("/assets/characters/boy_up_1"  , gp.tileSize * 2 , gp.tileSize * 2 );
    up2 = setup("/assets/characters/boy_up_2"  , gp.tileSize * 2 , gp.tileSize * 2);
    down1 = setup("/assets/characters/boy_down_1" , gp.tileSize * 2 , gp.tileSize * 2);
    down2 = setup("/assets/characters/boy_down_2" , gp.tileSize * 2 , gp.tileSize * 2);
    left1 = setup("/assets/characters/boy_left_1" , gp.tileSize * 2 , gp.tileSize * 2);
    left2 = setup("/assets/characters/boy_left_2" , gp.tileSize * 2 , gp.tileSize * 2);
    right1 = setup("/assets/characters/boy_right_1" , gp.tileSize * 2 , gp.tileSize * 2);
    right2 = setup("/assets/characters/boy_right_2" , gp.tileSize * 2 , gp.tileSize * 2);

  }

  public void getPlayerAttackImages() {
    attack_up1 = setup("/assets/characters/attack_up_1", gp.tileSize * 2, gp.tileSize * 2);
    attack_up2 = setup("/assets/characters/attack_up_2", gp.tileSize * 2, gp.tileSize * 2);
    attack_down1 = setup("/assets/characters/attack_down_1", gp.tileSize * 2, gp.tileSize * 2);
    attack_down2 = setup("/assets/characters/attack_down_2", gp.tileSize * 2, gp.tileSize * 2);
    attack_left1 = setup("/assets/characters/attack_left_1", gp.tileSize * 2, gp.tileSize * 2);
    attack_left2 = setup("/assets/characters/attack_left_2", gp.tileSize * 2, gp.tileSize * 2);
    attack_right1 = setup("/assets/characters/attack_right_1", gp.tileSize * 2, gp.tileSize * 2);
    attack_right2 = setup("/assets/characters/attack_right_2", gp.tileSize * 2, gp.tileSize * 2);
  }

  public void setDefaultPositions() {
    //DEFAULT POSITION
    System.out.println("Setting default position for level " + gp.currentLevel);
    if(gp.currentLevel == 1) {
      worldX = 37 * gp.tileSize;
      worldY = 7 * gp.tileSize;
      direction = "down";
    }
    else if(gp.currentLevel == 2) {
      worldX = 1100;
      worldY = 1000;
      direction = "down";
    }
    else {
      worldX = 39 * gp.tileSize;
      worldY = 8 * gp.tileSize;
    }
    
  }

  public void restoreLifeAndMana() { //AFTER DEATH
    life = maxlife;
    mana = maxMana;
    invincible = false;
  }
  public void update()
  {

    System.out.println(worldX/gp.tileSize + " " + worldY/gp.tileSize);

    //PLAYER ATTACKING
    if (attacking == true) {
      attacking();
    }
    
    //PLAYER MOVING AROUND
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
      
      //--  CHECK COLLISIONS. WILL SET A BOOLEAN TO TRUE IF ANY FORM OF COLLISION OCCURS --- 


      //CHECK Tile COLLISIONS
      gp.colisionDetector.checkTile(this);

      //CHECK OBJECT COLLISIONS  , RETURNS WHICH OBJECT
      int objIndex = gp.colisionDetector.checkObject(this, true);
      pickUpObject(objIndex);

      //CHECK NPC COLLISIONS , RETURNS WHICH NPC
      int npcIndex = gp.colisionDetector.checkEntity(this , gp.npc);
      if(npcIndex != -1 && gp.keyH.enterPressed == true ) { interactNpc(npcIndex); }


      //CHECK MONSTER COLLISIONS , RETURNS WHICH MONSTER
      int monsterIndex = gp.colisionDetector.checkEntity(this, gp.monster);
      contactMonster(monsterIndex);

      //CHECK COLLISION WITH INTERACTIVE TILES
      gp.colisionDetector.checkEntity(this, gp.interactiveTile);
      
      //CHECK EVENTS AND ACTIVATE THEM
      gp.eventhandler.checKEvent();
     
      
      if(this.colisionOn ==  false && gp.keyH.enterPressed == false) { //No Collision player can move
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
      
      //Change Player Sprite every 10 frames
      spriteCounter++;
      if(spriteCounter>10){
        if(spriteNum == 1) spriteNum = 2;
        else if(spriteNum == 2) spriteNum = 1;
  
        spriteCounter = 0;
      }

    }
    
    //PLAYER SHOOTING PROJECTILE
    if(gp.keyH.shootKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30
        && projectile.haveResource(this) == true) {

      projectile.set(worldX , worldY , direction , this);
      projectile.substractResources(this);
      gp.projectileList.add(projectile);
      shotAvailableCounter = 0;
      gp.playSoundEffect(9);

    }


    //INCREMENT COUNTERS
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

    //CHECK LIFE
    if(life <= 0) {
      gp.gameState = gp.gameOverState;
    }

  }

  public void pickUpObject(int i) {

    if(i != -1) {

      //PICKUP_ONLY OBJECTS
      if(gp.obj[i].type == entityType.PICKUP_ONLY) {
        gp.obj[i].use(this);
      }

      else if(gp.obj[i].type == entityType.OBSTACLE) {
        if(gp.keyH.enterPressed == true) {
          attackCancel = true;
          gp.obj[i].interact(i);
        }
      }
      
      //INVENTORY OBJECTS
      else {

        if(gp.obj[i].name == "Key") {
          hasKey++;
        }
        String text;
        if(obtainItem(gp.obj[i]) == true) {

          gp.playSoundEffect(1);
          text = "Picked Up " + gp.obj[i].name;

          gp.obj[i] = null;

        }
        else { text = "Inventory Full!!"; }

        gp.uiManager.addMessage(text);
      }
    }
  }
  public void useKey() {

    for(int i = 0; i<inventory.size(); i++) {

      if(inventory.get(i) != null) {
        if(inventory.get(i).name == "Key") {

          hasKey--;
          if(inventory.get(i).amount > 1) {
            inventory.get(i).amount--;
          }

          else {inventory.remove(i); }
          break;
        }
      }
    }
  }

  public int searchItemInInventory(String itemName) {
    int itemIndex = -1;

    for(int i=0; i<inventory.size(); i++) {
      if(inventory.get(i).name.equals(itemName)) {
        itemIndex = i;
        break;
      }
    }
    return itemIndex;
  }

  public boolean obtainItem(Entity item) {

    boolean canObtain = false;
    int index = searchItemInInventory(item.name);
    
    if(item.stackable == false || index == -1) { // NNOT STACKABLE OR NEW ITEM , WE NEED TO CHECK VACANCY
      if(inventory.size() != maxInventory) {
        inventory.add(item);
        canObtain = true;
      } 
    }
    else { //STACKABLE ITEM ALREADY IN INVENTORY
        inventory.get(index).amount++;
        canObtain = true;
      } 
    return canObtain;
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
      gp.uiManager.addMessage("Leveled Up!!!");

    }

  }

  public void damageInteractiveTile(int index) {

      if(gp.interactiveTile[index].isDestructible == true) {
        gp.interactiveTile[index] = gp.interactiveTile[index].getDestroyedForm();
        gp.interactiveTile[index].playSE();
        generateParticle(gp.interactiveTile[index], gp.interactiveTile[index]);
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

        if( selectedItem.use(this) == true) {
          if(selectedItem.amount > 1) {
            selectedItem.amount--;
          }
          else {
            inventory.remove(itemIndex);
          }
        }
        
      }
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
    g2.drawImage(image, screenX, screenY , gp.tileSize*2 , gp.tileSize*2,  null);
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1f));
  }
}
