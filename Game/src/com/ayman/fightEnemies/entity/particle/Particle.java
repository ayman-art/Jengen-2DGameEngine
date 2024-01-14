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
        this.life = life + (random.nextInt(20) - 10);
        sprite = Sprite.basicParticle;
        this.xVel = random.nextGaussian();
        this.yVel = random.nextGaussian();
    }

    public Particle(int x, int y, int life, int amount) {
        this(x, y, life);
        particles.add(this);
        particles = new ArrayList<Particle>(amount);
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
            particles.get(i).render(screen);
        }
        screen.renderSprite(x, y, sprite, true);
    }

}
