package com.ayman.jengen.entity.mob;

import com.ayman.jengen.Graphics.Screen;
import com.ayman.jengen.Graphics.Sprite;
import com.ayman.jengen.entity.IEntity;
import com.ayman.jengen.entity.projectile.Projectile;

import java.util.List;

/**
 * IMob is an interface that represents the common methods of the Mob class.

 */
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


    int getHealth();

    void setHealth(int heath);

    void updateHealth(int damage);



    @Override
    IMob clone() throws CloneNotSupportedException;

    int getSpeed();

    void setSpeed(int speed);

    List<Projectile> getProjectiles();

    boolean collision(int xa, int ya);
}
