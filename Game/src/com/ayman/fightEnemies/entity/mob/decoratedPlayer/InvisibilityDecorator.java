package com.ayman.fightEnemies.entity.mob.decoratedPlayer;

import com.ayman.fightEnemies.entity.mob.IPlayer;

public class InvisibilityDecorator extends DecoratedPlayer {
    public InvisibilityDecorator(IPlayer player) {
        super(player);
        time = 100;
//        System.out.println("InvisibilityDecorator1");
        player.setVisible(false);
    }

    @Override
    public void update() {
        if (!isStillDecorated()) {
            player.update();
            return;
        }

        player.update();
//        System.out.println("InvisibilityDecorator");

        time--;
        if (time == 0) {
            removeDecoration();
            setStillDecorated(false);
        }
    }

    @Override
    public void removeDecoration() {
        player.setVisible(true);
    }

    @Override
    public IPlayer clone() throws CloneNotSupportedException {
        var ret = new InvisibilityDecorator((IPlayer) player.clone());
        ret.player = (IPlayer) player.clone();
        ret.time = time;
        return ret;
    }
}
