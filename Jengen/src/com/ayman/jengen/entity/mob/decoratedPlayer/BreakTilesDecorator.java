package com.ayman.jengen.entity.mob.decoratedPlayer;

import com.ayman.jengen.entity.mob.IPlayer;
import com.ayman.jengen.level.effects.Effect;
/**
 * BreakTilesDecorator is a concrete decorator that adds the ability to break tiles to the player.
 */

public class BreakTilesDecorator extends DecoratedPlayer{
    public BreakTilesDecorator(IPlayer player) {
        super(player);
        time = Effect.EFFECT_TIME;
        player.setTileBreaker(true);
    }

    @Override
    public void update() {
        if (!isStillDecorated()) {
            player.update();
            return;
        }


        player.setTileBreaker(true); // ensure tileBreakerEffect
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
