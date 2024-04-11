package com.ayman.fightEnemies.entity.mob.decoratedPlayer;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.entity.mob.IMob;
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
        if(!isStillDecorated()) {
            player.update();
            return;
        }

        player.update();
//        System.out.println("FastPlayer");

        time--;
        if(time == 0) {
            removeDecoration();
            setStillDecorated(false);
        }

    }

    @Override
    public void removeDecoration() {
        player.setSpeed(initialSpeed);
    }


    @Override
    public IMob clone() throws CloneNotSupportedException {
        var ret = new FastPlayer((IPlayer) player.clone());
        ret.player = (Player) player.clone();
        ret.time = time;
        ret.initialSpeed = initialSpeed;
        return ret;
    }



}