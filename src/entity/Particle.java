package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import game.GamePanel;

public class Particle extends Entity{

    Entity generator; //The entity which generates the particle
    Color color;
    int size;
    int xd; //x direction
    int yd; //y direction

    public Particle(GamePanel gp ,Entity generator , Color color , int size , int speed , int maxlife , int xd , int yd) {
        super(gp);
        
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxlife = maxlife;
        this.xd = xd;
        this.yd = yd;

        life = maxlife;
        worldX = generator.worldX;
        worldY = generator.worldY;
    }

    public void update() {
        life--;

        worldX += xd * speed;
        worldY += yd * speed;

        if(life == 0) { alive = false; }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.setColor(color);
        g2.fillRect(screenX , screenY , size  ,size);
    }
    
}
