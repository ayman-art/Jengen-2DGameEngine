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
    private double xDouble, yDouble;
    private double xVel, yVel;


    public Particle(int x, int y, int life) {

        this.xDouble = this.x = x;
        this.yDouble = this.y = y;
        this.life = life;
        sprite = Sprite.basicParticle;
        this.xVel = random.nextGaussian();
        this.yVel = random.nextGaussian();
    }


    public void update() {

        if(life-- < 0) remove();
        xDouble += xVel;
        yDouble += yVel;

    }
    @Override
    public void render(Screen screen) {
        screen.renderSprite((int)xDouble, (int)yDouble, sprite, false);
    }



}
