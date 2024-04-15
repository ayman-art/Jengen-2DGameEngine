package com.ayman.fightEnemies.game.contexts.levelcontexts;

import com.ayman.fightEnemies.game.contexts.AIContext;

public class SpawnLevelContext implements ILevelContext{

    private final int numberOfLevels;
    private final String levelsLocation;

    private SpawnLevelContext(Builder builder){
        this.numberOfLevels = builder.numberOfLevels;
        this.levelsLocation = builder.levelsLocation;
    }

    public int getNumberOfLevels(){
        return numberOfLevels;
    }

    public String getLevelsLocation(){
        return levelsLocation;
    }
    public static class Builder{
        private int numberOfLevels;
        String levelsLocation;

        public Builder numberOfLevels(int numberOfLevels){
            this.numberOfLevels = numberOfLevels;
            return this;
        }

        public Builder levelsLocation(String levelsLocation){
            this.levelsLocation = levelsLocation;
            return this;
        }

        public SpawnLevelContext build(){
            return new SpawnLevelContext(this);
        }
    }

}
