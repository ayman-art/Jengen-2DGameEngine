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
        int size = 16; //the size of the sprite
        int[][] corners = new int[][] { //the corners of the sprite
                {-size, -size}, {size-1, -size}, {-size, size-1}, {size-1, size-1},
                {0, size-1}, {0, -size}, {-size, 0}, {size-1, 0}
        };
        for(int[] corner : corners) {
            int xc = x + corner[0] + xa;
            int yc = y + corner[1] + ya;
            System.out.println((xc>>4) + ", " + (yc>>4));
            System.out.println(level.getTile(xc>>4, yc>>4));
            if(level.getTile(xc>>4, yc>>4).isSolid()) {
                return true;
            }
        }



        return false;
    }

}
