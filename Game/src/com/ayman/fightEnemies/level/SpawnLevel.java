package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.entity.mob.Chaser;
import com.ayman.fightEnemies.entity.mob.Dummy;
import com.ayman.fightEnemies.level.tile.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpawnLevel extends Level{




    public SpawnLevel(String path) {
        super(path);
    }

    protected void loadLevel(String path) {
        try {
            System.out.println("Loading level from: " + path);

            BufferedImage image = ImageIO.read(getClass().getResource("/Sheets/level1.png"));

            this.width = image.getWidth();
            this.height = image.getHeight();
            tiles = new int[width * height];
            image.getRGB(0, 0, width, height, tiles, 0, width);
            if(image == null) System.out.println("image is null");
            if(tiles == null) System.out.println("levelPixels is null");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        add(new Chaser(3,3));



        //for(int i = 0; i < 1; i++)add(new Chaser(3,3+i));
    }
    protected void generateLevel() {



        for(int i = 0; i < tiles.length; i++) {



        }


    }
}
