package com.ayman.fightEnemies.entity;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.level.Level;

public interface IEntity {
    public void init(Level level);

    public void update();
    public void render(Screen screen);

    public void remove();
    public boolean isRemoved();
    public IEntity clone() throws CloneNotSupportedException;

    int getX();

    int getY();

    Level getLevel();
}
