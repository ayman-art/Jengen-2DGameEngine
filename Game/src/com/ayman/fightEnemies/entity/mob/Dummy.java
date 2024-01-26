package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.Screen;

public class Dummy extends Mob{

    public Dummy(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        this.sprite = sprite.player_forward;
    }

    public void update() {


    }
    public void render(Screen screen) {
        screen.renderMob(x, y, sprite, false);
    }
}
