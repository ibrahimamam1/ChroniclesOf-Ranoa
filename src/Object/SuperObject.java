package Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.GamePanel;
import game.UtilityTool;

public class SuperObject {
  UtilityTool uTool = new UtilityTool();

  public BufferedImage image;
  public String name;
  public boolean walkable = false;

  public int worldX;
  public int worldY;
  
  public Rectangle solidArea = new Rectangle(0 , 0 , 48 , 48); //48 = tileSize
  public int solidAreaDefaultX = 0;
  public int solidAreaDefaultY = 0;

  public void draw(Graphics2D  g2 , GamePanel gp) {
    //position of tile on screen
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    //only draw tiles within visible range
    if(worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize&&
      worldY > gp.player.worldY - gp.player.screenY - gp.tileSize && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize)
      {
        g2.drawImage(image, screenX, screenY , null);
      }
  }
}
