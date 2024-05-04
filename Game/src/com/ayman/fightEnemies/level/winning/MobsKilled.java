package com.ayman.fightEnemies.level.winning;

import com.ayman.fightEnemies.level.Level;

/**
 * This class is used in the GameController to check if the player has killed all the mobs.

 */
public class MobsKilled implements WinningState{
    @Override
    public boolean checkWinningState(Level level) {
        return level.getNumberOfEnemies() == 0;
    }
}
