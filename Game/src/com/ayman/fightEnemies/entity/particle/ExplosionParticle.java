package com.ayman.fightEnemies.entity.particle;

import com.ayman.fightEnemies.Graphics.Sprite;


/**
 * ExplosionParticle is a class that represents the explosion particles. It is created when an explosion occurs.
 */
public class ExplosionParticle extends Particle{
    public ExplosionParticle(int x, int y, int life, int xVel, int yVel, Sprite sprite) {
        super(x, y, life);
        this.sprite = sprite;
        this.xVel= (double)xVel / 2;
        this.yVel = (double)yVel / 2;
        this.life = 20;
    }

    @Override
    public void update() {
        time++;
        if(time > 7400) {
            time = 0;
        }
        if(time > life) {
            remove();
        }

        move(xDouble + xVel, yDouble + yVel);
    }

    public boolean collision(double x, double y) {
        return false;
    }




}
