package com.ayman.fightEnemies.game.contexts;

import com.ayman.fightEnemies.game.contexts.levelcontexts.RandomLevelContext;

import java.util.Optional;

public class GUIContext extends Context {

    private int width;
    private int height;

    private int scaleFactor;

    private Optional<Integer> MiniMapAlpha = Optional.empty();

    private String aboutText;

    private GUIContext(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.scaleFactor = builder.scaleFactor;
        this.MiniMapAlpha = builder.MiniMapAlpha;
        this.aboutText = builder.aboutText;
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

    public String getAboutText() {
        return aboutText;
    }


    public static class Builder {


        String aboutText;
        int width;
        int height;
        int scaleFactor;

        private Optional<Integer> MiniMapAlpha = Optional.empty();


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

        public Builder setAboutText(String aboutText) {
            this.aboutText = aboutText;
            return this;
        }

        public GUIContext build() {
            return new GUIContext(this);
        }

    }
}
