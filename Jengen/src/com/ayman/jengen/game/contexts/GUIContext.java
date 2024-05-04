package com.ayman.jengen.game.contexts;

import java.util.Optional;

/**
 * GUIContext is a class that holds the context of the GUI.
 */

public class GUIContext extends Context {

    private final int width;
    private final int height;

    private final int scaleFactor;

    private Optional<Integer> MiniMapAlpha;

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
    public Optional<Integer> getMiniMapAlpha() {
        return MiniMapAlpha;
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
