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
}
