package com.ayman.jengen.level.effects.decorationEffects;

import com.ayman.jengen.entity.mob.Player;
import com.ayman.jengen.entity.mob.decoratedPlayer.HelperFighterDecorator;
import com.ayman.jengen.level.Level;
import com.ayman.jengen.level.effects.Effect;
import com.ayman.jengen.util.Vector2i;

/**
 * This class is used to create the HelperFighterEffect which is an effect that helps the player fight the enemies.

 */
public class HelperFighterEffect extends Effect {
    public HelperFighterEffect(Vector2i position) {
        super(position, helperFighterAnimatedSprite);
    }

    public HelperFighterEffect(int x, int y) {
        this(new Vector2i(x, y));
    }
    @Override
    public void applyEffect(Level level, Player player) {
        if(!isRemoved()) {
            int index = level.getPlayerIndex(player);
            HelperFighterDecorator decoratedPlayer = new HelperFighterDecorator(level.getPlayer(index));
            level.setPlayer(index, decoratedPlayer);
        }
    }
}
