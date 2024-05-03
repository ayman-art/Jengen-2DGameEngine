package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.entity.mob.decoratedPlayer.DecoratedPlayer;
import com.ayman.fightEnemies.util.Vector2i;

public class Helper extends Chaser {

    private final Player player;


    public Helper(Player player) {
        super(player.getX() / 16, player.getY() / 16);
        this.player = player;
    }


    @Override
    public void update() {

//        System.out.println("Update from Helper");
        if(player.isRemoved()) {
            remove();
            return;
        }

        // Preventing Multiple helpers from attacking each other.
        if(currentEnemy != null && currentEnemy instanceof Helper h) {
            if(h.player == this.player) {
                currentEnemy = null;
            }
            System.out.println("Helper is attacking another helper");
        }


        if(currentEnemy == null || currentEnemy.isRemoved()) {

            if((level.getMobs().size() > 2))
                currentEnemy = level.getClosestMob(this, player);
            if
            (
                    currentEnemy == this ||
                    currentEnemy == player ||
                    currentEnemy instanceof DecoratedPlayer decoratedPlayer && decoratedPlayer.getInnerMostPlayer() == player
            ) {
                if(currentEnemy == this) System.out.println("this");
                if(currentEnemy == player) System.out.println("player");
                if(currentEnemy instanceof DecoratedPlayer decoratedPlayer && decoratedPlayer.getInnerMostPlayer() == player) {
                    System.out.println("lela tawela");
                }
                currentEnemy = null;
                System.out.println("Helper is attacking itself or the player");
            }
//            System.out.println(currentEnemy);
            return;
        }
        time++;

        int distancePow2 = (int) (Math.pow(currentEnemy.getX() - x, 2) + Math.pow(currentEnemy.getY() - y, 2));
        int distance = (int) Math.sqrt(distancePow2);
        if( distance > CHASING_RANGE) {
//            return;
        }
        else if(this.currentAnimatedSprite.getCurrentSPrite().SIZE > distance && time % (60*2) == 0) {
            shoot(x, y, random.nextGaussian());
        }
//        int xa = level.getPlayer().getX() - x;
//        int ya = level.getPlayer().getY() - y;

        int xa = 0, ya = 0;

        if(currentEnemy instanceof IPlayer player && !player.isVisible()) {
            return;
        }
        if(x !=vec.getX() * 16 || y != vec.getY() * 16){
            if(x < vec.getX() * 16) xa++;
            if(x > vec.getX() * 16) xa--;
            if(y < vec.getY() * 16) ya++;
            if(y > vec.getY() * 16) ya--;

        } else {


            path =
                    level.findPath(new Vector2i(x >> 4, y >> 4),
                            new Vector2i(currentEnemy.getX() >> 4, currentEnemy.getY() >> 4));
//            visited = level.findVis(new Vector2i(x >> 4, y >> 4),
//                    new Vector2i(level.getPlayer().getX() >> 4, level.getPlayer().getY() >> 4));

            if (path != null && !path.isEmpty()) {

                vec = path.get(0).tileCoordinate;

                if (x / 16 < vec.getX()) xa++;
                if (x / 16 > vec.getX()) xa--;
                if (y / 16 < vec.getY()) ya++;
                if (y / 16 > vec.getY()) ya--;

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
