package com.ayman.fightEnemies;

public class Screen {

    private final int width;
    private final int height;
    public int[] pixels;

    int counter = 0;
    int timer = 0;

    public Screen(int width, int height) {

        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }


    public void clear() {

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void render() {
        counter++;
        if(counter % 100 == 0) {
            timer++;
        }

        for(int y = 0; y < height; y++) {

            int row = y * width;
            for(int x = 0; x < width; x++) {


                pixels[x + y * width] = x * y + timer;
            }
        }
    }
}
