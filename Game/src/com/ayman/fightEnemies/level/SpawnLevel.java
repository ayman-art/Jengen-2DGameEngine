package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.util.Encryptor;
import com.ayman.fightEnemies.util.FileCodeChecker;
import com.ayman.fightEnemies.util.LevelEntitiesParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * This class is used to represent a Custom Level.
 * It extends the Level class and adds the ability to load levels from files.
    It takes the level index and loads the level from the file after validating it.
  It also saves the progress in a file.
 It loads the level by adding the Map and the entities.
 */

public class SpawnLevel extends Level {


    public static int numberOfLevels;
    public static String extension = "png";
    public static String levelsLocation;
    private int currentLevelIndex;


    public SpawnLevel() {
        this(-1);
    }

    public SpawnLevel(int currentLevelIndex) {
        if(currentLevelIndex > numberOfLevels)
            throw new IllegalArgumentException("Level index out of bounds"
                    + " currentLevelIndex: " + currentLevelIndex
                    + " numberOfLevels: " + numberOfLevels);
        if(currentLevelIndex == -1) {
            try {
                //if file found

                if (new File(levelsLocation + "\\progress.txt").exists()) {
                    BufferedReader reader = new BufferedReader(new FileReader(levelsLocation + "\\progress.txt"));
                    String line = reader.readLine();
                    currentLevelIndex = Encryptor.decrypt(line);
                    System.out.println("Decrypted level index: " + currentLevelIndex);
                    reader.close();


                    if (!(1 <= currentLevelIndex && currentLevelIndex <= numberOfLevels))
                        throw new IllegalArgumentException("Level index out of bounds"
                                + " currentLevelIndex: " + currentLevelIndex
                                + " numberOfLevels: " + numberOfLevels);

                } else {
                    throw new FileNotFoundException("Progress file not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                currentLevelIndex = 1;
            }
        }


        this.currentLevelIndex = currentLevelIndex;
        String levelPath = getLevelPath(currentLevelIndex);
        String entitiesPath = getEntitiesPath(currentLevelIndex);

        // Ensuring that the User has not tampered with the files
        FileCodeChecker.checkFileCode(levelsLocation, currentLevelIndex);

        loadLevel(levelPath);
        putEntities(entitiesPath);

        saveProgressInLogFile();
    }

    protected void loadLevel(String path) {
        if (path != null && path.endsWith(".txt")) {
            loadFromFile(path);
            return;
        }
        try {
            System.out.println("Loading level from: " + path);

            assert path != null;
            BufferedImage image = ImageIO.read(new File(path));
            if (image == null) System.out.println("image is null");

            assert image != null;
            this.width = image.getWidth();
            this.height = image.getHeight();
            tiles = new int[width * height];
            image.getRGB(0, 0, width, height, tiles, 0, width);
            if (tiles == null) System.out.println("levelPixels is null");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

//            // Print the array
//            System.out.println("Array:");
//            for (int i = 0; i < arrayLength; i++) {
//                System.out.print(tiles[i] + " ");
//                if ((i + 1) % width == 0) {
//                    System.out.println();
//                }
//            }

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

    @Override
    public Level getNextLevel() {
        if(currentLevelIndex > numberOfLevels)
            return null;
        return new SpawnLevel(currentLevelIndex + 1);
    }

    private void saveProgressInLogFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(levelsLocation + "\\progress.txt", false)); // Overwrite the file
            System.out.println("Encrypting level index: " + currentLevelIndex);
            writer.write(Encryptor.encrypt(currentLevelIndex) + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public boolean hasNextLevel(){
        return currentLevelIndex < numberOfLevels;
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    private String getPathPrefix(int levelIndex){
        return levelsLocation + "\\level_" + levelIndex + "\\";
    }
    private String getLevelPath(int levelIndex){
        return getPathPrefix(levelIndex) + "level." + SpawnLevel.extension;
    }
    private String getEntitiesPath(int levelIndex){
        return getPathPrefix(levelIndex) + "entities.txt";
    }


    private void putEntities(String fileName) {
        LevelEntitiesParser.parseEntitiesFile(fileName).forEach(this::add);
    }

    public void reset() {
        currentLevelIndex = 1;
        saveProgressInLogFile();
    }
}
