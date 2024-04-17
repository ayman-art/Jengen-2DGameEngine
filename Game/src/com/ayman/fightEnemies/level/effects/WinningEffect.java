package com.ayman.fightEnemies.level.effects;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.util.Vector2i;

public class WinningEffect extends Effect {
    public WinningEffect(Vector2i position) {
        super(position, coinAnimatedSprite);
    }

    @Override
    public void applyEffect(Level level, Player player) {
        level.setWinningEffectVisited(true);
    }
}
