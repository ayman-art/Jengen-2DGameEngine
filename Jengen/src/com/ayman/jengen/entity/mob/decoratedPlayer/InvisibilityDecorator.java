package com.ayman.jengen.entity.mob.decoratedPlayer;

import com.ayman.jengen.entity.mob.IPlayer;
import com.ayman.jengen.level.effects.Effect;

/**
 * InvisibilityDecorator is a concrete decorator that adds the ability to be invisible to the player.
 */
public class InvisibilityDecorator extends DecoratedPlayer {
    public InvisibilityDecorator(IPlayer player) {
        super(player);
        time = Effect.EFFECT_TIME;
        player.setVisible(false);
    }

    @Override
    public void update() {
        if (!isStillDecorated()) {
            player.update();
            return;
        }

        player.setVisible(false); // ensure invisibility
        player.update();

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
