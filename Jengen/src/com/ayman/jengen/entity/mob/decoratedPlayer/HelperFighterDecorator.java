package com.ayman.jengen.entity.mob.decoratedPlayer;

import com.ayman.jengen.entity.mob.Helper;
import com.ayman.jengen.entity.mob.IPlayer;
import com.ayman.jengen.level.effects.Effect;

/**
 * HelperFighterDecorator is a concrete decorator that adds the ability to have a helper to fight for the player.
 *
 */
public class HelperFighterDecorator extends DecoratedPlayer {
    Helper helper;
    public HelperFighterDecorator(IPlayer player) {
        super(player);
        time = Effect.EFFECT_TIME * 2; // Adding 2 times the effect time for the helper to be able to help the player for a longer time.
    }

    @Override
    public void update() {
        if (!isStillDecorated()) {
            player.update();
            return;
        }

        if(player.getLevel() != null && helper == null) {
            helper = new Helper(getInnerMostPlayer());
            player.getLevel().add(helper);
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
        helper.remove();
    }
    @Override
    public IPlayer clone() throws CloneNotSupportedException {
        var ret = new HelperFighterDecorator((IPlayer) player.clone());
        ret.player = (IPlayer) player.clone();
        ret.time = time;
        ret.helper = helper;
        return ret;
    }
}
