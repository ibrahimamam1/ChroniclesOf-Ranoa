package tiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

import game.GamePanel;

public class TileManager {
  GamePanel gp;
  Tile[] tile;
  int mapTileNum[][]; // will store map information in here


  public TileManager(GamePanel gp) {
    this.gp = gp;
    tile = new Tile[10]; //10 different type of tiles should be enough
    mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

    try {
      tile[0] = new Tile();
      tile[0].image = ImageIO.read(getClass().getResource("/assets/tilesets/grass.png")); //grass tiles

      tile[1] = new Tile();
      tile[1].image = ImageIO.read(getClass().getResource("/assets/tilesets/earth.png")); //earth tiles

      tile[2] = new Tile();
      tile[2].image = ImageIO.read(getClass().getResource("/assets/tilesets/wall.png")); //wall tiles

      
      tile[3] = new Tile();
      tile[3].image = ImageIO.read(getClass().getResource("/assets/tilesets/water.png")); //water tiles

      tile[4] = new Tile();
      tile[4].image = ImageIO.read(getClass().getResource("/assets/tilesets/tree.png")); //tree tiles

      tile[5] = new Tile();
      tile[5].image = ImageIO.read(getClass().getResource("/assets/tilesets/sand.png")); //sand tiles
    }
    catch(IOException e) {
      e.printStackTrace();
    }

    loadMap();
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

        g2.drawImage(tile[mapTileNum[i][j]].image, screenX, screenY , gp.tileSize , gp.tileSize , null);
      }
    } 
  }

  public void loadMap() {
    try {
      InputStream is = getClass().getResourceAsStream("/maps/world.txt");
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
}
