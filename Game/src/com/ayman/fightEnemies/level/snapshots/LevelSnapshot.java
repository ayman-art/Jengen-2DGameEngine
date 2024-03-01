package com.ayman.fightEnemies.level.snapshots;

import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.particle.Particle;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import org.junit.jupiter.api.condition.EnabledIf;

import java.util.ArrayList;
import java.util.List;

public class LevelSnapshot {
    public final List<Entity> mobs;
    public final List<Projectile> projectiles;
    public final List<Particle> particles;

    public LevelSnapshot(List<Entity> mobs, List<Projectile> projectiles, List<Particle> particles) {
        this.mobs = new ArrayList<>();
        for(Entity mob : mobs) {
            try {
                this.mobs.add(mob.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        this.projectiles = new ArrayList<>();
        for(Projectile projectile : projectiles) {
            try {
                this.projectiles.add((Projectile) projectile.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        this.particles = new ArrayList<>();
        for(Particle particle : particles) {
            try {
                this.particles.add((Particle) particle.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Entity> mobs() {
        return mobs;
    }

    public List<Projectile> projectiles() {
        return projectiles;
    }

    public List<Particle> particles() {
        return particles;
    }

}
