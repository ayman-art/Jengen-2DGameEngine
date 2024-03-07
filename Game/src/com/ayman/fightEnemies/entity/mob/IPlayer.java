package com.ayman.fightEnemies.entity.mob;


public interface IPlayer extends IMob{

    public String getName();
    public boolean isVisible();
    public void setVisible(boolean visible);
}
