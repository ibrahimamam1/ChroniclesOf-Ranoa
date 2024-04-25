package entity;

import java.awt.Color;

import game.GamePanel;

public class Fireball extends Projectile{

  public Fireball(GamePanel gp) {
    super(gp);
    this.gp = gp;
    
    name = "FireBall";
    speed = 2;
    maxlife = 80; //it will last for 80frames;
    life = maxlife;
    attack = 2;
    useCost = 1;
    alive = false;

    getImage();
  }
  
  public void getImage() {
    up1 = setup("/assets/Projectile/fireball_up_1"  , gp.tileSize , gp.tileSize );
    up2 = setup("/assets/Projectile/fireball_up_2"  , gp.tileSize , gp.tileSize);
    down1 = setup("/assets/Projectile/fireball_down_1" , gp.tileSize , gp.tileSize);
    down2 = setup("/assets/Projectile/fireball_down_2" , gp.tileSize , gp.tileSize);
    left1 = setup("/assets/Projectile/fireball_left_1" , gp.tileSize , gp.tileSize);
    left2 = setup("/assets/Projectile/fireball_left_2" , gp.tileSize , gp.tileSize);
    right1 = setup("/assets/Projectile/fireball_right_1" , gp.tileSize , gp.tileSize);  
    right2 = setup("/assets/Projectile/fireball_right_2" , gp.tileSize , gp.tileSize);
  }

  public boolean haveResource(Entity user) {
    return user.mana >= useCost;
  }

  public void substractResources(Entity user) {

    user.mana -= useCost;

  }

  public Color getParticleColor() { 
    
    Color color = new Color(240 , 50 , 0);
    return color;
  }
    public int getparticleSize() { return 20;}
    public int getParticleSpeed() { return 1;}
    public int getParticleMaxlife() { return 20;}
}
