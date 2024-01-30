package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.Graphics.SpriteSheet;
import com.ayman.fightEnemies.level.Node;
import com.ayman.fightEnemies.util.Vector2i;

import java.util.List;
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
        if(time%5 == 0) {
            return;
        }

        int distancePow2 = (int) (Math.pow(level.getPlayer().getX() - x, 2) + Math.pow(level.getPlayer().getY() - y, 2));
        int distance = (int) Math.sqrt(distancePow2);
        if(this.currentAnimatedSprite.getCurrentSPrite().SIZE > distance || distance > 1000) {
            return;
        }
//        int xa = level.getPlayer().getX() - x;
//        int ya = level.getPlayer().getY() - y;

        int xa = 0, ya = 0;

        List<Node> path =
                level.findPath(new Vector2i(x >> 4, y >> 4),
                        new Vector2i(level.getPlayer().getX() >> 4, level.getPlayer().getY() >> 4));

        if(path != null) {
            if(path.size() > 0) {
                Vector2i vec = path.get(path.size() - 1).tileCoordinate;
                if(x < vec.getX() << 4) xa++;
                if(x > vec.getX() << 4) xa--;
                if(y < vec.getY() << 4) ya++;
                if(y > vec.getY() << 4) ya--;
            }
        }



        if(xa < 0) {
            xa = -1;
            currentAnimatedSprite = left;
        } else if(xa > 0) {
            xa = 1;
            currentAnimatedSprite = right;
        }
        if(ya < 0) {
            ya = -1;
            currentAnimatedSprite = up;
        } else if(ya > 0) {
            ya = 1;
            currentAnimatedSprite = down;
        }


        if(xa != 0 || ya != 0){
            moving = true;
            move(xa, ya);
        } else {
            moving = false;
            currentAnimatedSprite.restart();
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
