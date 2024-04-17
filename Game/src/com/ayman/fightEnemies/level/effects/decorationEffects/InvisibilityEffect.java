package com.ayman.fightEnemies.level.effects.decorationEffects;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.InvisibilityDecorator;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.effects.Effect;
import com.ayman.fightEnemies.util.Vector2i;

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
