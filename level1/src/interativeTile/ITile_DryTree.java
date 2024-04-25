package interativeTile;

import java.awt.Color;

import game.GamePanel;

public class ITile_DryTree extends InteractiveTile{

    public ITile_DryTree(GamePanel gp , int col , int row) { //col and row = column and row position on world map
        super(gp , col , row);
        
        name = "Dry Tree";
        image = setup("/assets/Tiles_Interactive/drytree", gp.tileSize, gp.tileSize);
        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;
        isDestructible = true;
    }

    public InteractiveTile getDestroyedForm() {
        return new ITile_TreeTrunk(gp, this.worldX/gp.tileSize, this.worldY/gp.tileSize);   
    }

    public void playSe() { 
        gp.playSoundEffect(6);
    }
    public Color getParticleColor() { return new Color(65 , 50 , 30); }
    public int getparticleSize() { return 10;}
    public int getParticleSpeed() { return 1;}
    public int getParticleMaxlife() { return 20;}
    
}
