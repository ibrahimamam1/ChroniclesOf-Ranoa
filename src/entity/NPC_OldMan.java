package entity;



import java.util.Random;

import game.GamePanel;


public class NPC_OldMan extends Entity {

	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction ="down";
		speed = 1;
		
		getImage();
		setDialogue();
	}
	public void getImage() {

	    up1 = setup("/assets/NPC/oldman_up_1",gp.tileSize,gp.tileSize);
	    up2 = setup("/assets/NPC/oldman_up_2",gp.tileSize,gp.tileSize);
	    down1 = setup("/assets/NPC/oldman_down_1",gp.tileSize,gp.tileSize);
	    down2 = setup("/assets/NPC/oldman_down_2",gp.tileSize,gp.tileSize);
	    left1 = setup("/assets/NPC/oldman_left_1",gp.tileSize,gp.tileSize);
	    left2 = setup("/assets/NPC/oldman_left_2",gp.tileSize,gp.tileSize);
	    right1 = setup("/assets/NPC/oldman_right_1",gp.tileSize,gp.tileSize);
	    right2 = setup("/assets/NPC/oldman_right_2",gp.tileSize,gp.tileSize);

	}
	
	public void setDialogue() {
		dialogues[0]="Hello young Man!";
		dialogues[1]="Finally you've come.\n My wait is over";
		dialogues[2]="I was the friend of your father \nwho was killed by the dark lord";
		dialogues[3]="I will guide you to take a revenge";
	}
	
	

	public void setAction() {
		
		actionLockCounter ++;
		if(actionLockCounter ==120) {
			Random random = new Random();
		    int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

		    if (i <= 25) {
		        direction = "up";
		    }

		    if (i > 25 && i <= 50) {
		        direction = "down";
		    }

		    if (i > 50 && i <= 75) {
		        direction = "left";
		    }

		    if (i > 75 && i <= 100) {
		        direction = "right";
		    }
		    
		    actionLockCounter =0;
		}
	    
	}
	public void speak() {
		
		if(dialogueIndex > 4) { 

			dialogueIndex = 0; 
			immobile = false;
		  }
		
		  gp.uiManager.currentDialogue = dialogues[dialogueIndex++];
	  
		  facePlayer();
		  switch ("direction") {
			case "up":
				direction = "idle_up";
				break;
			case "down":
				direction = "idle_down";
				break;
			case "left":
				direction = "idle_left";
				break;
			case "right":
				direction = "idle_right";
				break;
		  
			default:
				break;
		  }
	}

}
