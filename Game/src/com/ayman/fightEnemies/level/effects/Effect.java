package com.ayman.fightEnemies.level.effects;

import com.ayman.fightEnemies.Graphics.*;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.util.Vector2i;

public abstract class Effect extends Entity {
    private Vector2i position;
    private AnimatedSprite sprite;

    public static AnimatedSprite coinAnimatedSprite;


    static {
        Sprite coinSprite = new Sprite(16, 0, 1, SpriteSheet.tiles);
        Sprite coinSprite2 = coinSprite.shift(0, 2);
        Sprite coinSprite3 = coinSprite.shift(0, 0);
        Sprite coinSprite4 = coinSprite.shift(0, -2);
        coinAnimatedSprite = new AnimatedSprite(new Sprite[]{coinSprite, coinSprite2, coinSprite3, coinSprite4});
        coinAnimatedSprite.setRate(10);
    }

    public Effect(Vector2i position, AnimatedSprite sprite) {
        this.position = position;
        this.sprite = sprite;
    }

    public void update() {
        sprite.update();
    }

    public void render(Screen screen) {
        screen.renderSprite(position.getX() * 16, position.getY() * 16, sprite.getCurrentSPrite(), false);
    }

    public Vector2i getPosition() {
        return position;
    }

    public abstract void applyEffect(Level level, IPlayer player);
}
