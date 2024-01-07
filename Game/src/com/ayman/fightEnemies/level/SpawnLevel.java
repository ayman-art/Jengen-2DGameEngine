package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.level.tile.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpawnLevel extends Level{


    private int[] levelPixels;


    public SpawnLevel(String path) {
        super(path);
    }

    protected void loadLevel(String path) {
        try {
            System.out.println("Loading level from: " + path);
            BufferedImage image = ImageIO.read(new File(path));
            int width = image.getWidth();
            int height = image.getHeight();
            tiles = new Tile[width * height];
            levelPixels = new int[width * height];
            image.getRGB(0, 0, width, height, levelPixels, 0, width);
            if(image == null) System.out.println("image is null");
            if(levelPixels == null) System.out.println("levelPixels is null");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected void generateLevel() {


        for(int i = 0; i < levelPixels.length; i++) {


            if(levelPixels[i] == 0xff00ff00) tiles[i] = Tile.grass;
            else if(levelPixels[i] == 0xffffff00) tiles[i] = Tile.flower;
            else if(levelPixels[i] == 0xff000000) tiles[i] = Tile.rock;
            else tiles[i] = Tile.sky;

            System.out.println("tiles[" + i + "] = " + tiles[i] + " levelPixels[" + i + "] = " + levelPixels[i]);

//            System.out.println("levelPixels[" + i + "] = " + levelPixels[i]);
//            System.out.println(0xff00ff00);
//            System.out.println(0xffffff00);
//            System.out.println(0xff000000);
        }

//        printing tiles:
        for(int i = 0; i < levelPixels.length; i++) {
            System.out.println("levelPixels[" + i + "] = " + levelPixels[i]);

        }
    }
}
