package game;

import javax.swing.JPanel;

import Object.SuperObject;
import entity.Player;
import tiles.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{

  //--SCREEN SETTINGS --

  final int originalTileSize = 16; //16 px
  final int scale = 3; 
  public  final int tileSize = originalTileSize * scale; //each object will be 16 * 3 = 48 px
  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12;
  public final int screenWidth = maxScreenCol  * tileSize;  //768px
  public final int screenHeight = maxScreenRow * tileSize; //576px

  //FPS
  final int FPS = 60; //represh rate of 60 frames per second

  //--WORLD SETTINGS
  public final int maxWorldCol = 50;
  public final int maxWorldRow = 50; //50 * 50 map
  public final int worldWidth = tileSize * maxScreenCol;
  public final int worldHeight = tileSize * maxScreenRow;


  //Controllers
  Thread gameThread;
  KeyHandler keyH = new KeyHandler();
  public SoundManager musicManager = new SoundManager();
  public SoundManager soundEffectManager = new SoundManager();
  public ColisionDetector colisionDetector = new ColisionDetector(this);
  public AssetSetter assetSetter = new AssetSetter(this);
  public UImanager uiManager = new UImanager(this);

  
  //--Game Elements 
  public Player player = new Player(this , keyH);
  TileManager tManager = new TileManager(this);
  public SuperObject obj[] = new SuperObject[10];

  

  GamePanel(){
    this.setPreferredSize(new Dimension(screenWidth , screenHeight)); 
    this.setDoubleBuffered(true); // every paint will be made on an offscreen buffer before being rendered on actual window
    this.addKeyListener(keyH);
    this.setFocusable(true);
  }

  public void setupGame() {
    assetSetter.setObject();
    playMusic(0);
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

  public void update(){
    player.update();
  }

  public void paintComponent(Graphics g){

    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    //TILE
    tManager.draw(g2);

    //OBJECTS
    for(int i=0; i<obj.length; i++) {
      if(obj[i] != null) {
        obj[i].draw(g2 , this);
      }
    }

    //PLAYER
    player.draw(g2);

    //UI Elemets
    uiManager.draw(g2);

    g2.dispose();
  }

  public void playMusic(int i) {
    musicManager.setFile(i);
    musicManager.play();
    musicManager.loop();
  }
  public void stopMusic() {
    musicManager.stop();
  }
  public void playSoundEffect(int i) {
    soundEffectManager.setFile(i);
    soundEffectManager.play();
  }
}
