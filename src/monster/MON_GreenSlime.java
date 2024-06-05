
package monster;

import java.util.Random;

import entity.Entity;
import game.GamePanel;
import Object.OBJ_Rock;

public class MON_GreenSlime extends Entity {
	GamePanel gp;

    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        
        this.gp=gp;
        type = entityType.MONSTER;
        name = "Green Slime";
        speed = 1;
        maxlife = 4;
        life = maxlife;
        
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);
        
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        getImage();
    }

    public void getImage() {
        up1 = setup("/assets/Monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        up2 = setup("/assets/Monster/greenslime_down_2",gp.tileSize,gp.tileSize);
        down1 = setup("/assets/Monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/assets/Monster/greenslime_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/assets/Monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        left2 = setup("/assets/Monster/greenslime_down_2",gp.tileSize,gp.tileSize);
        right1 = setup("/assets/Monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        right2 = setup("/assets/Monster/greenslime_down_2",gp.tileSize,gp.tileSize);
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
		
		int i = new Random().nextInt(100) + 1; // Generate random number (1-100)
		if (i > 99 && projectile.alive == false && shotAvailableCounter == 30) { // Check conditions for firing
		    projectile.set(worldX, worldY, direction, this); // Initialize projectile
		    gp.projectileList.add(projectile); // Add projectile to list
		    shotAvailableCounter = 0; // Reset counter
		}
		
		
    }
    public void damageReaction() {
    	actionLockCounter =0;
    	direction = gp.player.direction;
    }
}