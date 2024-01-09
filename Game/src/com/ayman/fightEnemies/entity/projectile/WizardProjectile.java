package com.ayman.fightEnemies.entity.projectile;

public class WizardProjectile extends Projectile {
    public WizardProjectile(int x, int y, double dir) {
        super(x, y, dir);
        range = 200;
        speed = 4;
        damage = 20;
        rateOfFire = 20;

    }

    public void update() {
        move();
    }

    protected void move() {
        x += nx;
        y += ny;
        if(distance() > range) remove();
    }

    private double distance() {
        double dist = 0;
        dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
        return dist;
    }
}
