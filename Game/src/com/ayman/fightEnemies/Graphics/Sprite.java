package com.ayman.fightEnemies.Graphics;

import java.awt.*;

public class Sprite {

    public final int SIZE;
    private int x, y;
    public int[] pixels;

    private SpriteSheet sheet; //The sheet that contains the sprite

    public static Sprite sky = new Sprite(16, 0, 0, SpriteSheet.tiles);
    public static Sprite bird = new Sprite(16, 1, 0, SpriteSheet.tiles);

    public static Sprite voidSprite = new Sprite(16, Color.red.getRGB());

    public static Sprite player0 = new Sprite(16, 0, 1, SpriteSheet.tiles);
    public static Sprite player1 = new Sprite(16, 1, 1, SpriteSheet.tiles);
    public static Sprite player2 = new Sprite(16, 0, 2, SpriteSheet.tiles);
    public static Sprite player3 = new Sprite(16, 1, 2, SpriteSheet.tiles);
    public static Sprite player_forward = new Sprite(32, 0, 1, SpriteSheet.tiles);
    public static Sprite player_forward_1 = new Sprite(32, 0, 2, SpriteSheet.tiles);
    public static Sprite player_forward_2 = new Sprite(32, 0, 3, SpriteSheet.tiles);
    public static Sprite player_side = new Sprite(32, 1, 1, SpriteSheet.tiles);
    public static Sprite player_side_1 = new Sprite(32, 1, 2, SpriteSheet.tiles);
    public static Sprite player_side_2 = new Sprite(32, 1, 3, SpriteSheet.tiles);
    public static Sprite player_backwards = new Sprite(32, 2, 1, SpriteSheet.tiles);
    public static Sprite player_backwards_1 = new Sprite(32, 2, 2, SpriteSheet.tiles);
    public static Sprite player_backwards_2 = new Sprite(32, 2, 3, SpriteSheet.tiles);

    public Sprite(int size, int x, int y, SpriteSheet sheet) {

        this.SIZE = size;
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        pixels = new int[SIZE * SIZE];

        load();
    }

    public Sprite(int size, int color) {

        this.SIZE = size;
        pixels = new int[SIZE * SIZE];

        setColor(color);
    }

    private void setColor(int color) {

        for(int i = 0; i < SIZE * SIZE; i++) {
            pixels[i] = color;
        }
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
