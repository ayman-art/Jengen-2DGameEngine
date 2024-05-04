package com.ayman.jengen.level.effects;

import com.ayman.jengen.entity.mob.Player;
import com.ayman.jengen.level.Level;
import com.ayman.jengen.util.Vector2i;

/**
 * This class is used to create the WinningEffect which is an effect that makes the player win the game.
 * It is used to create the winning effect that makes the player win the game if the winning state is reaching the end.
 */
public class WinningEffect extends Effect {
    public WinningEffect(Vector2i position) {
        super(position, winningAnimatedSprite);
    }

    @Override
    public void applyEffect(Level level, Player player) {
        level.setWinningEffectVisited(true);
    }
}
