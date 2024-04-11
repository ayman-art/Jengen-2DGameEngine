package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.entity.mob.Chaser;
import com.ayman.fightEnemies.entity.mob.Dummy;
import com.ayman.fightEnemies.level.tile.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class SpawnLevel extends Level {


    public SpawnLevel(String path) {
        super(path);
    }

    protected void loadLevel(String path) {
        if (path != null && path.substring(path.length() - 4).equals(".txt")) {
            loadFromFile(path);
            return;
        }
        try {
            System.out.println("Loading level from: " + path);

            BufferedImage image = ImageIO.read(getClass().getResource(path));

            this.width = image.getWidth();
            this.height = image.getHeight();
            tiles = new int[width * height];
            image.getRGB(0, 0, width, height, tiles, 0, width);
            if (image == null) System.out.println("image is null");
            if (tiles == null) System.out.println("levelPixels is null");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        add(new Chaser(3, 3));


        //for(int i = 0; i < 1; i++)add(new Chaser(3,3+i));
    }

    private void loadFromFile(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            // Read width
            width = Integer.parseInt(reader.readLine());

            // Read height
            height = Integer.parseInt(reader.readLine());

            // Calculate array length
            int arrayLength = width * height;

            // Read array
            tiles = new int[arrayLength];
            for (int i = 0; i < arrayLength; i++) {
                tiles[i] = Integer.parseInt(reader.readLine());
            }

            // Print the array
            System.out.println("Array:");
            for (int i = 0; i < arrayLength; i++) {
                System.out.print(tiles[i] + " ");
                if ((i + 1) % width == 0) {
                    System.out.println();
                }
            }

            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public void writeToFile(String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));


            writer.write(width + "\n");
            writer.write(height + "\n");

            for (int tile : tiles) {
                writer.write(tile + "\n");
            }

            writer.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void generateLevel() {


        for (int i = 0; i < tiles.length; i++) {


        }


    }
}
