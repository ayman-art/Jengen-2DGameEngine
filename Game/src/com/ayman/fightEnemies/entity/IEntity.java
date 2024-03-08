package com.ayman.fightEnemies.entity;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.level.Level;

import java.util.Random;

public interface IEntity {
    public void init(Level level);

    public void update();
    public void render(Screen screen);

    public void remove();
    public boolean isRemoved();
    public Entity clone() throws CloneNotSupportedException;

    int getX();

    int getY();

    Level getLevel();
}
