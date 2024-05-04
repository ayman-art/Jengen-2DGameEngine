package com.ayman.jengen.entity.projectile;

import com.ayman.jengen.Graphics.Screen;
import com.ayman.jengen.Graphics.Sprite;
import com.ayman.jengen.entity.Entity;
import com.ayman.jengen.level.Level;


/**
 * Projectile is an abstract class that represents the common methods and fields of the Projectile entities.
 */

public abstract class Projectile extends Entity {

        protected final int xOrigin, yOrigin;
        protected double angle;

    /**
     * The x and y coordinates of the projectile.
     */
    protected double x, y;

    /**
     * The x and y components of the projectile's velocity.
     */
    protected double nx, ny;
        protected double speed, range, damage;

        protected Sprite sprite;




        public Projectile(int x, int y, double dir, Level level) {
            init(level);
            xOrigin = x;
            yOrigin = y;
            angle = dir;
            this.x = x;
            this.y = y;
        }

        public abstract void move();

        public abstract void update();

         public abstract void render(Screen screen);

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


    public int getDamage() {
        return (int)damage;
    }
}
