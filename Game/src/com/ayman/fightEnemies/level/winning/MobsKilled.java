package com.ayman.fightEnemies.level.winning;

import com.ayman.fightEnemies.level.Level;

public class MobsKilled implements WinningState{
    @Override
    public boolean checkWinningState(Level level) {
        return level.getNumberOfEnemies() == 0;
    }
}
