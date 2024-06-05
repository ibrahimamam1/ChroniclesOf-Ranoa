package interativeTile;

import game.GamePanel;

public class ITile_TreeTrunk extends InteractiveTile{

    public ITile_TreeTrunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        
        image = setup("/assets/Tiles_Interactive/trunk", gp.tileSize, gp.tileSize);
        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;
        isDestructible = false;

        //SET SOLID ARE TO 0 TO AVOID COLLISION
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;
    }
    
}
