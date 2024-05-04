package com.ayman.fightEnemies.entity.particle;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;

/**
 * Particle is a class that represents the particles in the game.

 */

public class Particle extends Entity {

    Sprite sprite;
    protected int life;
    protected int time = 0;
    protected double xDouble, yDouble, zDouble;
    protected double xVel, yVel, zVel;
    private boolean collide = false;


    public Particle(int x, int y, int life) {

        this.xDouble = this.x = x;
        this.yDouble = this.y = y;
        this.life = life +50+random.nextInt(20);
        sprite = Sprite.basicParticle;
        this.xVel = random.nextGaussian() + 1.8;
        this.yVel = random.nextGaussian();
        this.zDouble = random.nextFloat() + 2.0;
    }


    public void update() {
        zVel -= 0.1;
        if(zDouble < 0) {
            zDouble = 0;
            zVel *= -0.5;
            xVel *= 0.4;
            yVel *= 0.4;
        }


        time++;
        if(time > 7400) {
            time = 0;
        }
        if(time > life) {
            remove();
        }
        move(xDouble + xVel, yDouble + yVel + (zDouble + zVel));

    }

    protected void move(double x, double y) {
        if(!collide && collision(x, y)) {
            collide = false;
            this.xVel *= -0.5;
            this.yVel *= -0.5;
            this.zVel *= -0.5;
        }
        this.xDouble += xVel;
        this.yDouble += yVel;
        this.zDouble += zVel;
    }


    public boolean collision(double x, double y) {
        int size = sprite.SIZE;
        boolean solid = false;
        double xt = (xVel<0)? x - size:x + size;
        double yt = (yVel + zVel<0)? y-size:y+size;
        if(level.getTile((int)xt >> 4, (int)yt >> 4).isSolid()) solid = true;
        return solid;
    }
    @Override
    public void render(Screen screen) {
        screen.renderSprite((int)xDouble, (int)yDouble + (int)zDouble, sprite, false);
    }



}
