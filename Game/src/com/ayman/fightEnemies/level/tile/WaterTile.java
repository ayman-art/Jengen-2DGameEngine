package com.ayman.fightEnemies.level.tile;

import com.ayman.fightEnemies.Graphics.Sprite;

public class WaterTile extends Tile{
    public WaterTile(Sprite sprite) {
        super(sprite);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
