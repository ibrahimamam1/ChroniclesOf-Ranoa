package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Object.OBJ_Key;

public class UImanager {
  GamePanel gp;
  Graphics2D g2;
  Font arial30 , arial80B;

  String message = "";
  boolean messageOn = false;
  int messageCounter = 0;
  public boolean gameFinished = false;

  public UImanager(GamePanel gp) {
    this.gp =  gp;
    arial30 = new Font("Arial" , Font.PLAIN , 30);
    arial80B = new Font("Arial" , Font.BOLD , 80);
  }

  public void showMessage(String text) {
    message = text;
    messageOn = true;
  }
  public void draw(Graphics2D g2) {
    this.g2 = g2;
    g2.setFont(arial30);
    g2.setColor(Color.white);

    if(gp.gameState == gp.playState) {
      //do something later
    }
    if(gp.gameState == gp.pauseState) {
      drawPauseScreen();
    }
  }

  public void drawPauseScreen() {
    String text = "PAUSED";
    int x = getXForCenteredText(text);
    int y = gp.screenHeight/2;
    g2.drawString(text , x , y);
  }

  public int getXForCenteredText(String text) {
    int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    int x = gp.screenWidth/2 - length/2;
    return x;
  }
    
    
}
