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

        public static Clip gunClip;
        public static Clip fireClip;
        protected Clip clip;

        public static void init() {
            gunClip = loadSound("/sounds/gun01.wav");
//            fireClip = loadSound("/sounds/fire01.wav");
        }

        public static Clip loadSound(String resourcePath) {
            Clip clip;
            try {
                System.out.println("Loading sound");
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            try {
                clip.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(WizardProjectile.class.getResource(resourcePath))));
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Done");
            return clip;
        }

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
        Thread musicThread = new Thread(() -> {
            try {
                clip.setFramePosition(0);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        musicThread.start();
    }
}
