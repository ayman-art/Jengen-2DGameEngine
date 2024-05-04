package com.ayman.fightEnemies.level;

/**
 * This class is used to represent a 2D integer vector to represent the tile coordinates actual coordinates.

 */
public class TileCoordinate {

        private int x, y;
        private final int TILE_SIZE = 16;

        public TileCoordinate(int x, int y) {
            this.x = x * TILE_SIZE;
            this.y = y * TILE_SIZE;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        public int[] xy() {
            return new int[]{x, y};
        }
}
