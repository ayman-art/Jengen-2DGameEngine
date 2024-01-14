package com.ayman.fightEnemies.entity.particle;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.level.Level;

import java.util.ArrayList;
import java.util.List;

public class Particle extends Entity {

    private List<Particle> particles = new ArrayList<>();
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

    static int counter = 0;
    public Particle(int x, int y, int life, int amount, Level level) {
        this(x, y, life);
        init(level);
        particles.add(this);
        for(int i = 0; i < amount - 1; i++) {
            particles.add(new Particle(x, y, life));
        }
    }

    public void update() {

        if(life-- < 0) remove();
        for(int i = 0; i < particles.size(); i++) {
            particles.get(i).xDouble += particles.get(i).xVel;
            particles.get(i).yDouble += particles.get(i).yVel;
        }

    }
    @Override
    public void render(Screen screen) {
        for(int i = 0; i < particles.size(); i++) {
            screen.renderSprite((int)particles.get(i).xDouble, (int)particles.get(i).yDouble, sprite, false);
        }
    }



}
