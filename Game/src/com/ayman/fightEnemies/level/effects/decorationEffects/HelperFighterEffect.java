package com.ayman.fightEnemies.level.effects.decorationEffects;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.HelperFighterDecorator;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.effects.Effect;
import com.ayman.fightEnemies.util.Vector2i;

public class HelperFighterEffect extends Effect {
    public HelperFighterEffect(Vector2i position) {
        super(position, helperFighterAnimatedSprite);
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
