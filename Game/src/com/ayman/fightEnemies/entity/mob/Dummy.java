package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.Graphics.SpriteSheet;

import java.util.Random;

public class Dummy extends Mob{

    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 3);
    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 3);

    private AnimatedSprite currentAnimatedSprite = down;

    private Random random = new Random();
    private int time = 0;
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    protected Direction direction = Direction.DOWN;

    public Dummy(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        this.sprite = Sprite.player_backwards;
    }

    public void update() {

        time++;

        if(time % (random.nextInt(50) + 30) == 0) {
            direction = Direction.values()[random.nextInt(4)];
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
        screen.renderMob(x - 16, y - 16, currentAnimatedSprite.getCurrentSPrite(), false);
    }
}
