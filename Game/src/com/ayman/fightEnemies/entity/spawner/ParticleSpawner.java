package com.ayman.fightEnemies.entity.spawner;

import com.ayman.fightEnemies.entity.particle.Particle;
import com.ayman.fightEnemies.level.Level;

public class ParticleSpawner extends Spawner{

    private int life;
    public ParticleSpawner(int x, int y, int life, int amount, Level level) {
        super(x, y, Type.PARTICLE, amount, level);
        for(int i = 0; i < amount; i++) {
            Particle p = new Particle(x, y, life);
            p.init(level);
            level.add(p);
        }
    }
}
