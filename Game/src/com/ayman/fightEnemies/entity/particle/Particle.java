package com.ayman.fightEnemies.entity.particle;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Particle extends Entity {

    private List<Particle> particles = new ArrayList<Particle>();
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

    public Particle(int x, int y, int life, int amount) {
        this(x, y, life);
        particles.add(this);
        for(int i = 0; i < amount - 1; i++) {
            particles.add(new Particle(x, y, life));
        }
    }

    public void update() {
        xDouble += xVel;
        yDouble += yVel;

    }

    public void render(Screen screen) {
        for(int i = 0; i < particles.size(); i++) {

            screen.renderSprite((int) particles.get(i).xDouble, (int) particles.get(i).yDouble, sprite, false);
        }
    }



}
