package com.ayman.fightEnemies.entity.spawner;

import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.particle.ExplosionParticle;
import com.ayman.fightEnemies.entity.particle.Particle;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.tile.Tile;

public class ParticleSpawner extends Spawner{

    private int life;
    public ParticleSpawner(int x, int y, int life, int amount, Level level) {
        super(x, y, Type.PARTICLE, amount, level);
    }

    public ParticleSpawner(int x, int y, int life, int amount, Level level, Tile tile) {
        super(x, y, Type.PARTICLE, amount, level);

        level.add(new ExplosionParticle(x, y, life, -1, -1, new Sprite(tile.sprite, 0, 0, 8, 8)));
        level.add(new ExplosionParticle(x, y, life, 1, -1,  new Sprite(tile.sprite, 8, 0, 8, 8)));
        level.add(new ExplosionParticle(x, y, life, -1, 1,  new Sprite(tile.sprite, 0, 8, 8, 8)));
        level.add(new ExplosionParticle(x, y, life, 1, 1,  new Sprite(tile.sprite, 8, 8, 8, 8)));
            }
}
