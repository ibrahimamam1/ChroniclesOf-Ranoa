package game;

import Object.OBJ_Boots;
import Object.OBJ_Chest;
import Object.OBJ_Door;
import Object.OBJ_Key;
import entity.NPC_OLD_MAN ;
import monster.Mon_Green_Slime;


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

  public void setMonster() {
    gp.monster[0] = new Mon_Green_Slime(gp);
    gp.monster[0].worldX = gp.tileSize * 22;
    gp.monster[0].worldY = gp.tileSize * 35;

    gp.monster[1] = new Mon_Green_Slime(gp);
    gp.monster[1].worldX = gp.tileSize * 22;
    gp.monster[1].worldY = gp.tileSize * 37;
  }
}
