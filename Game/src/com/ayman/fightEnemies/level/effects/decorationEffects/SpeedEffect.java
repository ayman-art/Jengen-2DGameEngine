package com.ayman.fightEnemies.level.effects.decorationEffects;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.DecoratedPlayer;
import com.ayman.fightEnemies.entity.mob.decoratedPlayer.FastPlayer;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.effects.Effect;
import com.ayman.fightEnemies.util.Vector2i;

public class SpeedEffect extends Effect {

        int speed;

        public SpeedEffect(Vector2i position) {
            super(position, speedAnimatedSprite);
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
