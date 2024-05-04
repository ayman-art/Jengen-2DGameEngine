package com.ayman.fightEnemies.level.tile;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to create Tiles which are the building blocks of the game.
 */
public class Tile {

        public static int TILE_SIZE = Sprite.TILE_SIZE;

        public int x, y;
        public Sprite sprite;

        public static Tile sky = new Tile(Sprite.sky, false, false);
        public static Tile bird = new Tile(Sprite.bird, false, false);
        public static Tile grass = new Tile(Sprite.grass, false, false);
        public static Tile rock = new Tile(Sprite.rock, true, true);
        public static Tile flower = new Tile(Sprite.flower, false, false);
        public static Tile water = new Tile(Sprite.water, false, false);
        public static Tile brick = new Tile(Sprite.brick, true, false);
        public static Tile wood = new Tile(Sprite.wood, false, false);

        public static Tile voidTile = new Tile(Sprite.voidSprite, false, false);


        public static final int grassColor = 0xff00ff00;
        public static final int rockColor = 0xff000000;
        public static final int flowerColor = 0xffffff00;
        public static final int skyColor = 0xffADD8E6;
        public static final int birdColor = 0; // not used.
        public static final int brickColor = 0xffff0000;
        public static final int waterColor = 0xff00ffff;
        public static final int woodColor = 0xff7F0000;

        public static Map<Integer, Tile> costumedTiles = new HashMap<>();


        private final boolean isSolid, isBreakable;


        public static void registerCostumedTile(int color, Tile tile) {
            costumedTiles.put(color, tile);
        }

        public Tile(Sprite sprite, boolean isSolid, boolean isBreakable) {

            this.sprite = sprite;
            this.isSolid = isSolid;
            this.isBreakable = isBreakable;
        }

        public Tile(int color, Sprite sprite, boolean isSolid, boolean isBreakable) {
            this.sprite = sprite;
            this.isSolid = isSolid;
            this.isBreakable = isBreakable;

            costumedTiles.put(color, this);
        }

        public void render(int x, int y, Screen screen) {
            screen.renderTile(x << 4, y << 4, this);

        }

    /**
     * This method is used to check if the tile is solid.
     * @return true if the tile is solid which causes collision, false otherwise.
     */
    public boolean isSolid() {
            return isSolid;
        }

    /**
     * This method is used to check if the tile is breakable.
     * @return true if the tile is breakable by the TileBreaker Decorated Player, false otherwise.
     */
    public boolean isBreakable() {
            return isBreakable;
        }


}
