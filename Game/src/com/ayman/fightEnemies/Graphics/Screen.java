package com.ayman.fightEnemies.Graphics;

import java.util.Random;

public class Screen {

    private final int width;
    private final int height;
    public int[] pixels;

    private final int MAP_SIZE = 64;

    private final int MAP_SIZE_MASK = MAP_SIZE - 1;

    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
    private Random random = new Random();

    int counter = 0;
    int timer = 0;

    public Screen(int width, int height) {

        this.width = width;
        this.height = height;
        pixels = new int[width * height];

        for(int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
            tiles[i] = random.nextInt(0xffffff);
        }
    }


    public void clear() {

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void render(int xr, int yr) {

            for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {

                int tileIndex = (x+xr >> 4) + (y + yr >> 4)  * MAP_SIZE ;

                pixels[x + y * width ] = Sprite.sky.pixels[(x + xr & 15) + (y + yr & 15) * Sprite.sky.SIZE];
            }
        }
    }
}
