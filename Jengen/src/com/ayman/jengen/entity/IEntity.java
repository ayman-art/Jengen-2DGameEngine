package com.ayman.jengen.entity;

import com.ayman.jengen.Graphics.Screen;
import com.ayman.jengen.level.Level;

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
