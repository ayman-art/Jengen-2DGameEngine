package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.Entity;
import com.ayman.fightEnemies.entity.spawner.ParticleSpawner;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.entity.projectile.WizardProjectile;

import java.util.ArrayList;
import java.util.List;

public abstract class Mob extends Entity implements IMob, Cloneable {
    protected AnimatedSprite currentAnimatedSprite;
    protected int dir = 0; //direction of the mob, popular convention :(0 = north, 1 = east, 2 = south, 3 = west)
    protected boolean moving = false;

    protected List<Projectile> projectiles = new ArrayList<>(); // To keep track of projectiles fired by a specific mob

    protected int speed = 1;
    protected int health = 100;
    public void move(int xa, int ya) {

//        if(xa != 0 && ya != 0) {
//            move(xa, 0);
//            move(0, ya);
//            return;
//        }

        if(xa > 0) dir = 1; //east
        if(xa < 0) dir = 3; //west
        if(ya > 0) dir = 2; //south
        if(ya < 0) dir = 0; //north

        if(!collision(xa, ya)) {
            x += xa;
            y += ya;

//            ClientController.getInstance().sendPlayerPosition(x, y);

        } else {
//            new ParticleSpawner(x,y, 20, 1, level);
//            new ParticleSpawner(x, y, 5, 1, level, level.getTile(x /16, y /16));
        }
    }
    public abstract void update();

    public abstract void render(Screen screen);

    public void renderHealth(Screen screen) {
        int x = this.x - 16;
        int y = this.y - 16;
        screen.renderHealthBar(x, y, health);
    }

    public boolean collision(int xa, int ya) {
        boolean solid = false;
        int width = 16; //the width of the Mob collision box
        int height = 16; //the height of the Mob collision box
        int offSetX = 0; //the offset of the collision box from the Center of the Mob
        int offSetY = 0; //the offset of the collision box from the Center of the Mob
        for(int c = 0; c < 4; c++) {
            int xt = ((x + xa) + c % 2 * (width-1) + offSetX) >> 4; //the x coordinate of the tile the Mob is colliding with
            int yt = ((y + ya) + c / 2 * (height-1) + offSetY) >> 4; //the y coordinate of the tile the Mob is colliding with
            if(level.getTile(xt, yt).isSolid()) {
                solid = true;
                if(Math.abs(xa)<= 1 && Math.abs(ya) <= 1)
                    new ParticleSpawner(x, y, 4, 1, level, level.getTile(xt, yt));
            }
        }
        return solid;
    }


    private boolean collisionEntire(int xa, int ya) {
        int size = 16; //the size of the sprite
        int[][] corners = new int[][]{ //the corners of the sprite
                {-size, -size}, {size - 1, -size}, {-size, size - 1}, {size - 1, size - 1},
                {0, size - 1}, {0, -size}, {-size, 0}, {size - 1, 0}
        };
        for (int[] corner : corners) {
            int xc = x + corner[0] + xa;
            int yc = y + corner[1] + ya;
            System.out.println((xc >> 4) + ", " + (yc >> 4));
            System.out.println(level.getTile(xc >> 4, yc >> 4));
            if (level.getTile(xc >> 4, yc >> 4).isSolid()) {
                return true;
            }
        }
        return false;
    }

    public void shoot(int x, int y, double dir) {

        Projectile projectile = new WizardProjectile(x + 6, y, dir, level);
        projectiles.add(projectile);
        level.addProjectile(projectile);

//        ClientController.getInstance().sendPlayerShoot(x, y, dir);
    }

    public Sprite getSprite() {
        return currentAnimatedSprite.getCurrentSPrite();
    }



    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void updateHealth(int damage) {
        this.health -= Math.min(damage, this.health);
        if(health == 0) {
            System.exit(34);
        }

    }

    public int getHealth() {
        return health;
    }


    @Override
    public Entity clone() throws CloneNotSupportedException {
        Mob ret = (Mob) super.clone();
        ret.currentAnimatedSprite = currentAnimatedSprite.clone(); //deep copy
        return ret;
    }
    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public List<Projectile> getProjectiles() {
        return this.projectiles;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
