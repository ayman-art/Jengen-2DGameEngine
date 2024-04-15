package com.ayman.fightEnemies.game.contexts;

public class WinnigStateContext extends Context{

    final private WinningStateType type;


    public enum WinningStateType {
        CollectAllCoins,
        KillAllEnemies,
        ReachEnd
    };


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
