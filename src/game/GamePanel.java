package game;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import entity.Projectile;
import tiles.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
  public KeyHandler keyH = new KeyHandler(this);
  public SoundManager musicManager = new SoundManager();
  public SoundManager soundEffectManager = new SoundManager();
  public ColisionDetector colisionDetector = new ColisionDetector(this);
  public AssetSetter assetSetter = new AssetSetter(this);
  public UImanager uiManager = new UImanager(this);
  public Eventhandler eventhandler = new Eventhandler(this);

  
  //--Game Elements 
  public Player player = new Player(this , keyH);
  TileManager tManager = new TileManager(this);
  public Entity obj[] = new Entity[10];
  public Entity npc[] = new Entity[10];
  public Entity monster[] = new Entity[20];
  public ArrayList<Entity> entityList = new ArrayList<>();
  public ArrayList<Projectile>projectileList = new ArrayList<>();

  //GAME STATE
  public int gameState;
  public final int titleState = 0;
  public final int playState  =1;
  public final int pauseState = 2;
  public final int dialogueState = 3;
  public final int characterStatusState = 4;

  

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
    assetSetter.setMonster();
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

      //monster update
      for(int i=0; i<monster.length; i++) {
        if(monster[i] != null && monster[i].alive == true) {
          monster[i].update();
        }
        if(monster[i] != null && monster[i].alive == false) {
          monster[i].checkDrop();
          monster[i] = null;
        }
      }

      //projectile update
      for(int i=0; i<projectileList.size(); i++) {
        if(projectileList.get(i) != null && projectileList.get(i).alive == true) {
          projectileList.get(i).update();
        }
        else if(projectileList.get(i) != null && projectileList.get(i).alive == false) {
          projectileList.remove(i);
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

      //Add Entities
      entityList.add(player);

      //add npc to entity list
      for(int i=0;i<npc.length; i++) {
        if(npc[i] != null)  {
          entityList.add(npc[i]);
        }
      }

      //add monsters to entity list
      for(int i=0;i<monster.length; i++) {
        if(monster[i] != null)  {
          entityList.add(monster[i]);
        }
      }

      //add projectiles to entity list
      for(int i=0;i<projectileList.size(); i++) {
        if(projectileList.get(i) != null)  {
          entityList.add(projectileList.get(i));
        }
      }

      //add objects to entity list
      for(int i=0; i<obj.length; i++) {
        if(obj[i] != null) {
          entityList.add(obj[i]);
        }
      }

      //sort entitylist based onyposition
      Collections.sort(entityList , new Comparator<Entity>() {

        @Override
        public int compare(Entity e1, Entity e2) {
          int result= Integer.compare(e1.worldY, e2.worldY);
          return result;
        }
        
      });

      //draw entities
      for(int i=0;i<entityList.size();i++){
        entityList.get(i).draw(g2);
      }
      //empty entity list
      for(int i=0;i<entityList.size();i++){
        entityList.remove(i);
      }
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
