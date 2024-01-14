package com.ayman.fightEnemies.entity.projectile;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.Spawner;
import com.ayman.fightEnemies.entity.particle.Particle;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.tile.Tile;

public class WizardProjectile extends Projectile {

    public static final int FIRE_INTERVAL = 2;
    public WizardProjectile(int x, int y, double dir, Level level) {
        super(x, y, dir, level);
        range = 100;
        speed = 1;
        damage = 20;
        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);

        sprite = Sprite.wizardProjectile;

    }

    public void update() {
        move();
    }

    public void move() {
        if(level.tileCollision(x, y, nx, ny, 5)) {

            Spawner spawner = new Spawner((int)x  ,(int)y, Spawner.Type.PARTICLE, 50, level);
            level.add(spawner);
            remove();
            return;
        }
        x += nx;
        y += ny;
        if(distance() > range) remove();
    }

    private double distance() {
        double dist = 0;
        dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
        return dist;
    }

    @Override
    public void render(Screen screen) {
        screen.renderProjectile((int)x-8 , (int)y, this);
    }
}
