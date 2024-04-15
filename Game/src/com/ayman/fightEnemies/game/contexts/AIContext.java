package com.ayman.fightEnemies.game.contexts;

public class AIContext extends Context{

    final private AIType type;

    public enum AIType {
        Dijkstra,
        AStar,
        DStar
    };


    private AIContext(Builder builder) {
        this.type = builder.type;
    }


    public static class Builder {
        private AIType type;

        public Builder setType(AIType type) {
            this.type = type;
            return this;
        }

        public AIContext build() {
            return new AIContext(this);
        }
    }

    public AIType getType() {
        return type;
    }
}
