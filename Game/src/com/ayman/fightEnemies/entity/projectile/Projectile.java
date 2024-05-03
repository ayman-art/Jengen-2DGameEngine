package com.ayman.fightEnemies.entity.projectile;

import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.level.Level;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;

public  class Projectile extends Entity {

        protected final int xOrigin, yOrigin;
        protected double angle;
        protected double x, y;
        protected double nx, ny;
        protected double distance;
        protected double speed, fireInterval, range, damage;

        protected Sprite sprite;


        public static Clip fireClip;
        protected Clip clip;
        Thread musicThread = new Thread(() -> {
            try {
                clip.setFramePosition(0);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        public Projectile(int x, int y, double dir, Level level) {
            init(level);
            xOrigin = x;
            yOrigin = y;
            angle = dir;
            this.x = x;
            this.y = y;
        }

        public void move() {
            System.out.println("Move from Projectile");
        }

        public void update() {
            System.out.println("Update from Projectile");
        }

         void render() {

        }

    public Sprite getSprite() {
        return sprite;
    }
    public int getSpriteSize() {
        return sprite.SIZE;
    }

    public int getX() {
        return (int)x;
    }
    public int getY() {
        return (int)y;
    }

    protected void playSound() {

        musicThread.start();
    }

    public int getDamage() {
        return (int)damage;
    }
}
