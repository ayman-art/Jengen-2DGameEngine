package com.ayman.fightEnemies.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    private final String path;

    public final int SIZE;

    public int[] pixels;

    public SpriteSheet(String path, int size) throws IOException {

        this.path = path;
        this.SIZE = size;
        pixels = new int[SIZE * SIZE];

        try {
            this.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load() throws IOException {

        BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));

        int w = image.getWidth();
        int h = image.getHeight();

        image.getRGB(0, 0, w, h, pixels, 0, w); //
    }
}
