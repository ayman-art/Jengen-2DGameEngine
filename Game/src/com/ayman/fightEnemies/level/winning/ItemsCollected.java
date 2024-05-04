package com.ayman.fightEnemies.level.winning;

import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.effects.CoinEffect;
import com.ayman.fightEnemies.level.effects.Effect;

/**
 * This class is used in the GameController to check if the player has collected all the items(coins).
 */
public class ItemsCollected implements WinningState{
    @Override
    public boolean checkWinningState(Level level) {
        return level.getNumberOfCoins() == 0;
    }
}
