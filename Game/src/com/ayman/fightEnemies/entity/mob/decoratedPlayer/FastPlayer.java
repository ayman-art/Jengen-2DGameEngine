package com.ayman.fightEnemies.entity.mob.decoratedPlayer;

import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.entity.mob.Player;

public class FastPlayer extends DecoratedPlayer {

    public FastPlayer(Player player) {
        super(player);
    }

    @Override
    public int getSpeed() {
        return speed * 2;
    }

}