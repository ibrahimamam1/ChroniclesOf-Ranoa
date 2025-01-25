package entity;


import java.util.Random;

import game.GamePanel;


public class NPC_Guru extends Entity{
	
	public NPC_Guru(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
 		 getImage();
 		 setDialogue();
	}
	public void getImage() {

		up1 = setup("/assets/NPC/merchant_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/assets/NPC/merchant_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/assets/NPC/merchant_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/assets/NPC/merchant_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/assets/NPC/merchant_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/assets/NPC/merchant_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/assets/NPC/merchant_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/assets/NPC/merchant_down_2", gp.tileSize, gp.tileSize);
	
	}
	
	public void setDialogue() {
		
		dialogues[0] = "Hello, lad";
		dialogues[1] = "So, you have come to this island \nto fulfill the prophecy?";
		dialogues[2] = "Head towards the castle \nThey await you there \nThis journey is dangerous";
		dialogues[3] = "well, good luck on you.";
	}
	
	public void setAction() {
		
		actionLockCounter ++;
		
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100)+1;// pick up a number from 1 to 100
			
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i<= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			
			actionLockCounter = 0;
			
		}
		
	}
	
	public void speak() {
		//do this character specific stuff
		if(dialogueIndex > 3) { 

			dialogueIndex = 0; 
			immobile = false;
		  }
		
		  gp.uiManager.currentDialogue = dialogues[dialogueIndex++];
	  
		  facePlayer();
		  immobile = true; //Stays Immobile when talking to player
	}

}