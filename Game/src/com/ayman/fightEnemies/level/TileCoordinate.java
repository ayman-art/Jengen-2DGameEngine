package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.level.tile.Tile;

/**
 * This class is used to represent a 2D integer vector to represent the tile coordinates actual coordinates.

 */
public class TileCoordinate {

        private int x, y;
        private final int TILE_SIZE = Sprite.TILE_SIZE;

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
