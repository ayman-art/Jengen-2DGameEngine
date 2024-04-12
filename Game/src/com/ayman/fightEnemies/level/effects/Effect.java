package com.ayman.fightEnemies.level.effects;

import com.ayman.fightEnemies.Graphics.*;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.util.Vector2i;

public abstract class Effect extends Entity {
    protected Vector2i position;
    protected AnimatedSprite sprite;

    public static AnimatedSprite coinAnimatedSprite;
    public static AnimatedSprite healthAnimatedSprite;
    public static AnimatedSprite speedAnimatedSprite;
    public static AnimatedSprite tileBreakerAnimatedSprite;
    public static AnimatedSprite helperFighterAnimatedSprite;


    static {
        coinAnimatedSprite = loadAnimations(0, 1);
        healthAnimatedSprite = loadAnimations(1, 1);
        speedAnimatedSprite = loadAnimations(2, 1);
        tileBreakerAnimatedSprite = loadAnimations(3, 1);
        helperFighterAnimatedSprite = loadAnimations(4, 1);
    }

    static AnimatedSprite loadAnimations(int x, int y) {
        Sprite s1 = new Sprite(16, x, y, SpriteSheet.tiles);
        Sprite s2 = s1.shift(0, 2);
        Sprite s3 = s1.shift(0, 0);
        Sprite s4 = s1.shift(0, -2);
        AnimatedSprite sprite = new AnimatedSprite(new Sprite[]{s1, s2, s3, s4});
        sprite.setRate(10);
        return sprite;
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

    public abstract void applyEffect(Level level, Player player);
}
