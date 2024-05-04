package com.ayman.jengen.entity.mob;

import com.ayman.jengen.Graphics.Sprite;
import com.ayman.jengen.entity.mob.decoratedPlayer.DecoratedPlayer;
import com.ayman.jengen.util.Vector2i;

/**
 * Helper is a concrete class from the Chaser class that represents a helper that helps the player in fighting.

 */
public class Helper extends Chaser {

    private final Player player;


    public Helper(Player player) {
        super(player.getX() / Sprite.TILE_SIZE, player.getY() / Sprite.TILE_SIZE);
        this.player = player;
    }


    @Override
    public void update() {

        if(player.isRemoved()) {
            remove();
            return;
        }

        // Preventing Multiple helpers from attacking each other.
        if(currentEnemy != null && currentEnemy instanceof Helper h) {
            if(h.player == this.player) {
                currentEnemy = null;
            }
        }


        if(currentEnemy == null || currentEnemy.isRemoved()) {

            if((level.getMobs().size() > 2))
                currentEnemy = level.getClosestMob(this, player);

            if
            (currentEnemy == this ||
                    currentEnemy == player ||
                        currentEnemy instanceof DecoratedPlayer decoratedPlayer && decoratedPlayer.getInnerMostPlayer() == player)
            {
                currentEnemy = null;
            }
            return;

        }
        time++;

        int distancePow2 = (int) (Math.pow(currentEnemy.getX() - x, 2) + Math.pow(currentEnemy.getY() - y, 2));
        int distance = (int) Math.sqrt(distancePow2);

        if(this.currentAnimatedSprite.getCurrentSPrite().SIZE > distance && time % (60*2) == 0) {
            shoot(x, y, random.nextGaussian());
        }

        int xa = 0, ya = 0;

        if(currentEnemy instanceof IPlayer player && !player.isVisible()) {
            return;
        }
        if(x !=vec.getX() * Sprite.TILE_SIZE || y != vec.getY() * Sprite.TILE_SIZE){
            if(x < vec.getX() * Sprite.TILE_SIZE) xa++;
            if(x > vec.getX() * Sprite.TILE_SIZE) xa--;
            if(y < vec.getY() * Sprite.TILE_SIZE) ya++;
            if(y > vec.getY() * Sprite.TILE_SIZE) ya--;

        } else {


            path =
                    level.findPath(new Vector2i(x >> 4, y >> 4),
                            new Vector2i(currentEnemy.getX() >> 4, currentEnemy.getY() >> 4));
//            visited = level.findVis(new Vector2i(x >> 4, y >> 4),
//                    new Vector2i(level.getPlayer().getX() >> 4, level.getPlayer().getY() >> 4));  // for debugging purposes

            if (path != null && !path.isEmpty()) {

                vec = path.get(0).tileCoordinate;

                if (x / Sprite.TILE_SIZE < vec.getX()) xa++;
                if (x / Sprite.TILE_SIZE > vec.getX()) xa--;
                if (y / Sprite.TILE_SIZE < vec.getY()) ya++;
                if (y / Sprite.TILE_SIZE > vec.getY()) ya--;

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

        if(fireInterval>=0)fireInterval--;

        updateShoot();

        clear();
    }



}
