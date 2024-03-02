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

        int[] SolidTilesColors = {Tile.brickColor, Tile.rockColor};
        int[] nonSolidTilesColors = {Tile.waterColor, Tile.flowerColor, Tile.grassColor, Tile.woodColor};


            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {


                    tiles[x + y * width] = nonSolidTilesColors[random.nextInt(nonSolidTilesColors.length)];
                    if(random.nextInt(5) == 0)tiles[x + y * width] = SolidTilesColors[random.nextInt(SolidTilesColors.length)];
                    if(x == 0 || y == 0 || x == width - 1 || y == height - 1) tiles[x + y * width] = Tile.brickColor;
                }
            }
    }


}
