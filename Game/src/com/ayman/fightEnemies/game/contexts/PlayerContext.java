package com.ayman.fightEnemies.game.contexts;


public class PlayerContext extends Context {

    private final String name;

    private final int effectsTime;
    private final int coinValue;
    private final int originalSpeed;
    private final int chasingRange;
    private final int shootingRange;

    private PlayerContext(Builder builder){
        this.name = builder.name;
        this.effectsTime = builder.effectsTime;
        this.coinValue = builder.coinValue;
        this.originalSpeed = builder.originalSpeed;
        this.chasingRange = builder.chasingRange;
        this.shootingRange = builder.shootingRange;
    }

    public String getName() {
        return name;
    }
    public int getEffectTime() {
        return effectsTime;
    }
    public int getCoinValue() {
        return coinValue;
    }
    public int getOriginalSpeed() {
        return originalSpeed;
    }
    public int getChasingRange() {
        return chasingRange;
    }
    public int getShootingRange() {
        return shootingRange;
    }


    public static class Builder {
        private String name = "Player1";
        private int effectsTime = 200;
        private int coinValue = 5;
        private int originalSpeed = 1;

        private int chasingRange = 600;
        private int shootingRange = 200;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }


        public Builder setEffectTime(int effectsTime) {
            this.effectsTime = effectsTime;
            return this;
        }

        public Builder setCoinValue(int coinValue) {
            this.coinValue = coinValue;
            return this;
        }


        public Builder setOriginalSpeed(int originalSpeed) {
            this.originalSpeed = originalSpeed;
            return this;
        }

        public Builder setChasingRange(int chasingRange) {
            this.chasingRange = chasingRange;
            return this;
        }
        public Builder setShootingRange(int shootingRange) {
            this.shootingRange = shootingRange;
            return this;
        }


        public PlayerContext build() {
            return new PlayerContext(this);
        }

    }
}
