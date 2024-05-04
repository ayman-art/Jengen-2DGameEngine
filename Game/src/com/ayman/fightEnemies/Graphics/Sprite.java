package com.ayman.fightEnemies.Graphics;

import java.awt.*;
import java.util.Arrays;

/**
 * A class used to represent a sprite
 */
public class Sprite {

    public final int SIZE;
    private final int width;
    private final int height;
    public int x, y;
    public int[] pixels;

    protected SpriteSheet sheet; //The sheet that contains the sprite

    public static Sprite sky = new Sprite(16, 0, 0, SpriteSheet.tiles);
    public static Sprite bird = new Sprite(16, 1, 0, SpriteSheet.tiles);
    public static Sprite grass = new Sprite(16, 2, 0, SpriteSheet.tiles);
    public static Sprite flower = new Sprite(16, 3, 0, SpriteSheet.tiles);
    public static Sprite rock = new Sprite(16, 4, 0, SpriteSheet.tiles);
    public static Sprite water = new Sprite(16, 5, 0, SpriteSheet.tiles);
    public static Sprite brick = new Sprite(16, 6, 0, SpriteSheet.tiles);
    public static Sprite wood = new Sprite(16, 7, 0, SpriteSheet.tiles);

    public static Sprite wizardProjectile = new Sprite(16, 8, 0, SpriteSheet.tiles);

    public static Sprite basicParticle = new Sprite(3, 0xAAAAAA);
    public static Sprite voidSprite = new Sprite(16, Color.GRAY.getRGB());



    public Sprite(SpriteSheet sheet, int width, int height) {

        SIZE = (width == height) ? width : -1;

        this.width = width;
        this.height = height;
        this.sheet = sheet;
        this.pixels = new int[width * height];
        load();
    }
    public Sprite(int size, int x, int y, SpriteSheet sheet) {

        this.SIZE = size;
        this.width = size;
        this.height = size;
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        pixels = new int[SIZE * SIZE];

        load();
    }

    public Sprite(int width, int height, int color) {

        SIZE = -1;
        this.width = width;
        this.height = height;
        pixels = new int[width * height];

        setColor(color);
    }

    public Sprite(int size, int color) {

        this.SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE * SIZE];

        setColor(color);
    }

    public Sprite(int[] spritePixels, int spriteSize, int spriteSize1) {

        SIZE = (spriteSize == spriteSize1) ? spriteSize : -1;
        this.width = spriteSize;
        this.height = spriteSize1;
        pixels = new int[spriteSize * spriteSize1];
        System.arraycopy(spritePixels, 0, pixels, 0, spritePixels.length);
    }
    public Sprite(Sprite sprite, int x, int y, int width, int height) {

        SIZE = (width == height) ? width : -1;
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        for(int y0 = 0; y0 < height; y0++) {
            for(int x0 = 0; x0 < width; x0++) {
                pixels[x0 + y0 * width] = sprite.pixels[(x + x0) + (y + y0) * sprite.width];
            }
        }
    }

    private void setColor(int color) {

        for(int i = 0; i < width * height; i++) {
            pixels[i] = color;
        }
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }




    private void load() {

        for(int y = 0; y < SIZE; y++) {
            for(int x = 0; x < SIZE; x++) {
                int xSheet = this.x;
                int ySheet = this.y;
                pixels[x + y * SIZE] = sheet.pixels[xSheet + x + (ySheet + y) * sheet.SIZE]; //mapping the sprite to the pixels array
            }
        }
    }

    public Sprite rotatedSPrite(double angle) {
        int[] newPixels = new int[pixels.length];
        Arrays.fill(newPixels, pixels[0]);
        for(int y = 0; y < SIZE; y++) {
            for(int x = 0; x < SIZE; x++) {
                double s = SIZE / 2.0;
                double radius = Math.sqrt((x - s) * (x - s) + (y - s) * (y - s));
                double theta = Math.atan2(y - s, x - s);

                int xx = (int) (radius * Math.cos(theta - angle) + SIZE / 2);
                int yy = (int) (radius * Math.sin(theta - angle) + SIZE / 2);

                if(xx < 0 || xx >= SIZE || yy < 0 || yy >= SIZE) continue;
                newPixels[x + y * SIZE] = pixels[xx + yy * SIZE];
            }
        }
        return new Sprite(newPixels, SIZE, SIZE);
    }



    public Sprite shift(int x, int y) {
        int[] newPixels = new int[pixels.length];
        Arrays.fill(newPixels, pixels[0]);
        for(int yy = 0; yy < SIZE; yy++) {
            for(int xx = 0; xx < SIZE; xx++) {
                int x0 = xx - x;
                int y0 = yy - y;
                if(x0 < 0 || x0 >= SIZE || y0 < 0 || y0 >= SIZE) continue;
                newPixels[xx + yy * SIZE] = pixels[x0 + y0 * SIZE];
            }
        }
        return new Sprite(newPixels, SIZE, SIZE);
    }




}
