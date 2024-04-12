package com.ayman.fightEnemies.level.effects.decorationEffects;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.InvisibilityDecorator;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.effects.Effect;
import com.ayman.fightEnemies.util.Vector2i;

public class BreakTilesEffect extends Effect {
    public BreakTilesEffect(Vector2i position, AnimatedSprite sprite) {
        super(position, sprite);
    }

    @Override
    public void applyEffect(Level level, Player player) {

    }
}
