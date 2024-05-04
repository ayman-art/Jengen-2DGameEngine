package com.ayman.jengen.level.winning;

import com.ayman.jengen.level.Level;

/**
 * This interface represents the WinningState which is used to check if the player has won the game.

 */
public interface WinningState {
    boolean checkWinningState(Level level);
}
