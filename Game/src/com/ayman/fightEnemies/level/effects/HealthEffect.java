package com.ayman.fightEnemies.level.effects;

import com.ayman.fightEnemies.audio.Sound;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.util.Vector2i;

/**
 * This class is used to create the HealthEffect which is an effect that gives the player health.

 */
public class HealthEffect extends Effect{
    int health;

    public HealthEffect(Vector2i position, int health) {
        super(position, healthAnimatedSprite);
        this.health = health;
    }

    public HealthEffect(int x, int y, int health) {
        this(new Vector2i(x, y), health);
    }

    public HealthEffect(int x, int y) {
        this(new Vector2i(x, y), 50);
    }



    public void applyEffect(Level level, Player player) {
        Sound.healthClip.setFramePosition(0);
        Sound.healthClip.start();
        if(!isRemoved())        player.updateHealth(-health);
    }
}
