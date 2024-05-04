package com.ayman.jengen.entity.spawner;

import com.ayman.jengen.Graphics.Sprite;
import com.ayman.jengen.entity.particle.ExplosionParticle;
import com.ayman.jengen.level.Level;
import com.ayman.jengen.level.tile.Tile;

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
