package com.ayman.fightEnemies.level.snapshots;

import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.particle.Particle;
import com.ayman.fightEnemies.entity.projectile.Projectile;

import java.util.ArrayList;
import java.util.List;

public record LevelSnapshot(List<Entity> mobs, List<Projectile> projectiles, List<Particle> particles) {
    public LevelSnapshot(List<Entity> mobs, List<Projectile> projectiles, List<Particle> particles) {
        this.mobs = new ArrayList<>(mobs);
        this.projectiles = new ArrayList<>(projectiles);
        this.particles = new ArrayList<>(particles);
    }
}
