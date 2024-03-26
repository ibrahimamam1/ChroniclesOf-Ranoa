package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Object.OBJ_Key;

public class UImanager {
  GamePanel gp;
  Font arial30 , arial80B;
  BufferedImage keyImage;
  String message = "";
  boolean messageOn = false;
  int messageCounter = 0;
  public boolean gameFinished = false;

  public UImanager(GamePanel gp) {
    this.gp =  gp;
    arial30 = new Font("Arial" , Font.PLAIN , 30);
    arial80B = new Font("Arial" , Font.BOLD , 80);
    OBJ_Key key = new OBJ_Key();
    keyImage = key.image;
  }

  public void showMessage(String text) {
    message = text;
    messageOn = true;
  }
  public void draw(Graphics2D g2) {
    if(!gameFinished) {
      g2.setFont(arial30);
      g2.setColor(Color.white);
      g2.drawImage(keyImage , gp.tileSize/2 , gp.tileSize/2 , gp.tileSize , gp.tileSize , null);
      g2.drawString("x" + gp.player.hasKey, 74, 50);

      if(messageOn) {
        g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
        messageCounter++;
        if(messageCounter >  120) {
          messageCounter = 0;
          messageOn = false;
        }
      }
    }

    else {
      g2.setFont(arial30);
      g2.setColor(Color.white);
      String text;
      int textLength;
      int x , y;
      text = "You found the treasure";
      textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
      x = gp.screenWidth/2 - (textLength/2);
      y = gp.screenWidth/2 - (gp.tileSize * 3);
      g2.drawString(text , x , y);

      g2.setFont(arial80B);
      g2.setColor(Color.white);
      text = "Congratulations";
      textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
      x = gp.screenWidth/2 - (textLength/2);
      y = gp.screenWidth/2 - (gp.tileSize * 5);
      g2.drawString(text , x , y);

      gp.gameThread = null;
    }
  }
    
    
}
