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

   //title state
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

   //playState
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
      else if(code == KeyEvent.VK_C){
        gp.gameState = gp.characterStatusState;
      }
   }

   //PAUSE STATE
    else if(gp.gameState == gp.pauseState) {
      if(gp.gameState == gp.pauseState) {
        gp.gameState = gp.playState;
      }
    }

    //DIALOGUE STATE
    else if(gp.gameState == gp.dialogueState) {
      if(code == KeyEvent.VK_ENTER) {
        gp.gameState = gp.playState;
      }
    }

    //CHARACTER STATE
    else if(gp.gameState == gp.characterStatusState) {
      if(code == KeyEvent.VK_C) {
        gp.gameState = gp.playState;
      }
      else if(code == KeyEvent.VK_W) {
        if(gp.uiManager.slotRow != 0) {
          gp.uiManager.slotRow--;
          gp.playSoundEffect(8);
        }
      }
      else if(code == KeyEvent.VK_S) {
        if(gp.uiManager.slotRow != 3) {
          gp.uiManager.slotRow++;
          gp.playSoundEffect(8);
        }
      }
      else if(code == KeyEvent.VK_A) {
        if(gp.uiManager.slotCol != 0) {
          gp.uiManager.slotCol--;
          gp.playSoundEffect(8);
        }
      }
      else if(code == KeyEvent.VK_D) {
        if(gp.uiManager.slotCol != 4) {
          gp.uiManager.slotCol++;
          gp.playSoundEffect(8);
        }
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
