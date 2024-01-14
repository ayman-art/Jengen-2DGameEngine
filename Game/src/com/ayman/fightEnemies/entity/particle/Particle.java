package com.ayman.fightEnemies.entity.particle;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.level.Level;

import java.util.ArrayList;
import java.util.List;

public class Particle extends Entity {

    Sprite sprite;
    private int life;
    private int time = 0;
    private double xDouble, yDouble, zDouble;
    private double xVel, yVel, zVel;


    public Particle(int x, int y, int life) {

        this.xDouble = this.x = x;
        this.yDouble = this.y = y;
        this.life = life +50+random.nextInt(20);
        sprite = Sprite.basicParticle;
        this.xVel = random.nextGaussian() + 1.8;
        if(xVel < 0) xVel = 0.1;
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
        xDouble += xVel;
        yDouble += yVel;
        zDouble += zVel;

    }
    @Override
    public void render(Screen screen) {
        screen.renderSprite((int)xDouble, (int)yDouble - (int)zDouble +15, sprite, false);
    }



}
