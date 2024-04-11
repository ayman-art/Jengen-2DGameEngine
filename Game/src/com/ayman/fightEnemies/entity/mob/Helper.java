package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.util.Vector2i;

public class Helper extends Chaser {

    private IPlayer player;
    private IMob currentEnemy;


    public Helper(IPlayer player) {
        super(player.getX() / 16, player.getY() / 16);
        this.player = player;


    }


    @Override
    public void update() {
//        System.out.println("Update from Helper");
        if(currentEnemy == null) {

            if((level.getMobs().size() > 2))
                currentEnemy = level.getMobs().get(1 + random.nextInt(level.getMobs().size() - 1));
            if(currentEnemy == this || currentEnemy == player) {
                currentEnemy = null;
            }
            System.out.println(currentEnemy);
            return;
        }
        time++;

        int distancePow2 = (int) (Math.pow(currentEnemy.getX() - x, 2) + Math.pow(currentEnemy.getY() - y, 2));
        int distance = (int) Math.sqrt(distancePow2);
        if(this.currentAnimatedSprite.getCurrentSPrite().SIZE > distance || distance > 2000) {
            return;
        }
        else if(Math.abs(currentEnemy.getX() - x) <= 16 && Math.abs(currentEnemy.getY() - y) <= 16) {
            currentEnemy.updateHealth(20);
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

        fireInterval--;

        updateShoot();

        clear();
    }
}
