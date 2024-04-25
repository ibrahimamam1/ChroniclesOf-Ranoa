package interativeTile;

import java.awt.Color;

import entity.Entity;
import game.GamePanel;

public class InteractiveTile extends Entity{

    public boolean isDestructible = false;
    public InteractiveTile(GamePanel gp , int col , int row) { //col and row = column and row position on world map
        super(gp);
        
    }

    public void update() {

    }

    public InteractiveTile getDestroyedForm() {
        return null;
    }

    public void playSE() { }
}
