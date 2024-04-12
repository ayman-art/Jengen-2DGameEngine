package com.ayman.fightEnemies.level.effects;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.util.Vector2i;

public class HealthEffect extends Effect{

    private static AnimatedSprite healthAnimatedSprite;
    int health;

    public HealthEffect(Vector2i position, int health) {
        super(position, healthAnimatedSprite);
        this.health = health;
    }


    @Override
    public void applyEffect(Level level, IPlayer player) {
        if(!isRemoved())        player.updateHealth(player.getHealth() - health);
    }
}
