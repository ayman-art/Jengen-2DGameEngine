package com.ayman.fightEnemies.level.effects;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.util.Vector2i;

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
        this(new Vector2i(x, y), 10);
    }



    public void applyEffect(Level level, Player player) {
        if(!isRemoved())        player.updateHealth(-health);
    }
}
