package com.ayman.fightEnemies.Graphics;

import com.ayman.fightEnemies.level.tile.Tile;

import java.util.Random;

public class Screen {

    public final int width;
    public final int height;
    public int[] pixels;

    private final int MAP_SIZE = 64;

    private final int MAP_SIZE_MASK = MAP_SIZE - 1;

    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
    private Random random = new Random();


    public int xOffset, yOffset;

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





    public void renderTile(int xp, int yp, Tile tile) {

        xp -= xOffset;
        yp -= yOffset;


        for(int y = 0; y < tile.sprite.SIZE; y++) {
            int ya = yp + y;
            for(int x = 0; x < tile.sprite.SIZE; x++) {
                int xa = xp + x;

                if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;

                pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
            }
        }
    }

    public void renderPlayer(int xp, int yp, Sprite sprite) {

        xp -= xOffset;
        yp -= yOffset;

        for(int y = 0; y < 32; y++) {
            int ya = yp + y;
            for(int x = 0; x < 32; x++) {
                int xa = xp + x;

                if(xa < -32 || xa >= width || ya < 0 || ya >= height) continue;

                int col = sprite.pixels[x + y * 32];
                if(col != 0xffff00ff) pixels[xa + ya * width] = col;
            }
        }
    }

    public void setOffset(int xOffset, int yOffset) {

        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}


