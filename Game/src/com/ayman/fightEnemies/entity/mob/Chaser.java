package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.SpriteSheet;
import com.ayman.fightEnemies.level.Node;
import com.ayman.fightEnemies.level.tile.Tile;
import com.ayman.fightEnemies.util.Vector2i;

import java.util.List;
import java.util.Set;

public class Chaser extends Mob{

    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 3);
    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 3);


    List<Node> path = null;
    Set<Vector2i> visited = null;
    private int time = 0;

    private Vector2i vec;

    public Chaser(int x, int y) {
        init(level);
        this.x = x << 4;
        this.y = y << 4;
        this.vec = new Vector2i(x, y);
        this.currentAnimatedSprite = down;
    }

    public void update() {

        time++;


        int distancePow2 = (int) (Math.pow(level.getPlayer().getX() - x, 2) + Math.pow(level.getPlayer().getY() - y, 2));
        int distance = (int) Math.sqrt(distancePow2);
        if(this.currentAnimatedSprite.getCurrentSPrite().SIZE > distance || distance > 1000) {
            return;
        }
//        int xa = level.getPlayer().getX() - x;
//        int ya = level.getPlayer().getY() - y;

        int xa = 0, ya = 0;

        if(x !=vec.getX() * 16 || y != vec.getY() * 16){
            if(x < vec.getX() * 16) xa++;
            if(x > vec.getX() * 16) xa--;
            if(y < vec.getY() * 16) ya++;
            if(y > vec.getY() * 16) ya--;

        }
        else {
            path =
                    level.findPath(new Vector2i(x >> 4, y >> 4),
                            new Vector2i(level.getPlayer().getX() >> 4, level.getPlayer().getY() >> 4));
            visited = level.findVis(new Vector2i(x >> 4, y >> 4),
                    new Vector2i(level.getPlayer().getX() >> 4, level.getPlayer().getY() >> 4));

            if (path != null) {
                if (path.size() >   1) {
                    vec = path.get(0).tileCoordinate;

                    if (x / 16 < vec.getX()) xa++;
                    if (x / 16 > vec.getX()) xa--;
                    if (y / 16 < vec.getY()) ya++;
                    if (y / 16 > vec.getY()) ya--;
                }
                else System.exit(0);
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
        screen.renderMob(x, y, this, false);
    }
    public void renderPath(Screen screen) {
        if(visited == null) {
            return;
        } else {
            System.out.println("working");
            for(Vector2i v : visited) {
                screen.renderTile(v.getX() << 4, v.getY() << 4, Tile.voidTile);}
        }
    }

}
