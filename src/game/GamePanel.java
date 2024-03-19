package game;

import javax.swing.JPanel;
import java.awt.Dimension;

public class GamePanel extends JPanel implements Runnable{
  final int originalTileSize = 16; //16 px
  final int scale = 3; 
  final int tileSize = originalTileSize * scale; //each object will be 16 * 3 = 48 px
  final int maxScreenCol = 16;
  final int maxScreenRow = 12;
  final int screenWidth = maxScreenCol  * tileSize;
  final int screenHeight = maxScreenRow * tileSize;

  GamePanel(){
    this.setPreferredSize(new Dimension(screenWidth , screenHeight)); 
    this.setDoubleBuffered(true); // every paint will be made on an offscreen buffer before being rendered on actual window
  }

  Thread gameThread;
  public void startGameThread(){
    gameThread = new Thread(this);
    gameThread.start();
  }
  @Override
  public void run() {
    System.out.println("Game Thread Running");
  }
}
