package com.ayman.fightEnemies.level.effects.decorationEffects;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.FastPlayer;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.effects.Effect;
import com.ayman.fightEnemies.util.Vector2i;

public class SpeedEffect extends Effect {

        int speed;

        public SpeedEffect(Vector2i position, AnimatedSprite sprite, int speed) {
            super(position, sprite);
            this.speed = speed;
        }

    @Override
    public void applyEffect(Level level, IPlayer player) {
        if(!isRemoved())level.setPlayer(player, new FastPlayer(player));
    }
}
