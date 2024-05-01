package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class UImanager {

  GamePanel gp;
  Graphics2D g2;
  Font arial30 , arial80B;

  public String currentDialogue = "";

  //ON SCREEN MESSAGES
  public ArrayList<String> message = new ArrayList<>();
  ArrayList<Integer>messageCounter = new ArrayList<>();

  public int menuOption = 0;


  //INVENTORY CURSOR
  public int slotCol = 0;
  public int  slotRow  =0;

  public UImanager(GamePanel gp) {
    this.gp =  gp;
    arial30 = new Font("Arial" , Font.PLAIN , 30);
    arial80B = new Font("Arial" , Font.BOLD , 80);
    
  }

  public void addMessage(String text) {

    message.add(text);
    messageCounter.add(0);

  }

  public void draw(Graphics2D g2) {

    this.g2 = g2;
    g2.setFont(arial30);
    g2.setColor(Color.white);

    if(gp.gameState == gp.playState) {
      drawPlayerLife(g2);
      drawMessage(g2);
    }

    else if(gp.gameState == gp.pauseState) {
      drawPauseScreen();
      drawPlayerLife(g2);
    }

    else if(gp.gameState == gp.dialogueState) {
      drawDialogueWindow();
    }

    else if(gp.gameState == gp.characterStatusState) {
      drawCharacterStatusFrame(g2);
      drawInventory(g2);
    }

    else if(gp.gameState == gp.gameOverState) {
      drawGameOverScreen(g2);
    }

  }
  
  public void drawTitleScreen(Graphics2D g2) {

    this.g2 = g2;
    
    //TITLE
    g2.setFont(g2.getFont().deriveFont(Font.BOLD , 60F));
    String text = "Tales Of Mysticiya";
    int x = getXForCenteredText(text);
    int y = gp.tileSize*3;

    //TEXT SHADOW
    g2.setColor(Color.gray);
    g2.drawString(text , x+5 , y+5);

    //MAIN TEXT
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

    int x = gp.tileSize/2;
    int y = gp.tileSize/2;

    int frameLength = gp.tileSize * (gp.player.maxlife/10);
    double oneHealthUnit = (double)frameLength/gp.player.maxlife;
    double hpBarValue = oneHealthUnit * gp.player.life;

    g2.setColor(new Color(35 , 35 , 35));
    g2.fillRect(x, y, frameLength , 10);

    g2.setColor(new Color(255 , 0 , 30));
    g2.fillRect(x, y,(int)hpBarValue, 12);
    

    //DRAW MAX MANA
    x = gp.tileSize/2;
    y = gp.tileSize*2;
    frameLength = gp.tileSize * (gp.player.maxMana/10);

    double oneManaUnit = (double)frameLength/gp.player.maxMana;
    double manaBarValue = oneManaUnit * gp.player.mana;

    g2.setColor(new Color(35 , 35 , 35));
    g2.fillRect(x, y, frameLength , 10);

    g2.setColor(new Color(122 , 192 , 224));
    g2.fillRect(x, y,(int)manaBarValue, 12);
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

    g2.drawString("mana ", textX, textY);
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

    value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
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
    final int slotXStart = frameX + 20;
    final int slotYStart = frameY +  20;
    int slotX = slotXStart;
    int slotY = slotYStart;
    int slotSize = gp.tileSize + 3;

    //CURSOR
    int cursorX = slotXStart + (slotSize * slotCol);
    int cursorY = slotYStart + (slotSize * slotRow);
    int cursorWidth = gp.tileSize;
    int cursorHeight = gp.tileSize;

    //DRAW Player's ITEMS
    for(int i=0; i<gp.player.inventory.size(); i++) {

      //EQUIPED ITEM
      if(gp.player.inventory.get(i) == gp.player.currentWeapon) {

        g2.setColor(new Color(240 , 90 , 90));
        g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);

      }

      //OTHER ITEMS
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

    //DRAW DESCRIPTION TEXT
    int textX = dFrameX + 20;
    int textY = dFrameY + gp.tileSize;
    g2.setFont(g2.getFont().deriveFont(20F));

    int itemIndex = getItemIndexOnSlot();

    if(itemIndex < gp.player.inventory.size()) {

      drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
      for(String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
        g2.drawString(line, textX, textY);
        textY += 32;
      }

    }
  }

  public void drawGameOverScreen(Graphics2D g2) {

    g2.setColor(new Color(0 , 0 , 0 , 150));
    g2.fillRect(0 , 0, gp.screenWidth, gp.screenHeight);

    int x;
    int y;

    String text;
    g2.setFont(g2.getFont().deriveFont(Font.BOLD , 110f));
    text = "YOU DIED";

    //SHADOW
    g2.setColor(Color.black);
    x = getXForCenteredText(text);
    y = gp.tileSize * 4;

    //MAIN
    g2.setColor(Color.white);
    g2.drawString(text , x-4 , y-4);


    //RETRY
    g2.setFont(g2.getFont().deriveFont(50f));
    text = "Retry";
    x = getXForCenteredText(text);
    y += gp.tileSize*4;
    g2.drawString(text, x, y);

    if(menuOption == 0) {
      g2.drawString(">", x-40, y);
    }

    //Back to title Screen
    text = "Quit";
    x = getXForCenteredText(text);
    y += 55;
    g2.drawString(text, x, y);

    if(menuOption == 1) {
      g2.drawString(">", x-40, y);
    }
  }
  public void drawMessage(Graphics2D g2) {
  
    int messageX = gp.tileSize;
    int messageY = gp.tileSize*4;

    g2.setFont(g2.getFont().deriveFont(Font.BOLD , 20F));

    for(int i=0; i < message.size(); i++) {

      if(message.get(i) != null) {

        g2.setColor(Color.white);
        g2.drawString(message.get(i), messageX, messageY);

        int counter = messageCounter.get(i) + 1;
        messageCounter.set(i , counter);

        if(messageCounter.get(i) > 120) {
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
