package com.ayman.fightEnemies.entity.mob.decoratedPlayer;

import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.spawner.ParticleSpawner;

public class BreakTilesDecorator extends DecoratedPlayer{
    public BreakTilesDecorator(IPlayer player) {
        super(player);
        time = 1500;
//        System.out.println("BreakTilesDecorator1");
        player.setTileBreaker(true);
    }

    @Override
    public void update() {
        if (!isStillDecorated()) {
            player.update();
            return;
        }


        player.update();

        time--;
        if (time == 0) {
            removeDecoration();
            setStillDecorated(false);
        }
    }

    @Override
    public void removeDecoration() {
        player.setTileBreaker(false);
    }

    @Override
    public IPlayer clone() throws CloneNotSupportedException {
        var ret = new BreakTilesDecorator((IPlayer) player.clone());
        ret.player = (IPlayer) player.clone();
        ret.time = time;
        return ret;
    }
}
