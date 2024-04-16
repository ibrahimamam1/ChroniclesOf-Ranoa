package entity;

import game.GamePanel;

public class Projectile extends Entity{

  Entity user;
  public int useCost;

  public Projectile(GamePanel gp) {
    super(gp);
    
  }

  public void set(int worldX , int worldY , String direction , boolean alive , Entity user){

    this.worldX = worldX;
    this.worldY = worldY;
    if(direction == "left" || direction == "right" || direction == "up" || direction == "down") {
      this.direction = direction;
    }
    else if(direction == "idle_up") {
      this.direction = "up";
    }
    else if(direction == "idle_down") {
      this.direction = "down";
    }
    else if(direction == "idle_left") {
      this.direction = "left";
    }
    else if(direction == "idle_right") {
      this.direction = "right";
    }
    
    this.alive = alive;
    this.user = user;
    this.life = maxlife;
  }

  public void update() {

    if(user == gp.player) {
      int monsterIndex = gp.colisionDetector.checkEntity(this, gp.monster);
      if(monsterIndex != -1) {
        gp.player.damageMonster(monsterIndex , attack);
        alive = false;
      }
    }

    else if(user != gp.player) {
      boolean contactPlayer = gp.colisionDetector.checkPlayer(this);
      if(gp.player.invincible == false && contactPlayer == true) {
        damagePlayer(attack);
        alive = false;
      }
    }

    switch(direction) {
      case "up" : worldY -= speed; break;
      case "down" : worldY += speed; break;
      case "left" : worldX -= speed; break;
      case "right" : worldX += speed; break;
    }

    life--;
    if(life <= 0) {
      alive = false;
    }

    spriteCounter++;
    if(spriteCounter > 12) {
      if(spriteNum == 1) {
        spriteNum = 2;
      }
      else if(spriteNum == 2) {
        spriteNum = 1;
      }
      spriteCounter = 0;
    }
  }
  
  public boolean haveResource(Entity user) {return false;}
  public void substractResources(Entity user) {}
}
