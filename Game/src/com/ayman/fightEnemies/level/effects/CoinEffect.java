package com.ayman.fightEnemies.level.effects;

import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.util.Vector2i;

public class CoinEffect extends Effect{

    public static int COIN_VALUE = 5;
    private int coins = COIN_VALUE;

    public CoinEffect(Vector2i position) {
        super(position, coinAnimatedSprite);
    }


    @Override
    public void applyEffect(Level level, Player player) {
        if(!isRemoved())        player.addCoins(coins);
    }
}
