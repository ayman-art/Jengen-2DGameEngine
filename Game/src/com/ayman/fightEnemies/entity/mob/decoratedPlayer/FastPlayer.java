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
        return player.getSpeed() * 4;
    }
}