package com.ayman.fightEnemies.Graphics;

public class Sprite {

    public final int SIZE;
    private int x, y;
    public int[] pixels;

    private SpriteSheet sheet; //The sheet that contains the sprite

    public static Sprite sky = new Sprite(16, 0, 0, SpriteSheet.tiles);
    public static Sprite bird = new Sprite(16, 1, 0, SpriteSheet.tiles);


    public Sprite(int size, int x, int y, SpriteSheet sheet) {

        this.SIZE = size;
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        pixels = new int[SIZE * SIZE];

        load();
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


}
