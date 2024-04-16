package game;

import Object.OBJ_Bronze_Coin;
import Object.OBJ_King_Sword;
import Object.OBJ_Red_Potion;
import entity.NPC_OLD_MAN ;
import monster.Mon_Green_Slime;



public class AssetSetter {

  GamePanel gp;

  AssetSetter(GamePanel gp) {
    this.gp = gp;
  }

  //CREATE AND PLACE ALL OBJECTS
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

    gp.obj[i] = new OBJ_Bronze_Coin(gp); 
    gp.obj[i].worldX = gp.tileSize*23;
    gp.obj[i].worldY = gp.tileSize*26;
    i++;
  }

  //CREATE AND PLACE ALL NPCS
  public void setNPC() {

    int i=0;
    gp.npc[i] =  new NPC_OLD_MAN(gp);
    gp.npc[i].worldX = gp.tileSize*21;
    gp.npc[i].worldY = gp.tileSize*21;
    i++;

  }

  //CREATE AND PLACE ALL MONSTERS
  public void setMonster() {

    int i = 0;
    gp.monster[i] = new Mon_Green_Slime(gp);
    gp.monster[i].worldX = gp.tileSize * 22;
    gp.monster[i].worldY = gp.tileSize * 35;
    i++;

    gp.monster[i] = new Mon_Green_Slime(gp);
    gp.monster[i].worldX = gp.tileSize * 22;
    gp.monster[i].worldY = gp.tileSize * 37;
    i++;
    
  }
}
