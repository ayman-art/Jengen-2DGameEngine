package com.ayman.fightEnemies.entity.projectile;

import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;

public abstract class Projectile extends Entity {

        protected final int xOrigin, yOrigin;
        protected double angle;
        protected double x, y;
        protected double nx, ny;
        protected double distance;
        protected double speed, fireInterval, range, damage;

        protected Sprite sprite;

        public Projectile(int x, int y, double dir) {
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
}
