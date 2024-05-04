package com.ayman.fightEnemies.entity.spawner;

import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.particle.Particle;
import com.ayman.fightEnemies.level.Level;

public class Spawner extends Entity {


    public enum Type {
        PARTICLE, MOB
    }

    public Spawner(int x, int y, Type type, int amount, Level level) {
        init(level);
        switch (type) {
            case PARTICLE:
                for(int i = 0; i < amount; i++) {
                    Particle p = new Particle(x, y, 50);
                    level.add(p);
                }
                break;
            case MOB:
                break;
        }
    }
}
