package game;

import java.awt.BasicStroke;
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
  public String currentDialogue = "";

  public int menuOption = 0;

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

    if(gp.gameState == gp.titleState) {
      drawTitleScreen(g2);
    }

    if(gp.gameState == gp.playState) {
      //do something later
    }
    if(gp.gameState == gp.pauseState) {
      drawPauseScreen();
    }
    if(gp.gameState == gp.dialogueState) {
      drawDialogueWindow();
    }

  }
  
  public void drawTitleScreen(Graphics2D g2) {
    //title Name
    g2.setFont(g2.getFont().deriveFont(Font.BOLD , 60F));
    String text = "Chronicles Of Ranoa";
    int x = getXForCenteredText(text);
    int y = gp.tileSize*3;

    //draw text shadow
    g2.setColor(Color.gray);
    g2.drawString(text , x+5 , y+5);

    //draw main text 
    g2.setColor(Color.white);
    g2.drawString(text , x , y);


    //MENU
    g2.setFont(g2.getFont().deriveFont(Font.BOLD , 48F));
    text = "NEW GAME";
    x = getXForCenteredText(text);
    y += gp.tileSize*4;

    if(menuOption == 0) {
      g2.drawString(">", x-gp.tileSize, y);
    }

    g2.drawString(text, x, y);

    text = "LOAD GAME";
    x = getXForCenteredText(text);
    y += gp.tileSize;

    if(menuOption == 1) {
      g2.drawString(">", x-gp.tileSize, y);
    } 

    g2.drawString(text, x, y);


    text = "EXIT GAME";
    x = getXForCenteredText(text);
    y += gp.tileSize;

    if(menuOption == 2) {
      g2.drawString(">", x-gp.tileSize, y);
    }
    g2.drawString(text, x, y);
  }
  public void drawDialogueWindow() {
    //WINDOW

    int x = gp.tileSize/2;
    int y = gp.tileSize/2;
    int width = gp.screenWidth - (gp.tileSize*4);
    int height = gp.tileSize * 5;

    drawSubWindow(x , y ,width , height);

    x += gp.tileSize;
    y += gp.tileSize;

    for(String line : currentDialogue.split("\n")) {
      g2.drawString(line, x, y);
      y += 40;
    }
   
  }

  public void drawSubWindow(int x , int y , int w , int h) {
    Color c = new Color(0 ,0 , 0 , 210);
    g2.setColor(c);
    g2.fillRoundRect(x, y, w, h, 35, 35);

    c = new Color(255 , 255 , 255);
    g2.setColor(c);
    g2.setStroke(new BasicStroke(5));
    g2.drawRoundRect(x+5, y+5, w, h-10, 25, 25);
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
