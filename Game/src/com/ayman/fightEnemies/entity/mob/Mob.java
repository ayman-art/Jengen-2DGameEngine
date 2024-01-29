package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.spawner.ParticleSpawner;
import com.ayman.fightEnemies.entity.spawner.Spawner;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.entity.projectile.WizardProjectile;

import java.util.ArrayList;
import java.util.List;

public abstract class Mob extends Entity {
    protected AnimatedSprite currentAnimatedSprite;
    protected int dir = 0; //direction of the mob, popular convention :(0 = north, 1 = east, 2 = south, 3 = west)
    protected boolean moving = false;

    protected List<Projectile> projectiles = new ArrayList<>(); // To keep track of projectiles fired by a specific mob
    public void move(int xa, int ya) {

        if(xa != 0 && ya != 0) {
            move(xa, 0);
            move(0, ya);
            return;
        }

        if(xa > 0) dir = 1; //east
        if(xa < 0) dir = 3; //west
        if(ya > 0) dir = 2; //south
        if(ya < 0) dir = 0; //north

        if(!collision(xa, ya)) {
            x += xa;
            y += ya;
        } else {
            ParticleSpawner particleSpawner = new ParticleSpawner(x,y, 20, 1, level);

            level.add(particleSpawner);
        }
    }
    public abstract void update();

    public abstract void render(Screen screen);

    protected boolean collision(int xa, int ya) {
        boolean solid = false;
        int width = 16; //the width of the Mob collision box
        int height = 16; //the height of the Mob collision box
        int offSetX = -8; //the offset of the collision box from the Center of the Mob
        int offSetY = -2; //the offset of the collision box from the Center of the Mob
        for(int c = 0; c < 4; c++) {
            int xt = ((x + xa) + c % 2 * (width-1) + offSetX) >> 4; //the x coordinate of the tile the Mob is colliding with
            int yt = ((y + ya) + c / 2 * (height-1) + offSetY) >> 4; //the y coordinate of the tile the Mob is colliding with
            if(level.getTile(xt, yt).isSolid()) solid = true;
        }
        return solid;
    }


    private boolean collisionEntire(int xa, int ya) {
        int size = 16; //the size of the sprite
        int[][] corners = new int[][]{ //the corners of the sprite
                {-size, -size}, {size - 1, -size}, {-size, size - 1}, {size - 1, size - 1},
                {0, size - 1}, {0, -size}, {-size, 0}, {size - 1, 0}
        };
        for (int[] corner : corners) {
            int xc = x + corner[0] + xa;
            int yc = y + corner[1] + ya;
            System.out.println((xc >> 4) + ", " + (yc >> 4));
            System.out.println(level.getTile(xc >> 4, yc >> 4));
            if (level.getTile(xc >> 4, yc >> 4).isSolid()) {
                return true;
            }
        }
        return false;
    }

    protected void shoot(int x, int y, double dir) {

        Projectile projectile = new WizardProjectile(x, y, dir, level);
        projectiles.add(projectile);
        level.addProjectile(projectile);
    }

    public Sprite getSprite() {
        return currentAnimatedSprite.getCurrentSPrite();
    }



    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

}
