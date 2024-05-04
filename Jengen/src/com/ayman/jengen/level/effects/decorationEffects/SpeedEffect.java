package com.ayman.jengen.level.effects.decorationEffects;

import com.ayman.jengen.entity.mob.Player;
import com.ayman.jengen.entity.mob.decoratedPlayer.DecoratedPlayer;
import com.ayman.jengen.entity.mob.decoratedPlayer.FastPlayer;
import com.ayman.jengen.level.Level;
import com.ayman.jengen.level.effects.Effect;
import com.ayman.jengen.util.Vector2i;

/**
 * This class is used to create the SpeedEffect which is an effect that makes the player faster.
 */
public class SpeedEffect extends Effect {

        int speed;

        public SpeedEffect(Vector2i position) {
            super(position, speedAnimatedSprite);
        }
        public SpeedEffect(int x, int y) {
            this(new Vector2i(x, y));
        }


    @Override
    public void applyEffect(Level level, Player player) {
            if(!isRemoved()) {
                int index = level.getPlayerIndex(player);
                DecoratedPlayer decoratedPlayer = new FastPlayer(level.getPlayer(index));
                level.setPlayer(index, decoratedPlayer);
            }

    }
}
