package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.level.tile.Tile;


public class Level {


    protected int width, height;
    protected int[] tiles;
    protected int[] tilesInt;

    public Level(int width, int height) {

        this.width = width;
        this.height = height;
        tilesInt = new int[width * height];
        generateLevel();
    }
    public Level(String path){

        loadLevel(path);
        generateLevel();
    }

    protected void generateLevel() {
    }

    protected void loadLevel(String path) {
        System.out.println("calling loadLevel from Level");
    }

    public void update() {

    }

    protected void time() {

    }

    public void render(int xScroll, int yScroll, Screen screen) {

        screen.setOffset(xScroll, yScroll);
        int x0 = xScroll >> 4;
        int x1 = (xScroll + screen.width +  16) >> 4; //rendering another tile to the right of the screen
        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.height + 16) >> 4;//rendering another tile to the bottom of the screen

        for(int y = y0; y < y1; y++) {
            for(int x = x0; x < x1; x++) {

                getTile(x, y).render(x, y, screen);





            }
        }
    }

    public Tile getTile(int x, int y) {
        if(x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;

       // System.out.println(tiles[x + y * width]);
        

        if(tiles[x + y * width] == 0xff00ff00) return Tile.grass;
        if(tiles[x + y * width] == 0xff000000) return Tile.rock;
        if(tiles[x + y * width] == 0xffffff00) return Tile.flower;
        if(tiles[x + y * width] == 0xff7f7f00) return Tile.brick;
        if(tiles[x + y * width] == 0xff00ffff) return Tile.water;
        if(tiles[x + y * width] == 0xff7f3300) return Tile.wood;



        return Tile.voidTile;
    }

}
