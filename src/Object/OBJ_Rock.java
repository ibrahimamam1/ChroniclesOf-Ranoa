package Object;

import entity.Entity;
import entity.Projectile;
import game.GamePanel;

public class OBJ_Rock extends Projectile {
    
    GamePanel gp;

    // Constructor for the Fireball object
    public OBJ_Rock(GamePanel gp) {
        super(gp); // Call the constructor of the superclass (Projectile)
        this.gp = gp;
        
        // Initialize Fireball properties
        name = "Rock";
        speed = 8;
        maxlife = 80;
        life = maxlife;
        attack = 2;
        useCost = 1;
        alive = false;
        
        // Load images for the Fireball
        getImage();
    }

    // Method to load images for different directions
    public void getImage() {
        // Set up images for moving up
        up1 = setup("/assets/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/assets/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        // Set up images for moving down
        down1 = setup("/assets/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/assets/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        // Set up images for moving left
        left1 = setup("/assets/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/assets/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        // Set up images for moving right
        right1 = setup("/assets/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/assets/Projectile/rock_down_1", gp.tileSize, gp.tileSize);
    }
    
    public boolean haveResource(Entity user) {
    	boolean haveResource =false;
    	
    	if(user.mana>=useCost) {
    		haveResource = true; 
    	}
    	return haveResource;
    }
    public void subtractResource(Entity user) {
    	user.mana -=useCost;
    }
}