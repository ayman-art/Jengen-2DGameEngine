package com.ayman.fightEnemies.entity.particle;

import com.ayman.fightEnemies.Graphics.Sprite;

public class ExplosionParticle extends Particle{
    public ExplosionParticle(int x, int y, int life, int xVel, int yVel, Sprite sprite) {
        super(x, y, life);
        this.sprite = sprite;
        this.xVel= xVel;
        this.yVel = yVel;
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
