package com.ayman.fightEnemies.entity.projectile;

import com.ayman.fightEnemies.entity.Entity;

public abstract class Projectile extends Entity {

        protected final int xOrigin, yOrigin;
        protected double angle;
        protected double x, y;
        protected double nx, ny;
        protected double distance;
        protected double speed, rateOfFire, range, damage;

        public Projectile(int x, int y, double dir) {
            xOrigin = x;
            yOrigin = y;
            angle = dir;
            this.x = x;
            this.y = y;
        }

        protected void move() {

        }

        public void update() {

        }

        public void render() {

        }
}
