package com.ayman.fightEnemies.level.winning;

import com.ayman.fightEnemies.level.Level;

public class TargetReached implements WinningState{
    @Override
    public boolean checkWinningState(Level level) {
        return level.isWinningEffectVisited();
    }
}
