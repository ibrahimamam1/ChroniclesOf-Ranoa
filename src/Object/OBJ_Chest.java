package Object;

import entity.Entity;
import game.GamePanel;

public class OBJ_Chest extends Entity{

  Entity loot;
  boolean opened = false;

  public OBJ_Chest(GamePanel gp , Entity loot) {
    super(gp);
    this.loot = loot;
    type = entityType.OBSTACLE;
    name = "Chest";
    image  = setup("/assets/Object/chest" , gp.tileSize , gp.tileSize);
    image2  = setup("/assets/Object/chest_opened" , gp.tileSize , gp.tileSize);
    walkable = false;

    solidArea.x = 4;
    solidArea.y = 16;
    solidArea.width = 40;
    solidArea.height = 32;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
  }

  public void interact(int i) {
    gp.gameState = gp.dialogueState;
    if(opened == false) {
      gp.playSoundEffect(3);
      String text = "";
      

      if(gp.player.inventory.size() >= gp.player.maxInventory) {
        text = "Your Inventory is Full";
      }
      else {
        text = "You Found the " + loot.name;
        gp.player.inventory.add(loot);
        image = image2;
        opened = true;
      }
      gp.uiManager.currentDialogue = text;
    }
  }
  
}
