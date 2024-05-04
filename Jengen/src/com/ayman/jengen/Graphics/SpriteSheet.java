package com.ayman.jengen.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 *
 */
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




    static {
        try {
            tiles = new SpriteSheet("/Sheets/spritesheet.png", 256);
            currentLevel = new SpriteSheet("/Sheets/level1.png", 256);

            player = new SpriteSheet(tiles, 0, 2, 3, 3, Sprite.TILE_SIZE);
            player_up = new SpriteSheet(player, 0, 0, 1, 3, Sprite.TILE_SIZE);
            player_right = new SpriteSheet(player, 1, 0, 1, 3, Sprite.TILE_SIZE);
            player_left = player_right.flippedCopy(); //flipped horizontally
            player_down = new SpriteSheet(player, 2, 0, 1, 3, Sprite.TILE_SIZE);




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
        System.arraycopy(spriteSheet.pixels, 0, this.pixels, 0, spriteSheet.pixels.length);


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


        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));

        int w = image.getWidth();
        int h = image.getHeight();

        //load the image into the pixels array
        image.getRGB(0, 0, w, h, pixels, 0, w);
    }



    public void flip() {
        for (int y0 = 0; y0 < HEIGHT; y0++) {
            for (int x0 = 0; x0 < WIDTH / 2; x0++) {
                int temp = pixels[x0 + y0 * WIDTH];
                int xReflected = (WIDTH - 1 - x0);
                pixels[x0 + y0 * WIDTH] = pixels[xReflected + y0 * WIDTH];
                pixels[xReflected+ y0 * WIDTH] = temp;
            }
        }
    }

    public SpriteSheet flippedCopy() throws IOException {

        SpriteSheet result = new SpriteSheet(this);
        result.flip();
        return result;
    }

}