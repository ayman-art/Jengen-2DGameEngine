package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;

public abstract class Mob extends Entity {
    protected Sprite sprite;
    protected int dir = 0; //direction of the mob, popular convention :(0 = north, 1 = east, 2 = south, 3 = west)
    protected boolean moving = false;

    public void move(int xa, int ya) {
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
        return level.getTile((x + xa) >> 4, (y + ya) >> 4).isSolid();
    }

}
