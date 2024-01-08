package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;

public abstract class Mob extends Entity {
    protected Sprite sprite;
    protected int dir = 0; //direction of the mob, popular convention :(0 = north, 1 = east, 2 = south, 3 = west)
    protected boolean moving = false;

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
        }
    }
    public void update() {

    }

    public void render() {

    }

    private boolean collision(int xa, int ya) {
        boolean solid = false;
        int width = 16; //the width of the Mob collision box
        int height = 16; //the height of the Mob collision box
        int offSetX = -8; //the offset of the collision box from the Center of the Mob
        int offSetY = -6; //the offset of the collision box from the Center of the Mob
        for(int c = 0; c < 4; c++) {
            int xt = ((x + xa) + c % 2 * width + offSetX) >> 4; //the x coordinate of the tile the Mob is colliding with
            int yt = ((y + ya) + c / 2 * height + offSetY) >> 4; //the y coordinate of the tile the Mob is colliding with
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
}
