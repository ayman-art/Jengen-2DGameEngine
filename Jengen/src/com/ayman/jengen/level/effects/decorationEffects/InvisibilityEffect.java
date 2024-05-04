package com.ayman.jengen.level.effects.decorationEffects;

import com.ayman.jengen.entity.mob.Player;
import com.ayman.jengen.entity.mob.decoratedPlayer.InvisibilityDecorator;
import com.ayman.jengen.level.Level;
import com.ayman.jengen.level.effects.Effect;
import com.ayman.jengen.util.Vector2i;

/**
 * This class is used to create the InvisibilityEffect which is an effect that makes the player invisible to the enemies.
 */
public class InvisibilityEffect extends Effect {
    public InvisibilityEffect(Vector2i position) {
        super(position, invisibilityAnimatedSprite);
    }

    public InvisibilityEffect(int x, int y) {
        this(new Vector2i(x, y));
    }

    @Override
    public void applyEffect(Level level, Player player) {
        if(!isRemoved()) {
            int index = level.getPlayerIndex(player);
            InvisibilityDecorator decoratedPlayer = new InvisibilityDecorator(level.getPlayer(index));
            level.setPlayer(index, decoratedPlayer);
        }
    }
}
