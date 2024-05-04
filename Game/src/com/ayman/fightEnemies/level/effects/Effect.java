package com.ayman.fightEnemies.level.effects;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.Graphics.SpriteSheet;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.level.Level;
import com.ayman.fightEnemies.level.tile.Tile;
import com.ayman.fightEnemies.util.Vector2i;

/**
 * This is an abstract class that represents the effects that can be applied to the player.
 The class contains the position of the effect, the sprite of the effect, and the time of the effect.
 It also stores the animated sprites of the effects.
 */
public abstract class Effect extends Entity {
    public static int EFFECT_TIME = 200;
    protected Vector2i position;
    protected AnimatedSprite sprite;

    public static AnimatedSprite coinAnimatedSprite;
    public static AnimatedSprite healthAnimatedSprite;
    public static AnimatedSprite speedAnimatedSprite;
    public static AnimatedSprite tileBreakerAnimatedSprite;
    public static AnimatedSprite helperFighterAnimatedSprite;
    public static AnimatedSprite invisibilityAnimatedSprite;
    public static AnimatedSprite winningAnimatedSprite;


    static {
        coinAnimatedSprite = loadAnimations(0, 1);
        healthAnimatedSprite = loadAnimations(1, 1);
        speedAnimatedSprite = loadAnimations(2, 1);
        tileBreakerAnimatedSprite = loadAnimations(3, 1);
        helperFighterAnimatedSprite = loadAnimations(4, 1);
        invisibilityAnimatedSprite = loadAnimations(5, 1);
        winningAnimatedSprite = loadAnimations(6, 1);
    }

    static AnimatedSprite loadAnimations(int x, int y) {
        Sprite s1 = new Sprite(Sprite.TILE_SIZE, x, y, SpriteSheet.tiles);
        Sprite s2 = s1.shift(0, 2);
        Sprite s3 = s1.shift(0, 0);
        Sprite s4 = s1.shift(0, -2);
        AnimatedSprite sprite = new AnimatedSprite(new Sprite[]{s1, s2, s3, s4});
        sprite.setRate(100);
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
        screen.renderSprite(position.getX() * Sprite.TILE_SIZE, position.getY() * Sprite.TILE_SIZE, sprite.getCurrentSPrite(), false);
    }

    public Vector2i getPosition() {
        return position;
    }

    public abstract void applyEffect(Level level, Player player);
}
