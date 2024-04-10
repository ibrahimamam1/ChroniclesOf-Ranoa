package game;

import entity.Entity;

public class ColisionDetector {
  GamePanel gp;
  public ColisionDetector(GamePanel gp) {
    this.gp = gp;
  }

  public void checkTile(Entity entity) {
    entity.colisionOn = false;
    //entity position on world map
    int entityLeftWorldX = entity.worldX +  entity.solidArea.x;
    int entityRightWorldX = entity.worldX +  entity.solidArea.x + entity.solidArea.width;
    int entityTopWorldY = entity.worldY +  entity.solidArea.y;
    int entityBottomWorldY = entity.worldY +  entity.solidArea.y + entity.solidArea.height;

    //entity position index in grid array
    int entityLeftCol = entityLeftWorldX/gp.tileSize;
    int entityRightCol = entityRightWorldX/gp.tileSize;
    int entityTopRow = entityTopWorldY/gp.tileSize;
    int entityBottomRow = entityBottomWorldY/gp.tileSize;

    int tileNum1 , tileNum2;
    switch(entity.direction) {

      /*  1) predict next position  based on direction
          2) check if the tile at that location is walkable
          3) set colisionOn to appropriate value
      */
      case "up" :
        entityTopRow =(entityTopWorldY - entity.speed)/gp.tileSize;
        tileNum1 = gp.tManager.mapTileNum[entityTopRow][entityLeftCol];
        tileNum2 = gp.tManager.mapTileNum[entityTopRow][entityRightCol];

        if(gp.tManager.tile[tileNum1].walkable == false || gp.tManager.tile[tileNum2].walkable == false) {
          entity.colisionOn = true;
        }
        break;
      case "down" :
        entityBottomRow =(entityBottomWorldY + entity.speed)/gp.tileSize;
        tileNum1 = gp.tManager.mapTileNum[entityBottomRow][entityLeftCol];
        tileNum2 = gp.tManager.mapTileNum[entityBottomRow][entityRightCol];

        if(gp.tManager.tile[tileNum1].walkable == false || gp.tManager.tile[tileNum2].walkable == false) {
          entity.colisionOn = true;
        }
        break;
      case "left" :
        entityLeftCol =(entityLeftWorldX - entity.speed)/gp.tileSize;
        tileNum1 = gp.tManager.mapTileNum[entityTopRow][entityLeftCol];
        tileNum2 = gp.tManager.mapTileNum[entityBottomRow][entityLeftCol];

        if(gp.tManager.tile[tileNum1].walkable == false || gp.tManager.tile[tileNum2].walkable == false) {
          entity.colisionOn = true;
        }
        break;
      case "right" :
        entityRightCol =(entityRightWorldX - entity.speed)/gp.tileSize;
        tileNum1 = gp.tManager.mapTileNum[entityTopRow][entityRightCol];
        tileNum2 = gp.tManager.mapTileNum[entityBottomRow][entityRightCol];

        if(gp.tManager.tile[tileNum1].walkable == false || gp.tManager.tile[tileNum2].walkable == false) {
          entity.colisionOn = true;
        }
        break;
    }
    
  }

  public int checkObject(Entity entity , boolean isPlayer) {
    int index = -1;

    for(int i=0; i<gp.obj.length; i++) {
      if(gp.obj[i] != null) {

        //Get entity solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        //Get Objects  solid Area position
        gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
        gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;  
        
        switch(entity.direction) {
          case "up" :
            entity.solidArea.y -= entity.speed;
            if(entity.solidArea.intersects(gp.obj[i].solidArea) & gp.obj[i].walkable == false) {
              entity.colisionOn = true;
              if(isPlayer == true) index = i;
            }
            break;
            case "down" :
            entity.solidArea.y += entity.speed;
            if(entity.solidArea.intersects(gp.obj[i].solidArea) & gp.obj[i].walkable == false) {
              entity.colisionOn = true;
              if(isPlayer == true) index = i;
            }
            break;
          case "left" :
            entity.solidArea.x -= entity.speed;
            if(entity.solidArea.intersects(gp.obj[i].solidArea) & gp.obj[i].walkable == false) {
              entity.colisionOn = true;
              if(isPlayer == true) index = i;
            }
            break;
          case "right" :
            entity.solidArea.x += entity.speed;
            if(entity.solidArea.intersects(gp.obj[i].solidArea) & gp.obj[i].walkable == false) {
              entity.colisionOn = true;
              if(isPlayer == true) index = i;
            }
            break;
        }
        //reset entity position values
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
        gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
       }
    }

    return index;
  }

  public int checkEntity(Entity entity , Entity[] target) {
    int index = -1;

    for(int i=0; i<target.length; i++) {
      if(target[i] != null) {

        //Get entity solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        //Gtargetects  solid Area position
        target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
        target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;  
        
        switch(entity.direction) {
          case "up" : entity.solidArea.y -= entity.speed; break;
          case "down" : entity.solidArea.y += entity.speed; break;
          case "left" : entity.solidArea.x -= entity.speed; break;
          case "right" :
            entity.solidArea.x += entity.speed; break;
        }
        if(entity.solidArea.intersects(target[i].solidArea)){

          if(target[i] != entity) {
            entity.colisionOn = true;
            index = i;
          }
          
        }

        //reset entity position values
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        target[i].solidArea.x = target[i].solidAreaDefaultX;
        target[i].solidArea.y = target[i].solidAreaDefaultY;
       }
    }

    return index;
  }

  public boolean checkPlayer(Entity entity) {
    boolean contact = false;
    //Get entity solid area position
    entity.solidArea.x = entity.worldX + entity.solidArea.x;
    entity.solidArea.y = entity.worldY + entity.solidArea.y;

    //Gtargetects  solid Area position
    gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
    gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;  
    
    switch(entity.direction) {
      case "up" :  entity.solidArea.y -= entity.speed;break;
        case "down" : entity.solidArea.y += entity.speed; break;
      case "left" : entity.solidArea.x -= entity.speed; break;
      case "right" : entity.solidArea.x += entity.speed; break;
    }
    if(entity.solidArea.intersects(gp.player.solidArea)) {
      entity.colisionOn = true;
      contact = true;
    }
    //reset entity position values
    entity.solidArea.x = entity.solidAreaDefaultX;
    entity.solidArea.y = entity.solidAreaDefaultY;
    gp.player.solidArea.x = gp.player.solidAreaDefaultX;
    gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    return contact;
   }

  
} 

