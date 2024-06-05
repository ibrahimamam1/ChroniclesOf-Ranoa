package game;

public class Eventhandler {
  GamePanel gp;
  public EventRect eventRect[][];
  public int previousEventX;
  public int previousEventY;
  public boolean canTouchEvent = true;

  Eventhandler(GamePanel gp) {

    this.gp = gp;
    eventRect = new EventRect[gp.maxWorldRow][gp.maxWorldCol];
    
    for(int i=0; i < gp.maxWorldRow; i++) {

      for(int j=0; j < gp.maxWorldCol; j++) {

        eventRect[i][j] = new EventRect();
        eventRect[i][j].x = 23;
        eventRect[i][j].y = 23;
        eventRect[i][j].width  = 10;
        eventRect[i][j].height = 10;
        eventRect[i][j].eventRectDefaultX = eventRect[i][j].x;
        eventRect[i][j].eventRectDefaultY = eventRect[i][j].y;

      }
    }
  }

  public void checKEvent() {

    if(canTouchEvent == true) {

      //if the player hits a specific tile we launch a specific event

      if(hit(26 , 15 , "right") == true) {
        damagePit(26 , 15 , gp.dialogueState);
      }
      if(hit(22 , 12 , "up") == true) {
        healingPool(23 , 11 , gp.dialogueState);
      }

    }
    

    if(canTouchEvent == false) //we allow the event occur again if the player moves at least by 1 tile
    {
       //get distance between now and previous event
      int disX = Math.abs(previousEventX - gp.player.worldX);
      int disY =Math.abs(previousEventY - gp.player.worldY);
      int dis = Math.max(disX, disY);

      if(dis > gp.tileSize){
        canTouchEvent = true;
      }
    }
    
  }

  public boolean hit(int col , int row , String reqDirection) {

    boolean hit = false;
    gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
    gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
    eventRect[row][col].x += col * gp.tileSize;
    eventRect[row][col].y += row * gp.tileSize;

    if(gp.player.solidArea.intersects(eventRect[row][col])) {
      if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
        hit = true;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
      }
    }

    gp.player.solidArea.x = gp.player.solidAreaDefaultX;
    gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    eventRect[row][col].x = eventRect[row][col].eventRectDefaultX;
    eventRect[row][col].y = eventRect[row][col].eventRectDefaultY;

    return hit;
  }

  public void damagePit(int row , int col , int gameState) {

    gp.uiManager.addMessage("Hidden Pit Damage");
    gp.playSoundEffect(4);
    gp.player.life -= 1;
    canTouchEvent = false;

  }

  public void healingPool(int row , int col , int gameState) {

    if(gp.keyH.enterPressed == true) {
      gp.player.attackCancel = true;
      gp.gameState = gameState;
      gp.uiManager.currentDialogue = "Healing Pool\nLife and Mana Restored";
      gp.player.life = gp.player.maxlife;
      gp.player.mana = gp.player.maxMana;
      gp.assetSetter.setMonster();
    }
  }


}
