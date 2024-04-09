package com.ayman.fightEnemies.entity.projectile;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.spawner.ParticleSpawner;
import com.ayman.fightEnemies.entity.spawner.Spawner;
import com.ayman.fightEnemies.level.Level;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;

public class WizardProjectile extends Projectile {




    public static final int FIRE_INTERVAL = 4;
    public WizardProjectile(int x, int y, double dir, Level level) {
        super(x, y, dir, level);
        range = 1000;
        speed = 2;


        damage = 20;
        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);

        sprite = Sprite.wizardProjectile.rotatedSPrite(angle);

        this.clip = Projectile.gunClip;
        playSound();

        this.damage = 2;
    }

    public void update() {
        move();
    }

    public void move() {
        if(level.tileCollision((int) (x + nx), (int) (y + ny), 5, -2, 4)) {

            ParticleSpawner particleSpawner = new ParticleSpawner((int)x, (int)y,0, 5, level);
            level.add(particleSpawner);
            remove();
            return;
        }
        x += nx;
        y += ny;
        if(distance() > range) remove();
    }

    private double distance() {
        double dist = 0;
        dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
        return dist;
    }

    @Override
    public void render(Screen screen) {
        screen.renderProjectile((int)x-8 , (int)y, this);
    }

}
