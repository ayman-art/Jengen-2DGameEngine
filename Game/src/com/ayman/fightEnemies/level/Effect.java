package com.ayman.fightEnemies.level;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.AnimatedTile;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.util.Vector2i;

public class Effect extends Entity {
    private Vector2i position;
    private AnimatedSprite sprite;

    public Effect(Vector2i position, AnimatedSprite sprite) {
        this.position = position;
        this.sprite = sprite;
    }

    public void update() {
        sprite.update();
    }

    public void render(Screen screen) {
        screen.renderSprite(position.getX(), position.getY(), sprite.getCurrentSPrite(), false);
    }
}
