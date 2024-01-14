package com.ayman.fightEnemies.entity.spawner;

import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.particle.Particle;
import com.ayman.fightEnemies.level.Level;

import java.util.ArrayList;
import java.util.List;

public class Spawner extends Entity {

    List<Entity> entities = new ArrayList<>();

    public enum Type {
        PARTICLE, MOB;
    }

    private Type type;

    public Spawner(int x, int y, Type type, int amount, Level level) {
        init(level);
        this.type = type;
        switch (type) {
            case PARTICLE:
                for(int i = 0; i < amount; i++) {
                    level.addParticle(new Particle(x, y, 5));
                }
                break;
            case MOB:
                break;
        }
    }
}