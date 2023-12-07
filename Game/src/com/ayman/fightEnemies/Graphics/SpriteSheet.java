package com.ayman.fightEnemies.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SpriteSheet {

    private final String path;

    public final int SIZE;

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
        this.SIZE = size;
        pixels = new int[SIZE * SIZE];

        try {
            this.load(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load(String path) throws IOException {



        BufferedImage image = ImageIO.read(new File(path));

        int w = image.getWidth();
        int h = image.getHeight();

        image.getRGB(0, 0, w, h, pixels, 0, w); //
    }
}
