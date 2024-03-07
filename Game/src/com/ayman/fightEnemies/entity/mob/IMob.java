package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.IEntity;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.entity.projectile.WizardProjectile;
import com.ayman.fightEnemies.entity.spawner.ParticleSpawner;

public interface IMob extends IEntity {

    public void move(int xa, int ya);

    public abstract void update();

    public abstract void render(Screen screen);

    public void renderHealth(Screen screen);

    public void shoot(int x, int y, double dir);
    public Sprite getSprite();

    public int getX();
    public int getY();
    public void setX(int x);
    public void setY(int y);
    public void setXY(int x, int y);
    public void updateHealth(int damage);


    @Override
    public Entity clone() throws CloneNotSupportedException;

    public int getSpeed();
}
