package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_Door extends Entity{

  public OBJ_Door(GamePanel gp) {
    super(gp);
    name = "Door";
    type = entityType.OBSTACLE;
    image = setup("/assets/Object/door" , gp.tileSize , gp.tileSize);
  }

  public void interact(int index) {

    gp.gameState = gp.dialogueState;
    if(gp.player.hasKey > 0) {
      gp.uiManager.currentDialogue = "You Used a key";
      gp.player.useKey();
      gp.obj[index] = null;
    }
    else {
      gp.uiManager.currentDialogue = "You need a key to open this";
    }
    
  }
  
}
