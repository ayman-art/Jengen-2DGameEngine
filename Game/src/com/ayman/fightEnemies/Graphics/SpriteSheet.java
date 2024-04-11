package com.ayman.fightEnemies.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {

    private String path;

    public final int SIZE; //size of the sprite sheet in pixels
    public final int WIDTH, HEIGHT; //size of the sprite sheet in pixels

    public int[] pixels;

    public static SpriteSheet tiles, currentLevel;
    public static SpriteSheet player;
    public static SpriteSheet player_down;
    public static SpriteSheet player_up;
    public static SpriteSheet player_left;
    public static SpriteSheet player_right;

    public static SpriteSheet coin;



    static {
        try {
            tiles = new SpriteSheet("/Sheets/spritesheet.png", 256);
            currentLevel = new SpriteSheet("/Sheets/level1.png", 256);

            player = new SpriteSheet(tiles, 0, 1, 3, 3, 16);
            player_up = new SpriteSheet(player, 0, 0, 1, 3, 16);
            player_right = new SpriteSheet(player, 1, 0, 1, 3, 16);
            player_left = player_right.flippedCopy();
            player_down = new SpriteSheet(player, 2, 0, 1, 3, 16);

            coin = new SpriteSheet(tiles,0, 1, 1, 1, 16);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SpriteSheet(String path, int size) throws IOException {

        this.path = path;
        this.SIZE = this.WIDTH = this.HEIGHT = size;
        pixels = new int[SIZE * SIZE];

        try {
            this.load(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SpriteSheet(SpriteSheet spriteSheet) {

            this.path = spriteSheet.path;
            this.SIZE = spriteSheet.SIZE;
            this.WIDTH = spriteSheet.WIDTH;
            this.HEIGHT = spriteSheet.HEIGHT;
            this.pixels = new int[WIDTH * HEIGHT];
            for(int i = 0; i < spriteSheet.pixels.length; i++) {
                this.pixels[i] = spriteSheet.pixels[i];
            }


    }

    public SpriteSheet(String path, int width, int height) throws IOException {

        this.path = path;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.SIZE = WIDTH;
        pixels = new int[WIDTH * HEIGHT];

        try {
            this.load(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SpriteSheet(SpriteSheet spriteSheet, int x, int y, int width, int height, int spriteSize) { //for cropping sprites

        int xBegin = x * spriteSize;
        int yBegin = y * spriteSize;
        int wPixels = width * spriteSize;
        int hPixels = height * spriteSize;

        SIZE = WIDTH = wPixels;
        HEIGHT = hPixels;

        pixels = new int[wPixels * hPixels];
        for (int y0 = 0; y0 < hPixels; y0++) {
            int yPixel = yBegin + y0;
            for (int x0 = 0; x0 < wPixels; x0++) {
                int xPixel = xBegin + x0;
                pixels[x0 + y0 * wPixels] =
                        spriteSheet.pixels[xPixel + yPixel * spriteSheet.WIDTH];
            }
        }




}


    private void load(String path) throws IOException {


        BufferedImage image = ImageIO.read(getClass().getResource("/Sheets/spritesheet.png"));
//        System.out.println(path);

        int w = image.getWidth();
        int h = image.getHeight();

        image.getRGB(0, 0, w, h, pixels, 0, w); //
    }



    public SpriteSheet flip() {
        for (int y0 = 0; y0 < HEIGHT; y0++) {
            for (int x0 = 0; x0 < WIDTH / 2; x0++) {
                int temp = pixels[x0 + y0 * WIDTH];
                int xReflected = (WIDTH - 1 - x0);
                pixels[x0 + y0 * WIDTH] = pixels[xReflected + y0 * WIDTH];
                pixels[xReflected+ y0 * WIDTH] = temp;
            }
        }
        return this;
    }

    public SpriteSheet flippedCopy() throws IOException {

        SpriteSheet result = new SpriteSheet(this);
        result.flip();
        return result;
    }

}