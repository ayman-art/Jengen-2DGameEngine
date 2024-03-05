package com.ayman.fightEnemies.entity.mob.decoratedPlayer;

import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.entity.mob.Player;

public abstract class DecoratedPlayer extends Player{


    protected Player innerPlayer;
    public DecoratedPlayer(Player player) {
        this.innerPlayer = player;
    }




}
