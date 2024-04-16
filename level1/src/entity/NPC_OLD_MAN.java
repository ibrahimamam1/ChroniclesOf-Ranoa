package entity;

import java.util.Random;

import game.GamePanel;

public class NPC_OLD_MAN extends Entity{

  public NPC_OLD_MAN(GamePanel gp) {
    super(gp);
    direction = "down";
    speed = 1;
    getImage();
    setDialogue();
  }

  public void getImage(){
   
    up1 = setup("/assets/NPC/oldman_up_1" , gp.tileSize , gp.tileSize);
    up2 = setup("/assets/NPC/oldman_up_2" , gp.tileSize , gp.tileSize);
    down1 = setup("/assets/NPC/oldman_down_1" , gp.tileSize , gp.tileSize);
    down2 = setup("/assets/NPC/oldman_down_2" , gp.tileSize , gp.tileSize);
    left1 = setup("/assets/NPC/oldman_left_1" , gp.tileSize , gp.tileSize);
    left2 = setup("/assets/NPC/oldman_left_2" , gp.tileSize , gp.tileSize);
    right1 = setup("/assets/NPC/oldman_right_1" , gp.tileSize , gp.tileSize);
    right2 = setup("/assets/NPC/oldman_right_2" , gp.tileSize , gp.tileSize);
  }

  public void setAction() {
    actionLockCounter++;
    if(actionLockCounter == 120) {
      Random random = new Random();
      int i = random.nextInt(100)+1;

      if(i <= 25) {
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
    
  }

  public void setDialogue() {
    dialogues[0] = "Hello Lad";
    dialogues[1] = "You don't seem to be from here\n What brings you to Mysticya?\n";
    dialogues[2] = "Oh so you are an Adventurer\n you are here to kill the dark lords/n";
    dialogues[3] = "I used to be a strong mage\n Alas at my age i cant help you/n";
    dialogues[4] = "Well good luck\n go meet the chief she might help";
  }

  public void speak() {
    if(dialogueIndex > 4)
    dialogueIndex = 0;
    gp.uiManager.currentDialogue = dialogues[dialogueIndex++];

    facePlayer();
  }
}
