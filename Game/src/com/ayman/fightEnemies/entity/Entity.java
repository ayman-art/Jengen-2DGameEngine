package com.ayman.fightEnemies.entity;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.level.Level;

import java.util.Random;

public class Entity {

    public int x, y; //if the entity has a sprite
    private boolean removed = false; //if the entity is removed from the level
    protected Level level;

    public void init(Level level) {
        this.level = level;
    }
    protected final Random random = new Random();

    public void update() {
        System.out.println("Update from Entity");
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
