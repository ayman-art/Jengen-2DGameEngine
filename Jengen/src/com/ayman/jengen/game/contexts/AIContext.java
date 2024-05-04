package com.ayman.jengen.game.contexts;

/**
 * AIContext is a class that holds the context of the AI.
 * It has a type that can be Dijkstra, AStar, or DStar for Finding Path Algorithms.

 */
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
