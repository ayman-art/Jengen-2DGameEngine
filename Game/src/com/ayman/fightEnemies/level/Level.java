package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.level.tile.Tile;

import java.util.ArrayList;
import java.util.List;


public class Level {


    protected int width, height;
    protected int[] tiles;
    protected int[] tilesInt;
    public static Level spawn = new SpawnLevel("resources\\Sheets\\level1.png");
    private List<Entity> entities = new ArrayList<>();

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
        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
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

        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).render(screen);
        }
    }

    public boolean tileCollision(double x, double y, double xa, double ya, int size) {

        boolean solid = false;
        int width = 4; //the width of the Mob collision box
        int height = 4; //the height of the Mob collision box
        int offSetX = -2; //the offset of the collision box from the Center of the Mob
        int offSetY = 4; //the offset of the collision box from the Center of the Mob
        for(int c = 0; c < 4; c++) {
//            double xt = ((x + xa) + c % 2 * size/5 -5) /16; //the x coordinate of the tile the Mob is colliding with
//            double xt = ((x + xa) + c % 2 * size/5 -5) /16; //the x coordinate of the tile the Mob is colliding with
            double xt = ((x + xa) + c % 2 * width + offSetX) /16; //the x coordinate of the tile the Mob is colliding with
            double yt = ((y + ya) + c / 2 * height + offSetY) / 16; //the y coordinate of the tile the Mob is colliding with

            if(getTile((int)xt, (int)yt).isSolid()) solid = true;
        }
        return solid;



    }

    public Tile getTile(int x, int y) {
        if(x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;

       // System.out.println(tiles[x + y * width]);


        if(tiles[x + y * width] == Tile.grassColor) return Tile.grass;
        if(tiles[x + y * width] == Tile.brickColor) return Tile.brick;
        if(tiles[x + y * width] == Tile.rockColor) return Tile.rock;
        if(tiles[x + y * width] == Tile.flowerColor) return Tile.flower;
        if(tiles[x + y * width] == Tile.skyColor) return Tile.sky;
        if(tiles[x + y * width] == Tile.birdColor) return Tile.bird;
        if(tiles[x + y * width] == Tile.waterColor) return Tile.water;
        if(tiles[x + y * width] == Tile.woodColor) return Tile.wood;




        return Tile.voidTile;
    }

    public void add(Entity entity) {
        entity.init(this);
        this.entities.add(entity);
    }

    public void removeProjectile(Projectile projectile) {
        this.entities.remove(projectile);
    }
}
