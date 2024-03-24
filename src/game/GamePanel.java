package game;

import javax.swing.JPanel;

import entity.Player;
import tiles.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
  final int originalTileSize = 16; //16 px
  final int scale = 3; 
  public  final int tileSize = originalTileSize * scale; //each object will be 16 * 3 = 48 px
  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12;
  final int screenWidth = maxScreenCol  * tileSize;
  final int screenHeight = maxScreenRow * tileSize;
  final int FPS = 60; //represh rate of 60 frames per second

  Thread gameThread;
  KeyHandler keyH = new KeyHandler();

  GamePanel(){
    this.setPreferredSize(new Dimension(screenWidth , screenHeight)); 
    this.setDoubleBuffered(true); // every paint will be made on an offscreen buffer before being rendered on actual window
    this.addKeyListener(keyH);
    this.setFocusable(true);
    startGameThread();
  }

  
  public void startGameThread(){
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    double drawInterval = 1000000000/FPS; //1s expressed as nanoseconds / FRAME RATE
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    while(gameThread != null)
    {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime)/drawInterval;
      lastTime = currentTime;
      if(delta >= 1){
        update();
        repaint();
        delta--; 
      }
      
    }

  }

  //instatianting game elements  
  Player player = new Player(this , keyH);
  TileManager tManager = new TileManager(this);


  public void update(){
    player.update();
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    tManager.draw(g2);
    player.draw(g2);
    g2.dispose();
  }
}
