package com.ayman.jengen.level.effects;

import com.ayman.jengen.audio.Sound;
import com.ayman.jengen.entity.mob.Player;
import com.ayman.jengen.level.Level;
import com.ayman.jengen.util.Vector2i;

/**
 * This class is used to create the CoinEffect which is an effect that gives the player coins.

 */
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
