package game;

import javax.swing.JPanel;

import Object.SuperObject;
import entity.Entity;
import entity.Player;
import tiles.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.EventHandler;

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
  KeyHandler keyH = new KeyHandler(this);
  public SoundManager musicManager = new SoundManager();
  public SoundManager soundEffectManager = new SoundManager();
  public ColisionDetector colisionDetector = new ColisionDetector(this);
  public AssetSetter assetSetter = new AssetSetter(this);
  public UImanager uiManager = new UImanager(this);
  public Eventhandler eventhandler = new Eventhandler(this);

  
  //--Game Elements 
  public Player player = new Player(this , keyH);
  TileManager tManager = new TileManager(this);
  public SuperObject obj[] = new SuperObject[10];
  public Entity npc[] = new Entity[10];

  //GAME STATE
  public int gameState;
  public final int titleState = 0;
  public final int playState  =1;
  public final int pauseState = 2;
  public final int dialogueState = 3;

  

  GamePanel(){
    this.setPreferredSize(new Dimension(screenWidth , screenHeight)); 
    this.setDoubleBuffered(true); // every paint will be made on an offscreen buffer before being rendered on actual window
    this.addKeyListener(keyH);
    this.setBackground(Color.black);
    this.setFocusable(true);
  }

  public void setupGame() {
    assetSetter.setObject();
    assetSetter.setNPC();
    //playMusic(0);
    gameState = titleState;
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
    if(gameState == playState) {
      player.update();

      //npc update
      for(int i=0; i<npc.length; i++) {
        if(npc[i] != null) {
          npc[i].update();
        }
      }
      
    }
    else if(gameState == pauseState) {
      //do nothing for now
    }  
    
  }

  public void paintComponent(Graphics g){

    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    if(gameState == titleState) {
        uiManager.draw(g2);
    }
    else {
        //TILE
      tManager.draw(g2);

      //OBJECTS
      for(int i=0; i<obj.length; i++) {
        if(obj[i] != null) {
          obj[i].draw(g2 , this);
        }
      }

      //NPCS
      for(int i=0; i<npc.length; i++) {
        if(npc[i] != null) {
          npc[i].draw(g2);
        }
      }
      //PLAYER
      player.draw(g2);
      //UI Elemets
      uiManager.draw(g2);
      }

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
