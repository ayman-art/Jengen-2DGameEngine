package com.ayman.fightEnemies.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {

    private String path;

    public final int SIZE;
    public final int WIDTH, HEIGHT;

    public int[] pixels;

    public static SpriteSheet tiles, currentLevel;
    public static SpriteSheet player;
    public static SpriteSheet player_down;
    public static SpriteSheet player_up;
    public static SpriteSheet player_left;
    public static SpriteSheet player_right;

    private Sprite[] sprites;

    static {
        try {
            tiles = new SpriteSheet("resources\\Sheets\\spritesheet.png", 256);
            currentLevel = new SpriteSheet("resources\\Sheets\\level1.png", 256);

            player = new SpriteSheet(tiles, 0, 1, 3, 3, 32);
            player_up = new SpriteSheet(player, 0,1,1,3,32);
            player_right = new SpriteSheet(player, 1,1,1,3,32);
//            player_left = player_right.flipped();
            player_down = new SpriteSheet(player, 1,2,1,3,32);



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

        int frame = 0;
        sprites = new Sprite[width * height];

        for(int ya = 0; ya < height; ya++) {
            for(int xa = 0; xa < width; xa++) {
                int[] spritePixels = new int[spriteSize * spriteSize];
                for(int y0 = 0; y0 < spriteSize; y0++) {
                    for(int x0 = 0; x0 < spriteSize; x0++) {
                        spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * WIDTH];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
                sprites[frame++] = sprite;
            }
        }


    }

    private void load(String path) throws IOException {



        BufferedImage image = ImageIO.read(new File(path));

        int w = image.getWidth();
        int h = image.getHeight();

        image.getRGB(0, 0, w, h, pixels, 0, w); //
    }

    public Sprite[] getSprites() {
        return sprites;
    }


    public SpriteSheet flip() {
        for(int y0 = 0; y0 < HEIGHT; y0++) {
            for(int x0 = 0; x0 < WIDTH / 2; x0++) {
                int xReverse = WIDTH - x0;
                int tempPixel = pixels[x0 + y0 * WIDTH];
                pixels[x0 + y0 * WIDTH] = pixels[xReverse + y0 * WIDTH];
                pixels[xReverse + y0 * WIDTH] = tempPixel;
            }
        }
        return this;
    }

//    public SpriteSheet flipped() {
//        try {
//            return ((SpriteSheet) this.clone()).flip();
//        } catch (CloneNotSupportedException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
