package com.ayman.jengen.entity;

import com.ayman.jengen.Graphics.Screen;
import com.ayman.jengen.entity.mob.IPlayer;
import com.ayman.jengen.level.Level;

import java.util.Random;

public abstract class Entity implements IEntity, Cloneable {

    public int x, y; //if the entity has a sprite
    private boolean removed = false; //if the entity is removed from the level
    protected Level level;

    public void init(Level level) {
        this.level = level;
    }
    protected final Random random = new Random();

    public void update() {

//        System.out.println("Update from Entity");
    }

    public void render(Screen screen) {

    }

    public void remove() {
        if(this instanceof IPlayer) return;
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

public IEntity clone() throws CloneNotSupportedException {
        return (Entity) super.clone();
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Level getLevel() {
        return level;
    }

}
