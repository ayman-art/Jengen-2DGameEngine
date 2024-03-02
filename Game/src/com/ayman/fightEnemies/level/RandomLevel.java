package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.level.tile.Tile;

import java.util.Random;

public class RandomLevel extends Level {

    private static final Random random = new Random();
    public RandomLevel(int width, int height) {
        super(width, height);
        generateLevel();
    }

    protected void generateLevel() {
        System.out.println("Random Level");

        int[] colors = new int[]{
                Tile.birdColor,
                Tile.grassColor,
                Tile.flowerColor,
                Tile.rockColor,
                Tile.waterColor
        };


            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {


                    tiles[x + y * width] = colors[random.nextInt(5)];
                }
            }
    }


}
