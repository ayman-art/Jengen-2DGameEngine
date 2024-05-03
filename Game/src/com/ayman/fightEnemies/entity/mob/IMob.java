package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.IEntity;
import com.ayman.fightEnemies.entity.projectile.Projectile;

import java.util.List;

public interface IMob extends IEntity {

    void move(int xa, int ya);

    void update();

    void render(Screen screen);

    void renderHealth(Screen screen);

    void shoot(int x, int y, double dir);
    Sprite getSprite();

    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
    void setXY(int x, int y);
    void updateHealth(int damage);


    @Override
    IMob clone() throws CloneNotSupportedException;

    int getSpeed();
    void setSpeed(int speed);

    List<Projectile> getProjectiles();

    int getHealth();

    boolean collision(int xa, int ya);

    void setHealth(int heath);
}
