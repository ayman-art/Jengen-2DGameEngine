package com.ayman.fightEnemies.Graphics;

import java.awt.*;

public class Sprite {

    public final int SIZE;
    private int width, height;
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

    public static Sprite player0 = new Sprite(16, 0, 1, SpriteSheet.tiles);
    public static Sprite player1 = new Sprite(16, 1, 1, SpriteSheet.tiles);
    public static Sprite player2 = new Sprite(16, 0, 2, SpriteSheet.tiles);
    public static Sprite player3 = new Sprite(16, 1, 2, SpriteSheet.tiles);
    public static Sprite player_forward = new Sprite(16, 0, 1, SpriteSheet.tiles);
    public static Sprite player_forward_1 = new Sprite(16, 0, 2, SpriteSheet.tiles);
    public static Sprite player_forward_2 = new Sprite(16, 0, 3, SpriteSheet.tiles);
    public static Sprite player_side = new Sprite(16, 1, 1, SpriteSheet.tiles);
    public static Sprite player_side_1 = new Sprite(16, 1, 2, SpriteSheet.tiles);
    public static Sprite player_side_2 = new Sprite(16, 1, 3, SpriteSheet.tiles);
    public static Sprite player_backwards = new Sprite(16, 2, 1, SpriteSheet.tiles);
    public static Sprite player_backwards_1 = new Sprite(16, 2, 2, SpriteSheet.tiles);
    public static Sprite player_backwards_2 = new Sprite(16, 2, 3, SpriteSheet.tiles);



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
        for(int i = 0; i < spritePixels.length; i++) {
            pixels[i] = spritePixels[i];
        }
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

    public Sprite rotatedSPrite() {
        return null;
    }





}
