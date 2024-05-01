package entity;

import java.awt.Graphics2D;
import java.util.Random;

import game.GamePanel;

public class NPC_Peasant extends Entity{
    
    boolean questGiven = false;
    boolean questFinish = false;
     public NPC_Peasant(GamePanel gp) {

    super(gp);
    direction = "idle";
    speed = 1;
    dialogueLength = 5;
    getImage();
    setInitialDialogue();
  }

  public void getImage(){

    idle = setup("/assets/NPC/peasant_idle" , gp.tileSize * 2, gp.tileSize * 2);

  }

  public void setInitialDialogue() {
    dialogues[0] = "Hello There...";
    dialogues[1] = "Are you an Adventurer?\n";
    dialogues[2] = "Some Slimes have occupied the area\nI cannot do my work anymore\n";
    dialogues[3] = "Can you please take care of them?\n I am but a peasant , i cannot fight";
    dialogues[4] = "I will reward you\n";
  }

  public void setAfterQuestDialogue() {
    dialogues[0] = "You took Care of Them";
    dialogues[1] = "Thank you so much\nI can resume my work now\n";
  }

  public void speak() {

    if(questGiven == true && questFinish == false) {
        if(isQuestCompleted() == true) {
            dialogueLength = 2;
            setAfterQuestDialogue();
            gp.player.coin += 100;
            gp.playSoundEffect(1);
            questFinish = true;
            gp.uiManager.addMessage("Reward = 100 coins");
        }
    }

    if(dialogueIndex == dialogueLength) { dialogueIndex = 0; questGiven = true;}
  
    gp.uiManager.currentDialogue = dialogues[dialogueIndex++];

    facePlayer();
    immobile = true; //Stays Immobile when talking to player
    System.out.println(gp.monster.length);
  }

  public boolean isQuestCompleted() {
    int count = 0;
    for(int i=0; i<gp.monster.length; i++) {
        if(gp.monster[i] != null) {
            count++;
        }
    }
    return count == 0;
  }
  public void draw(Graphics2D g2) {
    //position of tile on screen
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    //only draw tiles within visible range
    if(worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize&&
      worldY > gp.player.worldY - gp.player.screenY - gp.tileSize && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize)
      {
        g2.drawImage(idle, screenX, screenY , gp.tileSize*2 , gp.tileSize*2, null);
      }
 }
}
