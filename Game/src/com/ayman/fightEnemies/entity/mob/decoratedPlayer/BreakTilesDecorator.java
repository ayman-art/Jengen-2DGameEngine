package com.ayman.fightEnemies.entity.mob.decoratedPlayer;

import com.ayman.fightEnemies.entity.mob.IPlayer;

public class BreakTilesDecorator extends DecoratedPlayer{
    public BreakTilesDecorator(IPlayer player) {
        super(player);
        time = 50;
        System.out.println("BreakTilesDecorator1");
    }

    @Override
    public void update() {
        if(timeOut()) {
            player.update();
            return;
        }



        player.update();
        if(player.collision(player.getX() - 3, player.getY() - 3)) {
            System.out.println("Breaking Tiles");
        }
        System.out.println("BreakTilesDecorator");
        time--;


    }


}
