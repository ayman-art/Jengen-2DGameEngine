package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.level.tile.Tile;

import java.util.Random;



public class LevelGenerator {

    protected int width, height;
    protected int[] tiles;

    public LevelGenerator(int width, int height) {

        this.width = width;
        this.height = height;
        tiles = new int[width * height];
        generateLevel();
    }

    protected void generateLevel() {
    }

    protected void loadLevel(String path) {

    }

    public void update() {

    }

    protected void time() {

    }

    public void render(int xScroll, int yScroll, Screen screen) {

        screen.setOffset(xScroll, yScroll);
        int x0 = xScroll >> 4;
        int x1 = (xScroll + screen.width ) >> 4;
        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.height ) >> 4;

        for(int y = y0; y < y1; y++) {
            for(int x = x0; x < x1; x++) {

                getTile(x, y).render(x, y, screen);
            }
        }
    }

    public Tile getTile(int x, int y) {
        System.out.println(tiles[x + y * width]);

        if(tiles[x + y * width] == 0) return Tile.bird;
        else if(tiles[x + y * width] == 1) return Tile.sky;
        return Tile.voidTile;
    }
}
