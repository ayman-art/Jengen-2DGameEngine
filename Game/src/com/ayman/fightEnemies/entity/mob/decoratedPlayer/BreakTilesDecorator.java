package com.ayman.fightEnemies.entity.mob.decoratedPlayer;

import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.spawner.ParticleSpawner;

public class BreakTilesDecorator extends DecoratedPlayer{
    public BreakTilesDecorator(IPlayer player) {
        super(player);
        time = 50;
        System.out.println("BreakTilesDecorator1");
    }

    @Override
    public void update() {
        if (!isStillDecorated()) {
            player.update();
            return;
        }


        player.update();
        if (player.collision(player.getX() - 3, player.getY() - 3)) {
            System.out.println("Breaking Tiles");
        }
        System.out.println("BreakTilesDecorator");

        time--;
        if (time == 0) {
            removeDecoration();
            setStillDecorated(false);
        }
    }

    @Override
    public boolean collision(int xa, int ya) {
        boolean solid = false;
        int width = 16; //the width of the Mob collision box
        int height = 16; //the height of the Mob collision box
        int offSetX = 0; //the offset of the collision box from the Center of the Mob
        int offSetY = 0; //the offset of the collision box from the Center of the Mob
        for(int c = 0; c < 4; c++) {
            int xt = ((getX() + xa) + c % 2 * (width-1) + offSetX) >> 4; //the x coordinate of the tile the Mob is colliding with
            int yt = ((getY() + ya) + c / 2 * (height-1) + offSetY) >> 4; //the y coordinate of the tile the Mob is colliding with
            if(getLevel().getTile(xt, yt).isSolid()) {
                solid = true;
                if(Math.abs(xa)<= 1 && Math.abs(ya) <= 1) {
                new ParticleSpawner(getX(), getY(), 1, 1, getLevel(), getLevel().getTile(xt, yt));
                    getLevel().removeTile(xt, yt);
                }
            }
        }
        return solid;
    }


}
