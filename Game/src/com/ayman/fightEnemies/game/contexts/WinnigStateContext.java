package com.ayman.fightEnemies.game.contexts;

/**
 * WinnigStateContext is a class that holds the context of the Winning State.
 The Engine supports three types of winning states:
    CollectAllCoins: The player has to collect all the coins in the level to win.
    KillAllEnemies: The player has to kill all the enemies in the level to win.
    ReachEnd: The player has to reach the end of the level to win.

 */
public class WinnigStateContext extends Context{

    final private WinningStateType type;


    public enum WinningStateType {
        CollectAllCoins,
        KillAllEnemies,
        ReachEnd
    };

    public WinningStateType getType() {
        return type;
    }


    private WinnigStateContext(Builder builder) {
        this.type = builder.type;
    }

    public static class Builder {
        private WinningStateType type;

        public Builder setType(WinningStateType type) {
            this.type = type;
            return this;
        }

        public WinnigStateContext build() {
            return new WinnigStateContext(this);
        }
    }
}
