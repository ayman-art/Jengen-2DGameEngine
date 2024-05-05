package com.ayman.jengen.gameCreation.contexts.levelcontexts;

/**
 * RandomLevelContext is a class that holds the context of the Random Level.
 */
public class RandomLevelContext implements ILevelContext{

    private final int width;
    private final int height;

    private RandomLevelContext(Builder builder){
        this.width = builder.width;
        this.height = builder.height;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }


    public static class Builder{
        private int width;
        private int height;

        public Builder setWidth(int width){
            this.width = width;
            return this;
        }

        public Builder setHeight(int height){
            this.height = height;
            return this;
        }

        public Builder setSize(int width, int height){
            this.width = width;
            this.height = height;
            return this;
        }

        public RandomLevelContext build(){
            return new RandomLevelContext(this);
        }
    }
}
