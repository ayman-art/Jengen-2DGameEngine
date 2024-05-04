package com.ayman.jengen.level.effects.decorationEffects;

import com.ayman.jengen.entity.mob.Player;
import com.ayman.jengen.entity.mob.decoratedPlayer.BreakTilesDecorator;
import com.ayman.jengen.entity.mob.decoratedPlayer.DecoratedPlayer;
import com.ayman.jengen.level.Level;
import com.ayman.jengen.level.effects.Effect;
import com.ayman.jengen.util.Vector2i;

/**
 * This class is used to create the BreakTilesEffect which is an effect that breaks the tiles by the player.

 */
public class BreakTilesEffect extends Effect {
    public BreakTilesEffect(Vector2i position) {
        super(position, tileBreakerAnimatedSprite);
    }

    public BreakTilesEffect(int x, int y) {
        this(new Vector2i(x, y));
    }

    @Override
    public void applyEffect(Level level, Player player) {
        if(!isRemoved()) {
            int index = level.getPlayerIndex(player);
            DecoratedPlayer decoratedPlayer = new BreakTilesDecorator(level.getPlayer(index));
            level.setPlayer(index, decoratedPlayer);
        }
    }
}
