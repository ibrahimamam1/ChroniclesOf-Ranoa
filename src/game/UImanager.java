package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Object.OBJ_Hearth;
import Object.OBJ_Key;
import entity.Entity;

public class UImanager {
  GamePanel gp;
  Graphics2D g2;
  Font arial30 , arial80B;

  boolean messageOn = false;
  public String currentDialogue = "";

  ArrayList<String> message = new ArrayList<>();
  ArrayList<Integer>messageCounter = new ArrayList<>();

  public int menuOption = 0;

  public BufferedImage hearth_blank , hearth_half , hearth_full; 
  public int slotCol = 0;
  public int  slotRow  =0;

  public UImanager(GamePanel gp) {
    this.gp =  gp;
    arial30 = new Font("Arial" , Font.PLAIN , 30);
    arial80B = new Font("Arial" , Font.BOLD , 80);

    //HUD Objects
    Entity hearth = new OBJ_Hearth(gp);
    hearth_blank = hearth.image;
    hearth_half = hearth.image2;
    hearth_full = hearth.image3;
  }

  public void addMessage(String text) {
    message.add(text);
    messageCounter.add(0);
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
      drawPlayerLife(g2);
      drawMessage(g2);
    }
    if(gp.gameState == gp.pauseState) {
      drawPauseScreen();
      drawPlayerLife(g2);
    }
    if(gp.gameState == gp.dialogueState) {
      drawDialogueWindow();
      drawPlayerLife(g2);
    }
    if(gp.gameState == gp.characterStatusState) {
      drawCharacterStatusFrame(g2);
      drawInventory(g2);
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

  public void drawPlayerLife(Graphics2D g2) {

    //DRAW MAXLIFE
    int x = gp.tileSize/2;
    int y = gp.tileSize/2;
    int i=0;

    while(i<gp.player.maxlife/2) {
      g2.drawImage(hearth_blank, x, y , null);
      i++;
      x += gp.tileSize;
    }

    x = gp.tileSize/2;
    y = gp.tileSize/2;
    i = 0;

    //DRAW CURRENT LIFE
    while(i < gp.player.life) {
      g2.drawImage(hearth_half, x , y , null);
      i++;
      if( i < gp.player.life) {
        g2.drawImage(hearth_full, x , y , null);
      }
      i++;
      x += gp.tileSize;
    }

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

  public void drawCharacterStatusFrame(Graphics2D g2) {

    final int frameX = gp.tileSize * 2;
    final int frameY = gp.tileSize;
    final int frameWidth = gp.tileSize*5;
    final int frameheight = gp.tileSize * 10;

    drawSubWindow(frameX, frameY, frameWidth, frameheight);

    //TEXT
    g2.setColor(Color.white);
    g2.setFont(g2.getFont().deriveFont(20F));

    int textX = frameX + 20;
    int textY = frameY + gp.tileSize;
    final int lineHeight = 35;

    g2.drawString("Level ", textX, textY);
    textY += lineHeight;

    g2.drawString("life ", textX, textY);
    textY += lineHeight;

    g2.drawString("Strength ", textX, textY);
    textY += lineHeight;

    g2.drawString("Dexterity ", textX, textY);
    textY += lineHeight;

    g2.drawString("Attack ", textX, textY);
    textY += lineHeight;

    g2.drawString("Defense ", textX, textY);
    textY += lineHeight;

    g2.drawString("Exp ", textX, textY);
    textY += lineHeight;

    g2.drawString("Next Level ", textX, textY);
    textY += lineHeight;

    g2.drawString("Coin ", textX, textY);
    textY += lineHeight;

    //VALUES
    int tailX = (frameX + frameWidth) - 30;
    textY = frameY + gp.tileSize;
    String value;

    value = String.valueOf(gp.player.level);
    textX = getXForRightAlignText(value , tailX);
    g2.drawString(value, textX, textY);
    textY += lineHeight;

    value = String.valueOf(gp.player.life + "/" + gp.player.maxlife);
    textX = getXForRightAlignText(value , tailX);
    g2.drawString(value, textX, textY);
    textY += lineHeight;

    value = String.valueOf(gp.player.strength);
    textX = getXForRightAlignText(value , tailX);
    g2.drawString(value, textX, textY);
    textY += lineHeight;

    value = String.valueOf(gp.player.dexterity);
    textX = getXForRightAlignText(value , tailX);
    g2.drawString(value, textX, textY);
    textY += lineHeight;

    value = String.valueOf(gp.player.attack);
    textX = getXForRightAlignText(value , tailX);
    g2.drawString(value, textX, textY);
    textY += lineHeight;

    value = String.valueOf(gp.player.defense);
    textX = getXForRightAlignText(value , tailX);
    g2.drawString(value, textX, textY);
    textY += lineHeight;

    value = String.valueOf(gp.player.exp);
    textX = getXForRightAlignText(value , tailX);
    g2.drawString(value, textX, textY);
    textY += lineHeight;

    value = String.valueOf(gp.player.nextLevelExp);
    textX = getXForRightAlignText(value , tailX);
    g2.drawString(value, textX, textY);
    textY += lineHeight;

    value = String.valueOf(gp.player.coin);
    textX = getXForRightAlignText(value , tailX);
    g2.drawString(value, textX, textY);
    textY += lineHeight;


  }

  public void drawInventory(Graphics2D g2) {
    int frameX =  gp.tileSize*9;
    int frameY = gp.tileSize;
    int frameWidth= gp.tileSize * 6;
    int  frameHeight = gp.tileSize * 5;
    drawSubWindow(frameX  , frameY , frameWidth , frameHeight);

    //SLOT
    final int slotXStart = frameX +20;
    final int slotYStart = frameY +  20;
    int slotX = slotXStart;
    int slotY = slotYStart;
    int slotSize = gp.tileSize + 3;

    //CURSOR
    int cursorX = slotXStart + (slotSize * slotCol);
    int cursorY = slotYStart + (slotSize * slotRow);
    int cursorWidth = gp.tileSize;
    int cursorHeight = gp.tileSize;

    //DRAW ITEMS
    for(int i=0; i<gp.player.inventory.size(); i++) {
      g2.drawImage(gp.player.inventory.get(i).image , slotX , slotY , gp.tileSize , gp.tileSize , null);
      slotX += slotSize;
      if(i == 4 || i == 9 || i == 14) {
        slotX =  slotXStart;
        slotY += gp.tileSize;
      }
    }
    //DRAW CURSOR
    g2.setColor(Color.white);
    g2.setStroke(new BasicStroke(3));
    g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

    //DRAW DESCRIPTION BOX
    int dFrameX = frameX;
    int dFrameY = frameY + frameHeight;
    int dFrameWidth = frameWidth;
    int dFrameHeight = gp.tileSize * 3;
    drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

    //DRAW DESCRIPTION TEXT
    int textX = dFrameX + 20;
    int textY = dFrameY + gp.tileSize;
    g2.setFont(g2.getFont().deriveFont(20F));
    int itemIndex = getItemIndexOnSlot();

    if(itemIndex < gp.player.inventory.size()) {
      for(String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
        g2.drawString(line, textX, textY);
        textY += 32;
      }
    }
  }

  public void drawMessage(Graphics2D g2) {
  
    int messageX = gp.tileSize;
    int messageY = gp.tileSize*4;
    g2.setFont(g2.getFont().deriveFont(Font.BOLD , 28F));

    for(int i=0; i < message.size(); i++) {
      if(message.get(i) != null) {
        g2.setColor(Color.white);
        g2.drawString(message.get(i), messageX, messageY);
        int counter = messageCounter.get(i) + 1;
        messageCounter.set(i , counter);
        if(messageCounter.get(i) > 180) {
          message.remove(i);
          messageCounter.remove(i);
        }
      }
    }
  }

  public int getItemIndexOnSlot() {
    return slotCol + (slotRow * 5);
  }
  public int getXForCenteredText(String text) {
    int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    int x = gp.screenWidth/2 - length/2;
    return x;
  }

  public int getXForRightAlignText(String text , int tailX) {
    int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    int x = tailX - length;
    return x;
  }
    
    
}
