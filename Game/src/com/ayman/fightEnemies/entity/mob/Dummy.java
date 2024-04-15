package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.SpriteSheet;

import java.util.Random;

public class Dummy extends Mob{

    private final AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 3);
    private final AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 3);
    private final AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 3);
    private final AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 3);


    private final Random random = new Random();
    private int time = 0;
    protected enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }
    protected Direction direction = Direction.DOWN;

    public Dummy(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        this.currentAnimatedSprite = down;
    }

    public void update() {

        time++;

        if(time % ( 60) == 0) {
            Direction newDirection = Direction.values()[random.nextInt(5)];
            if(direction != newDirection) {
                direction = newDirection;
            } else {
                direction = Direction.NONE;
            }
        }

        if(random.nextInt(100) == 0) {
            shoot(x, y, random.nextGaussian());
        }


        int xa = 0, ya = 0;
        if(direction == Direction.UP) {
            ya--;
            currentAnimatedSprite = up;
        }
        if(direction == Direction.DOWN) {
            ya++;
            currentAnimatedSprite = down;
        }
        if(direction == Direction.LEFT) {
            xa--;
            currentAnimatedSprite = left;
        }
        if(direction == Direction.RIGHT) {
            xa++;
            currentAnimatedSprite = right;
        }
        if(direction == Direction.NONE) {
            currentAnimatedSprite.restart();
        }

        if(level.tileCollision(this.x + xa, this.y + ya, 16, 0,0)) {
            direction = Direction.NONE;
            return;
        }

        if(xa != 0 || ya != 0){
            moving = true;
            move(xa, ya);
        } else {
            moving = false;
        }


        if(moving) {
            currentAnimatedSprite.update();
        } else {
            currentAnimatedSprite.restart();
        }





    }
    public void render(Screen screen) {
        screen.renderMob(x - 16, y - 16, this, false);
    }
}
