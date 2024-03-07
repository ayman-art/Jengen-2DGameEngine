package com.ayman.fightEnemies.entity.mob.decoratedPlayer;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.Player;

public class FastPlayer extends DecoratedPlayer {


    public FastPlayer(IPlayer player) {
        super(player);
    }

    @Override
    public int getSpeed() {
        return player.getSpeed() * 5;
    }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void render(Screen screen) {
        player.render(screen);
    }

    @Override
    public void shoot(int x, int y, double dir) {
        player.shoot(x, y, dir);
    }

    @Override
    public void move(int xa, int ya) {
        player.move(xa, ya);
    }



    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void renderHealth(Screen screen) {
        player.renderHealth(screen);
    }
}