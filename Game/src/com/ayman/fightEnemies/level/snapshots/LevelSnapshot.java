package com.ayman.fightEnemies.level.snapshots;

import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.IEntity;
import com.ayman.fightEnemies.entity.particle.Particle;
import com.ayman.fightEnemies.entity.projectile.Projectile;

import java.util.ArrayList;
import java.util.List;

public record LevelSnapshot(List<IEntity> mobs, List<Projectile> projectiles, List<Particle> particles) {
    public LevelSnapshot(List<IEntity> mobs, List<Projectile> projectiles, List<Particle> particles) {
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
    }

}
