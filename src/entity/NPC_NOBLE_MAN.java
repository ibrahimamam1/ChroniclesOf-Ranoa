package entity;

import java.util.Random;
import java.awt.Graphics2D;
import game.GamePanel;

public class NPC_NOBLE_MAN extends Entity  {
    
    public NPC_NOBLE_MAN(GamePanel gp) {
    super(gp);
    direction = "idle";
    speed = 1;
    getImage();
    setDialogue();
  }

  public void getImage(){

    idle = setup("/assets/NPC/nobleman_idle1" , gp.tileSize * 2, gp.tileSize * 2);
    left1 = setup("/assets/NPC/nobleman_left1" , gp.tileSize * 2, gp.tileSize * 2);
    left2 = setup("/assets/NPC/nobleman_left2" , gp.tileSize , gp.tileSize);
    right1 = setup("/assets/NPC/nobleman_right1" , gp.tileSize , gp.tileSize);
    right2 = setup("/assets/NPC/nobleman_right2" , gp.tileSize , gp.tileSize);

  }

  public void setAction() {

    actionLockCounter++;
      if(immobile == false) {

        if(actionLockCounter == 120) {
          Random random = new Random();
          int i = random.nextInt(100)+1;
    
          if(i <= 33) {
            direction = "idle";
          }
          else if(i > 33 && i <= 66) {
            direction = "right";
          }
          else if(i > 66) {
              direction = "left";
          }
          actionLockCounter = 0;
        }
  
        if(immobileCounter > 120) {
          immobileCounter = 0;
          immobile = false;
  
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

  public void setDialogue() {
    dialogues[0] = "Humm... Who are you?";
    dialogues[1] = "I am Count Lautrec\nOne of the nobles at the service of her majesty?\n";
    dialogues[2] = "Oh so you are an Adventurer\nMake sure not to cause any harm around here\n";
    dialogues[3] = "Also go announce yourself to her majesty at the castle\n";
    dialogues[4] = "Have a good day";
  }

  public void speak() {
    if(dialogueIndex > 4) { 

      dialogueIndex = 0; 
      immobile = false;
    }
  
    gp.uiManager.currentDialogue = dialogues[dialogueIndex++];

    facePlayer();
    immobile = true; //Stays Immobile when talking to player
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
            case "idle":
                image = idle;
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

        
        g2.drawImage(image, screenX, screenY , gp.tileSize*2 , gp.tileSize*2, null);
      }
 }
}
