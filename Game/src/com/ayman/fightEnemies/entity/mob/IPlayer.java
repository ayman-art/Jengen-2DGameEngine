package com.ayman.fightEnemies.entity.mob;


public interface IPlayer extends IMob{

    String getName();
    boolean isVisible();
    void setVisible(boolean visible);

    boolean isTileBreaker();
    void setTileBreaker(boolean tileBreaker);

    void addCoins(int coins);

    int getCoins();
}
