package com.ayman.fightEnemies.entity;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.level.Level;

import java.util.Random;

public class Entity {

    public int x, y; //if the entity has a sprite
    private boolean removed = false; //if the entity is removed from the l
    protected Level level;

    protected final Random random = new Random();

    public void update() {

    }

    public void render(Screen screen) {

    }

    public void remove() {

        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }
}
