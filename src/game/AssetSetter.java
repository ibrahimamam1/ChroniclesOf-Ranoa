package game;

import Object.OBJ_Bronze_Coin;
import Object.OBJ_Chest;
import Object.OBJ_Door;
import Object.OBJ_Hearth;
import Object.OBJ_Key;
import Object.OBJ_King_Sword;
import Object.OBJ_Mana_Crystal;
import Object.OBJ_Red_Potion;
import entity.NPC_Guru;
import entity.NPC_NOBLE_MAN;
import entity.NPC_OldMan;
import entity.NPC_Peasant;
import interativeTile.ITile_DryTree;
import monster.MON_Bat;
import monster.MON_GreenSlime;
import monster.Mon_Green_Slime;
import monster.Mon_Orc;
import monster.Mon_Skeleton;


public class AssetSetter {

  GamePanel gp;

  AssetSetter(GamePanel gp) {
    this.gp = gp;
  }

  //CREATE AND PLACE ALL OBJECTS
  public void setObject() {
    
    if(gp.currentLevel == 1) {
      setLevelOneObject();
    }
    else if(gp.currentLevel == 2) {
      setLevelTwoObject();
    }
    else if(gp.currentLevel == 3) {
      setLevelThreeObjects();
    }
  }

  //CREATE AND PLACE ALL NPCS
  public void setNPC() {

    if(gp.currentLevel == 1) {
      setLevelOneNPC();
    }
    else if(gp.currentLevel == 2) {
      setLevelTwoNPC();
    }
    else if(gp.currentLevel == 3) {
      setLevelThreeNPC();
    }

  }

  //CREATE AND PLACE ALL MONSTERS
  public void setMonster() {

    if(gp.currentLevel == 1) {
      setLevelOneMonster();
    }
    else if(gp.currentLevel == 2) {
      setLevelTwoMonster();
    }
    else if(gp.currentLevel == 3) {
      setLevelThreeMonster();
    }
    
  }

  public void setLevelOneObject() {

    // int i = 0;
      
    //   gp.obj[i] = new OBJ_Red_Potion(gp);
    //   gp.obj[i].worldX = gp.tileSize*35;
    //   gp.obj[i].worldY = gp.tileSize*36;
    //   i++;
    //   gp.obj[i] = new OBJ_Bronze_Coin(gp);
    //   gp.obj[i].worldX = gp.tileSize*34;
    //   gp.obj[i].worldY = gp.tileSize*38;
    //   i++;  
  
  }
  
  public void setLevelOneNPC() {
      gp.npc[0] = new NPC_Guru(gp);
      gp.npc[0].worldX = gp.tileSize*21;
      gp.npc[0].worldY = gp.tileSize*21;
      
  }
  
  public void setLevelOneMonster() {
  
    int i = 0;
      
      gp.monster[i] = new Mon_Green_Slime(gp);
      gp.monster[i].worldX = gp.tileSize*21;
      gp.monster[i].worldY = gp.tileSize*36;
      i++;
      gp.monster[i] = new Mon_Green_Slime(gp);
      gp.monster[i].worldX = gp.tileSize*23;
      gp.monster[i].worldY = gp.tileSize*44;
      i++;
      gp.monster[i] = new Mon_Green_Slime(gp);
      gp.monster[i].worldX = gp.tileSize*35;
      gp.monster[i].worldY = gp.tileSize*36;
      i++;
      gp.monster[i] = new Mon_Green_Slime(gp);
      gp.monster[i].worldX = gp.tileSize*37;
      gp.monster[i].worldY = gp.tileSize*40;
      i++;
    
  
  }

  public void setLevelTwoObject() {

    	int i=0;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = 23 * gp.tileSize;
        gp.obj[i].worldY = 7 * gp.tileSize;
        i++;

        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = 23 * gp.tileSize;
        gp.obj[i].worldY = 40 * gp.tileSize;
        i++;

        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = 38 * gp.tileSize;
        gp.obj[i].worldY = 8 * gp.tileSize;
        i++;
        
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = 11 * gp.tileSize;
        gp.obj[i].worldY = 11 * gp.tileSize;
        i++;

        gp.obj[i] = new OBJ_Door(gp);
        gp.obj[i].worldX = 35 * gp.tileSize;
        gp.obj[i].worldY = 39* gp.tileSize;
        i++;
        

        
        gp.obj[i] = new OBJ_Red_Potion(gp);
        gp.obj[i].worldX = 23* gp.tileSize;
        gp.obj[i].worldY = 27* gp.tileSize;
        i++;

        gp.obj[i] = new OBJ_Door(gp);
        gp.obj[i].worldX = 38 * gp.tileSize;
        gp.obj[i].worldY = 14 * gp.tileSize;
        i++;
        
//        gp.obj[6] = new OBJ_Door(gp);
//        gp.obj[6].worldX = 16 * gp.tileSize;
//        gp.obj[6].worldY = 22* gp.tileSize;

        gp.obj[i] = new OBJ_Chest(gp , new OBJ_King_Sword(gp));
        gp.obj[i].worldX = 35 * gp.tileSize;
        gp.obj[i].worldY = 42 * gp.tileSize;
        i++;
        
//        gp.obj[7] = new OBJ_Boots(gp);
//        gp.obj[7].worldX = 7 * gp.tileSize;
//        gp.obj[7].worldY = 10* gp.tileSize;
        
    }
    
    public void setLevelTwoNPC()
    {
    	gp.npc[0]= new NPC_OldMan(gp);
    	gp.npc[0].worldX = gp.tileSize*21;
    	gp.npc[0].worldY = gp.tileSize*21;		
    }

    public void setLevelTwoMonster() {
    	gp.monster[0]=new MON_GreenSlime(gp);
    	gp.monster[0].worldX =gp.tileSize*40;
    	gp.monster[0].worldY =gp.tileSize*35;
    	
    	gp.monster[1]=new MON_GreenSlime(gp);
    	gp.monster[1].worldX =gp.tileSize*36;
    	gp.monster[1].worldY =gp.tileSize*38;
    	
    	gp.monster[2]= new MON_GreenSlime(gp);
    	gp.monster[2].worldX =gp.tileSize*22;
    	gp.monster[2].worldY =gp.tileSize*38;
    	
    	gp.monster[3]= new MON_GreenSlime(gp);
    	gp.monster[3].worldX =gp.tileSize*24;
    	gp.monster[3].worldY =gp.tileSize*8;
    	
    	gp.monster[4]= new MON_GreenSlime(gp);
    	gp.monster[4].worldX =gp.tileSize*37;
    	gp.monster[4].worldY =gp.tileSize*8;
    	
    	gp.monster[5]= new MON_GreenSlime(gp);
    	gp.monster[5].worldX =gp.tileSize*36;
    	gp.monster[5].worldY =gp.tileSize*42;
    	
    	gp.monster[6]= new MON_GreenSlime(gp);
    	gp.monster[6].worldX =gp.tileSize*12;
    	gp.monster[6].worldY =gp.tileSize*11;
    	
    	gp.monster[7]= new MON_Bat(gp);
    	gp.monster[7].worldX =gp.tileSize*37;
    	gp.monster[7].worldY =gp.tileSize*42;
    	
    	gp.monster[8]= new MON_Bat(gp);
    	gp.monster[8].worldX =gp.tileSize*10;
    	gp.monster[8].worldY =gp.tileSize*11;
}
  

public void setLevelThreeNPC() {
  
  int i=0;
  gp.npc[i] =  new NPC_Peasant(gp);
  gp.npc[i].worldX = gp.tileSize*32;
  gp.npc[i].worldY = gp.tileSize*7;
  i++;

}

public void setLevelThreeObjects() {

}

public void setLevelThreeMonster() {

  int i = 0;

    gp.monster[i] = new Mon_Orc(gp);
    gp.monster[i].worldX = gp.tileSize * 33;
    gp.monster[i].worldY = gp.tileSize * 16;
    i++;

    gp.monster[i] = new Mon_Skeleton(gp);
    gp.monster[i].worldX = gp.tileSize * 15;
    gp.monster[i].worldY = gp.tileSize * 13;
    i++;
}

  public void setInteractiveTiles() {

    if(gp.currentLevel == 1) {
      setLevelOneInteractiveTiles();
    }

    if(gp.currentLevel == 2) {
      setLevelTwoInteractiveTiles();
    }
  }
  public void setLevelOneInteractiveTiles() {
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

  public void setLevelTwoInteractiveTiles() {
    int i = 0;

    gp.interactiveTile[i] = new ITile_DryTree(gp , 24 , 14);
    i++;

    gp.interactiveTile[i] = new ITile_DryTree(gp , 24 , 15);
    i++;

    gp.interactiveTile[i] = new ITile_DryTree(gp , 24 , 16);
    i++;

   
  }

  
}
