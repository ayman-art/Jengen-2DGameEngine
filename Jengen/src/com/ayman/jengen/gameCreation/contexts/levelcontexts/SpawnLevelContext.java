package com.ayman.jengen.gameCreation.contexts.levelcontexts;

/**
 * SpawnLevelContext is a class that holds the context of the Spawn Level that is created by the Game maker.

 */
public class SpawnLevelContext implements ILevelContext{

    private final int numberOfLevels;
    private final String levelsLocation;

    private final String extension;

    private SpawnLevelContext(Builder builder){
        this.numberOfLevels = builder.numberOfLevels;
        this.levelsLocation = builder.levelsLocation;
        this.extension = builder.extension;
    }

    public int getNumberOfLevels(){
        return numberOfLevels;
    }

    public String getPath(){
        return levelsLocation;
    }

    public static class Builder{
        private int numberOfLevels;
        String levelsLocation;

        String extension = "png";

        public Builder setNumberOfLevels(int numberOfLevels){
            this.numberOfLevels = numberOfLevels;
            return this;
        }

        public Builder setPath(String levelsLocation){
            this.levelsLocation = levelsLocation;
            return this;
        }

        public Builder setLevelExtension(String extension){
            this.extension = extension;
            return this;
        }



        public SpawnLevelContext build(){
            return new SpawnLevelContext(this);
        }



    }

}
