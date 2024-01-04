package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Input.Keyboard;

public class Player extends Mob {

    private Keyboard input;
    public Player(Keyboard input) {
        this.input = input;
    }
    public Player(int x, int y, Keyboard input) { //if we want to spawn the player at a specific location
        this.x = x;
        this.y = y;
        this.input = input;
    }


    public void update() {
        if(input.up) y--;
        if(input.down) y++;
        if(input.left) x--;
        if(input.right) x++;


    }

    public void render() {

    }
}
