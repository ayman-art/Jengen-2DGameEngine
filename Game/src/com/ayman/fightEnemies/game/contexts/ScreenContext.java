package com.ayman.fightEnemies.game.contexts;

import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.game.contexts.Context;
import com.ayman.fightEnemies.level.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ScreenContext is a class that holds the context of the screen(Rendering).

 */
public class ScreenContext extends Context {

    private final Map<Integer, Tile> costumedTiles;



    private ScreenContext(Builder builder) {
        this.costumedTiles = builder.costumedTiles;
    }

    public Map<Integer, Tile> getCostumedTiles() {
        return costumedTiles;
    }

    public static class Builder {

        private Map<Integer, Tile> costumedTiles = new HashMap<>();
        public Builder addTile(int color, boolean isSolid, boolean isBreakable, Sprite sprite) {
            costumedTiles.put(color, new Tile(color, sprite, isSolid, isBreakable));
            return this;

        }

        public ScreenContext build() {
            return new ScreenContext(this);
        }
    }
}
