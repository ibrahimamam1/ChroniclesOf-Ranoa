package game;

import Object.OBJ_Boots;
import Object.OBJ_Chest;
import Object.OBJ_Door;
import Object.OBJ_Key;
import Object.OBJ_King_Sword;
import Object.OBJ_Red_Potion;
import entity.NPC_OLD_MAN ;
import monster.Mon_Green_Slime;



public class AssetSetter {
  GamePanel gp;

  AssetSetter(GamePanel gp) {
    this.gp = gp;
  }

  public void setObject() {
    int i=0;

    gp.obj[i] = new OBJ_King_Sword(gp); 
    gp.obj[i].worldX = gp.tileSize*24;
    gp.obj[i].worldY = gp.tileSize*21;
    i++;

    gp.obj[i] = new OBJ_Red_Potion(gp); 
    gp.obj[i].worldX = gp.tileSize*30;
    gp.obj[i].worldY = gp.tileSize*21;
    i++;
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
