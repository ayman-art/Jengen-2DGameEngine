package com.ayman.fightEnemies.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SpriteSheet {

    private String path;

    public final int SIZE;
    public final int WIDTH, HEIGHT;

    public int[] pixels;

    public static SpriteSheet tiles;

    static {
        try {
            tiles = new SpriteSheet("resources\\Sheets\\spritesheet.png", 256);
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
        for(int y0 = 0; y0 < hPixels; y0++) {
            int yPixel = yBegin + y0;
            for(int x0 = 0; x0 < wPixels; x0++) {
                int xPixel = xBegin + x0;
                pixels[x0 + y0 * wPixels] = spriteSheet.pixels[xPixel + yPixel * WIDTH];

            }
        }


    }

    private void load(String path) throws IOException {



        BufferedImage image = ImageIO.read(new File(path));

        int w = image.getWidth();
        int h = image.getHeight();

        image.getRGB(0, 0, w, h, pixels, 0, w); //
    }
}
