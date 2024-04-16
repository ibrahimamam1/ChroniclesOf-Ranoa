package tiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

import game.GamePanel;
import game.UtilityTool;

public class TileManager {

  GamePanel gp;
  public Tile[] tile;
  public int mapTileNum[][]; // array storing map position of each tile


  public TileManager(GamePanel gp) {

    this.gp = gp;
    tile = new Tile[50]; //Array containing all different type of tiles
    mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

    getTileImage();
  }

  public void getTileImage() {
    //------------PLACEHOLDERS ------------
      setup(0, "grass00", true);
      setup(1, "grass00", true);
      setup(2, "grass00", true);
      setup(3, "grass00", true);
      setup(4, "grass00", true);
      setup(5, "grass00", true);
      setup(6, "grass00", true);
      setup(7, "grass00", true);
      setup(8, "grass00", true);
      setup(9, "grass00", true);
    //-----------------------------------------------//

      setup(10, "grass00", true);
      setup(11, "grass01", true);
      setup(12, "water00", false);
      setup(13, "water01", false);
      setup(14, "water02", false);
      setup(15, "water03", false);
      setup(16, "water04", false);
      setup(17, "water05", false);
      setup(18, "water06", false);
      setup(19, "water07", false);
      setup(20, "water08", false);
      setup(21, "water09", false);
      setup(22, "water10", false);
      setup(23, "water11", false);
      setup(24, "water12", false);
      setup(25, "water13", false);
      setup(26, "road00", true);
      setup(27, "road01", true);
      setup(28, "road02", true);
      setup(29, "road03", true);
      setup(30, "road04", true);
      setup(31, "road05", true);
      setup(32, "road06", true);
      setup(33, "road07", true);
      setup(34, "road08", true);
      setup(35, "road09", true);
      setup(36, "road10", true);
      setup(37, "road11", true);
      setup(38, "road12", true);
      setup(39, "earth", true);
      setup(40, "wall", false);
      setup(41, "tree", false);
      
  }

  public void setup(int index , String imagePath ,boolean isWalkable) {

    UtilityTool uTool = new UtilityTool();
    
    try{

      tile[index] = new Tile();
      tile[index].image = ImageIO.read(getClass().getResource("/assets/tilesets/" + imagePath + ".png"));
      tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
      tile[index].walkable = isWalkable;

    } 
    catch(IOException e) {
      e.printStackTrace();
    }

  }

  public void loadMap() {

    try {

      InputStream is = getClass().getResourceAsStream("/maps/worldV2.txt");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      for(int i=0; i<gp.maxWorldRow; i++) {
        String line = br.readLine();
        for(int j=0; j<gp.maxWorldCol; j++) {
          String numbers[] = line.split(" ");
          int num = Integer.parseInt(numbers[j]);
          mapTileNum[i][j] = num;
        }
      }

      br.close();
    } 

    catch(Exception e) {
      e.printStackTrace();
    }
    
  }

  public void draw(Graphics2D g2) {

    for(int i=0; i<gp.maxWorldRow; i++) {

      for(int j=0; j<gp.maxWorldCol; j++) {

        //position of tile in world
        int worldX = j *  gp.tileSize;
        int worldY = i * gp.tileSize;

        //position of tile on screen
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //only draw tiles within visible range
        if(worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize&&
          worldY > gp.player.worldY - gp.player.screenY - gp.tileSize && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize)
          {
            g2.drawImage(tile[mapTileNum[i][j]].image, screenX, screenY , null);
          }
      }
    } 
  }

  
}
