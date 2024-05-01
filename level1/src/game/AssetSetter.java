package game;

import entity.NPC_NOBLE_MAN;
import entity.NPC_OLD_MAN ;
import entity.NPC_Peasant;
import interativeTile.ITile_DryTree;
import monster.Mon_Green_Slime;


public class AssetSetter {

  GamePanel gp;

  AssetSetter(GamePanel gp) {
    this.gp = gp;
  }

  //CREATE AND PLACE ALL OBJECTS
  public void setObject() {
    
    
  }

  //CREATE AND PLACE ALL NPCS
  public void setNPC() {

    int i=0;
    gp.npc[i] =  new NPC_NOBLE_MAN(gp);
    gp.npc[i].worldX = gp.tileSize*21;
    gp.npc[i].worldY = gp.tileSize*21;
    i++;

    gp.npc[i] =  new NPC_Peasant(gp);
    gp.npc[i].worldX = gp.tileSize*21;
    gp.npc[i].worldY = gp.tileSize*30;
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

    gp.monster[i] = new Mon_Green_Slime(gp);
    gp.monster[i].worldX = gp.tileSize * 25;
    gp.monster[i].worldY = gp.tileSize * 37;
    i++;

    gp.monster[i] = new Mon_Green_Slime(gp);
    gp.monster[i].worldX = gp.tileSize * 37;
    gp.monster[i].worldY = gp.tileSize * 37;
    i++;

    gp.monster[i] = new Mon_Green_Slime(gp);
    gp.monster[i].worldX = gp.tileSize * 35;
    gp.monster[i].worldY = gp.tileSize * 37;
    i++;

    
  }

  public void setInteractiveTiles() {
    int i = 0;

    gp.interactiveTile[i] = new ITile_DryTree(gp , 27 , 12);
    i++;

    gp.interactiveTile[i] = new ITile_DryTree(gp , 28 , 12);
    i++;

    gp.interactiveTile[i] = new ITile_DryTree(gp , 29 , 12);
    i++;

    gp.interactiveTile[i] = new ITile_DryTree(gp , 30 , 12);
    i++;

    gp.interactiveTile[i] = new ITile_DryTree(gp , 31 , 12);
    i++;

    gp.interactiveTile[i] = new ITile_DryTree(gp , 32 , 12);
    i++;

    gp.interactiveTile[i] = new ITile_DryTree(gp , 33 , 12);
    i++;
  }
}
