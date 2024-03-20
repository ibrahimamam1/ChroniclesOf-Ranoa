package game;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
  final int originalTileSize = 16; //16 px
  final int scale = 3; 
  final int tileSize = originalTileSize * scale; //each object will be 16 * 3 = 48 px
  final int maxScreenCol = 16;
  final int maxScreenRow = 12;
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

  int playerX = 50 , playerY = 50;
  int playerSpeed = 4; // Player moves 4 pxs at a time

  public void update(){
    if(keyH.upPressed == true){
      playerY -= playerSpeed;
    }
    if(keyH.downPressed == true){
      playerY += playerSpeed;
    }
    if(keyH.leftPressed == true){
      playerX -= playerSpeed;
    }
    if(keyH.rightPressed == true){
      playerX += playerSpeed;
    }
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.dispose();
  }
}
