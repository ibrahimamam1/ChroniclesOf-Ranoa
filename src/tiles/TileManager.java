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
    mapTileNum = new int[gp.maxScreenRow][gp.maxScreenCol];

    try {
      tile[0] = new Tile();
      tile[0].image = ImageIO.read(getClass().getResource("/assets/tilesets/grass.png")); //grass tiles

      tile[1] = new Tile();
      tile[1].image = ImageIO.read(getClass().getResource("/assets/tilesets/water.png")); //water tiles

      tile[2] = new Tile();
      tile[2].image = ImageIO.read(getClass().getResource("/assets/tilesets/wall.png")); //wall tiles
    }
    catch(IOException e) {
      e.printStackTrace();
    }

    loadMap();
  }

  public void draw(Graphics2D g2) {
    for(int i=0; i<gp.maxScreenRow; i++) {
      for(int j=0; j<gp.maxScreenCol; j++) {
        g2.drawImage(tile[mapTileNum[i][j]].image, j*gp.tileSize, i*gp.tileSize , gp.tileSize , gp.tileSize , null);
      }
    } 
  }

  public void loadMap() {
    try {
      InputStream is = getClass().getResourceAsStream("/maps/map1.txt");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      for(int i=0; i<gp.maxScreenRow; i++) {
        String line = br.readLine();
        for(int j=0; j<gp.maxScreenCol; j++) {
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
