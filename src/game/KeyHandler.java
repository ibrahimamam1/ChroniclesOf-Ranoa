package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

  public boolean upPressed = false , downPressed = false  , leftPressed = false , rightPressed = false , enterPressed = false;
  GamePanel gp;

  public KeyHandler(GamePanel gp) {
    this.gp = gp;
  }

  @Override
  public void keyPressed(KeyEvent e) {
   int code = e.getKeyCode();

   if(gp.gameState == gp.titleState)
   {
      if(code == KeyEvent.VK_W){
        if(gp.uiManager.menuOption > 0)
          gp.uiManager.menuOption--;
      }
      else if(code == KeyEvent.VK_S){
        if(gp.uiManager.menuOption < 2)
          gp.uiManager.menuOption++;
      }
      else if(code == KeyEvent.VK_ENTER){
        if(gp.uiManager.menuOption == 0){ //start game
          gp.gameState = gp.playState;
          gp.playMusic(0);
        }
        else if(gp.uiManager.menuOption == 1){ ///load game
          //later
        }
        if(gp.uiManager.menuOption == 2){ ///exit game
          System.exit(0);
        }
      }
   }
   else if(gp.gameState == gp.playState)
   {
    if(code == KeyEvent.VK_W){
      upPressed = true;
     }
     else if(code == KeyEvent.VK_S){
      downPressed = true;
     }
     else if(code == KeyEvent.VK_A){
      leftPressed = true;
     }
     else if(code == KeyEvent.VK_D){
      rightPressed = true;
     }
     else if(code == KeyEvent.VK_P){
        gp.gameState = gp.pauseState;
      }
      else if(code == KeyEvent.VK_ENTER){
        enterPressed = true;
      }
   }

    else if(gp.gameState == gp.pauseState) {
      if(gp.gameState == gp.pauseState) {
        gp.gameState = gp.playState;
      }
    }

    else if(gp.gameState == gp.dialogueState) {
      if(code == KeyEvent.VK_ENTER) {
        gp.gameState = gp.playState;
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();

   if(code == KeyEvent.VK_W){
    upPressed = false;
   }
   else if(code == KeyEvent.VK_S){
    downPressed = false;
   }
   else if(code == KeyEvent.VK_A){
    leftPressed = false;
   }
   else if(code == KeyEvent.VK_D){
    rightPressed = false;
   }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    
  }
  
}
