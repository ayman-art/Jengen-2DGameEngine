package com.ayman.fightEnemies.level.effects;

import com.ayman.fightEnemies.audio.Sound;
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

    public CoinEffect(int x, int y) {
        this(new Vector2i(x, y));
    }


    @Override
    public void applyEffect(Level level, Player player) {
        Sound.coinClip.setFramePosition(0);
        Sound.coinClip.start();
        if(!isRemoved())        player.addCoins(coins);
    }
}
