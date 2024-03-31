package game;

import Object.OBJ_Boots;
import Object.OBJ_Chest;
import Object.OBJ_Door;
import Object.OBJ_Key;
import entity.NPC_OLD_MAN ;


public class AssetSetter {
  GamePanel gp;

  AssetSetter(GamePanel gp) {
    this.gp = gp;
  }

  public void setObject() {
    
  }
  public void setNPC() {
    gp.npc[0] =  new NPC_OLD_MAN(gp);
    gp.npc[0].worldX = gp.tileSize*21;
    gp.npc[0].worldY = gp.tileSize*21;
  }
}
