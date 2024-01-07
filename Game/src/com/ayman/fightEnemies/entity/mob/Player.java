package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
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
        int xa = 0, ya = 0;
        if(input.up) ya--;
        if(input.down) ya++;
        if(input.left) xa--;
        if(input.right) xa++;
        if(xa != 0 || ya != 0) move(xa, ya);


    }

    public void render() {

    }

    public void render(Screen screen) {
        int size = 16;
        screen.renderPlayer(x, y, Sprite.player0);
        screen.renderPlayer(x + 16, y, Sprite.player1);
        screen.renderPlayer(x, y + 16, Sprite.player2);
        screen.renderPlayer(x + 16, y + 16, Sprite.player3);

    }
}
