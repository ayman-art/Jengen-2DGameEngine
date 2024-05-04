package com.ayman.jengen.entity.projectile;

import com.ayman.jengen.Graphics.Screen;
import com.ayman.jengen.Graphics.Sprite;
import com.ayman.jengen.audio.Sound;
import com.ayman.jengen.entity.spawner.ParticleSpawner;
import com.ayman.jengen.level.Level;

public class WizardProjectile extends Projectile {




    public static int FIRE_INTERVAL = 10;
    public static int RANGE = 1000;
    public static int SPEED = 2;
    public static int DAMAGE = 1;

    public WizardProjectile(int x, int y, double dir, Level level) {
        super(x, y, dir, level);
        range = RANGE;
        speed = SPEED;
        damage = DAMAGE;

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);

        sprite = Sprite.wizardProjectile.rotatedSPrite(angle);

        Sound.gunClip.setFramePosition(0);
        Sound.gunClip.start();

    }

    public void update() {
        move();
    }

    public void move() {
        if(level.tileCollision((int) (x + nx), (int) (y + ny), 5, -2, 4)) {

            ParticleSpawner particleSpawner = new ParticleSpawner((int)x, (int)y,0, 5, level);
            level.add(particleSpawner);
            remove();
            return;
        }
        x += nx;
        y += ny;
        if(distance() > range) remove();
    }

    private double distance() {
        return Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
    }

    @Override
    public void render(Screen screen) {
        screen.renderProjectile((int)x-8 , (int)y, this);
    }

}
