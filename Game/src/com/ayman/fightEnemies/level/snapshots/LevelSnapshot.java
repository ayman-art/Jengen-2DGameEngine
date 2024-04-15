package com.ayman.fightEnemies.level.snapshots;

import com.ayman.fightEnemies.entity.IEntity;
import com.ayman.fightEnemies.entity.particle.Particle;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.level.effects.Effect;
import com.ayman.fightEnemies.util.Vector2i;

import java.util.*;

public record LevelSnapshot(int[] tiles, List<IEntity> mobs, List<Projectile> projectiles, List<Particle> particles, Map<Vector2i, Effect> effects) {
    public LevelSnapshot(int[] tiles, List<IEntity> mobs, List<Projectile> projectiles, List<Particle> particles, Map<Vector2i, Effect> effects) {
        this.tiles = tiles.clone();
        this.mobs = new ArrayList<>();
        for (IEntity mob : mobs) {
            try {
                this.mobs.add(mob.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        this.projectiles = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            try {
                this.projectiles.add((Projectile) projectile.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        this.particles = new ArrayList<>();
        for (Particle particle : particles) {
            try {
                this.particles.add((Particle) particle.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        this.effects = new TreeMap<>(Comparator.comparing(Vector2i::getX).thenComparing(Vector2i::getY));
        for (Map.Entry<Vector2i, Effect> entry : effects.entrySet()) {
            Vector2i key = entry.getKey();
            Effect value = entry.getValue();
            try {
                this.effects.put(new Vector2i(key), (Effect) value.clone());

            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
