package com.ayman.fightEnemies.game.contexts;

import java.util.Optional;

public class GUIContext extends Context {

    private int width;
    private int height;

    private int scaleFactor;

    private Optional<Integer> MiniMapAlpha = Optional.empty();

    private GUIContext(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.scaleFactor = builder.scaleFactor;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getScaleFactor() {
        return scaleFactor;
    }


    public static class Builder {


        int width;
        int height;
        int scaleFactor;

        private Optional<Integer> MiniMapAlpha = Optional.empty();

        public Builder() {
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }
        public Builder setScaleFactor(int scaleFactor) {
            this.scaleFactor = scaleFactor;
            return this;
        }

        public Builder setMiniMapAlpha(int MiniMapAlpha) {
            this.MiniMapAlpha = Optional.of(MiniMapAlpha);
            return this;
        }

        public GUIContext build() {
            return new GUIContext(this);
        }
    }
}
