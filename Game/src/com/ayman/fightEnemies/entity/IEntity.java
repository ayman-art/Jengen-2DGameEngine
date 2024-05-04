package com.ayman.fightEnemies.entity;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.level.Level;

public interface IEntity {
    void init(Level level);

    void update();
    void render(Screen screen);

    void remove();
    boolean isRemoved();
    IEntity clone() throws CloneNotSupportedException;

    int getX();

    int getY();

    Level getLevel();

}
