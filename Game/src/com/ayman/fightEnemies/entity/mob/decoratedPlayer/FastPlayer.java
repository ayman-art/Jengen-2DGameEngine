package com.ayman.fightEnemies.entity.mob.decoratedPlayer;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.Player;

public class FastPlayer extends DecoratedPlayer {

    int initialSpeed;


    public FastPlayer(IPlayer player) {
        super(player);
        this.time = 100;
        this.initialSpeed = player.getSpeed();
        player.setSpeed(4 * player.getSpeed());
    }

    @Override
    public void update() {
        if(timeOut()) {
            return;
        }
        else {
            time--;
        }

        player.update();
        System.out.println("FastPlayer");
    }

    @Override
    public IPlayer restorePlayer() {
        player.setSpeed(initialSpeed);
        return player;
    }



}