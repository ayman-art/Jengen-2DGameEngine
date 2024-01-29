package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.Graphics.SpriteSheet;

import java.util.Random;

public class Chaser extends Mob{

    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 3);
    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 3);


    private int time = 0;

    public Chaser(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        this.currentAnimatedSprite = down;
    }

    public void update() {

        time++;

        int xa = level.getPlayer().getX() - x;
        int ya = level.getPlayer().getY() - y;
        if(xa * xa + ya * ya < 50 * 50) {
            if(xa < 0) {
                xa = -1;
            } else if(xa > 0) {
                xa = 1;
            }
            if(ya < 0) {
                ya = -1;
            } else if(ya > 0) {
                ya = 1;
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


    }
    public void render(Screen screen) {
        screen.renderMob(x - 16, y - 16, this, false);
    }
}
