package com.ayman.fightEnemies.level.winning;

import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.effects.CoinEffect;
import com.ayman.fightEnemies.level.effects.Effect;

public class ItemsCollected implements WinningState{
    @Override
    public boolean checkWinningState(Level level) {
        return level.getNumberOfCoins() == 0;
    }
}
