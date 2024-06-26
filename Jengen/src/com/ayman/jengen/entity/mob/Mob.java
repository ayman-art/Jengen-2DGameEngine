package com.ayman.jengen.entity.mob;

import com.ayman.jengen.Graphics.AnimatedSprite;
import com.ayman.jengen.Graphics.Screen;
import com.ayman.jengen.Graphics.Sprite;
import com.ayman.jengen.audio.Sound;
import com.ayman.jengen.entity.Entity;
import com.ayman.jengen.entity.projectile.Projectile;
import com.ayman.jengen.entity.projectile.WizardProjectile;
import com.ayman.jengen.entity.spawner.ParticleSpawner;
import com.ayman.jengen.level.effects.Effect;
import com.ayman.jengen.network.client.controller.ClientController;

import java.util.ArrayList;
import java.util.List;

/**
 * Mob is an abstract class that represents the common methods and fields of the Mob entities.
 */
public abstract class Mob extends Entity implements IMob, Cloneable {


    public static int CHASING_RANGE = 600;
    public static int SHOOTING_RANGE = 200;
    public static int ORIGINAL_SPEED = 1;
    protected AnimatedSprite currentAnimatedSprite;
    protected int dir = 0; //direction of the mob, popular convention :(0 = north, 1 = east, 2 = south, 3 = west)
    protected boolean moving = false;

    protected List<Projectile> projectiles = new ArrayList<>(); // To keep track of projectiles fired by a specific mob

    protected int speed = ORIGINAL_SPEED;
    protected int health = 100;
    public void move(int xa, int ya) {

        if(xa > 0) dir = 1; //east
        if(xa < 0) dir = 3; //west
        if(ya > 0) dir = 2; //south
        if(ya < 0) dir = 0; //north

        if(!collision(xa, ya)) {
            x += xa;
            y += ya;

            if(ClientController.isOn() && this instanceof IPlayer)
                ClientController.getInstance().sendPlayerPosition(x, y, health);
        }
    }


    public abstract void update();

    public abstract void render(Screen screen);

    public void renderHealth(Screen screen) {
        int x = this.x - Sprite.TILE_SIZE;
        int y = this.y - Sprite.TILE_SIZE;
        screen.renderHealthBar(x, y, health);
    }

    public boolean collision(int xa, int ya) {
        boolean solid = false;
        int width = Sprite.TILE_SIZE; //the width of the Mob collision box
        int height = Sprite.TILE_SIZE; //the height of the Mob collision box
        int offSetX = 0; //the offset of the collision box from the Center of the Mob
        int offSetY = 0; //the offset of the collision box from the Center of the Mob
        for(int c = 0; c < 4; c++) {
            int xt = ((x + xa) + c % 2 * (width-1) + offSetX) >> 4; //the x coordinate of the tile the Mob is colliding with
            int yt = ((y + ya) + c / 2 * (height-1) + offSetY) >> 4; //the y coordinate of the tile the Mob is colliding with
            final boolean near = Math.abs(xa) <= 1 && Math.abs(ya) <= 1;
            if(level.getTile(xt, yt).isSolid()) {
                solid = true;
                if(this instanceof IPlayer player && player.isTileBreaker()) {
                    if(near) {
                        if(level.getTile(xt, yt).isBreakable()) {
                            new ParticleSpawner(getX(), getY(), 1, 1, getLevel(), getLevel().getTile(xt, yt));
                            getLevel().removeTile(xt, yt);

                            Sound.break_tileClip.setFramePosition(0);
                            Sound.break_tileClip.start();

                        }
                    }
                }
            } else if(this instanceof IPlayer && level.hasEffect(xt, yt)) {
                if(near) {
                    Effect effect = level.getEffect(xt, yt);
                    effect.applyEffect(level, (Player) this);
                    effect.remove();
                }
            }
        }
        return solid;
    }



    private boolean collisionEntire(int xa, int ya) {
        int size = Sprite.TILE_SIZE; //the size of the sprite
        int[][] corners = new int[][]{ //the corners of the sprite
                {-size, -size}, {size - 1, -size}, {-size, size - 1}, {size - 1, size - 1},
                {0, size - 1}, {0, -size}, {-size, 0}, {size - 1, 0}
        };
        for (int[] corner : corners) {
            int xc = x + corner[0] + xa;
            int yc = y + corner[1] + ya;
            System.out.println((xc >> 4) + ", " + (yc >> 4));
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

        if(ClientController.isOn() && this instanceof IPlayer)
            ClientController.getInstance().sendPlayerShoot(x, y, dir);
    }

    public Sprite getSprite() {
        return currentAnimatedSprite.getCurrentSPrite();
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
        health -= damage;
        if(health <= 0) {
            health = 0;
        } else if(health >= 100) {
            health = 100;
        }

        if(health == 0) {
            if( !(this instanceof IPlayer))
                remove();
            if(ClientController.isOn() && this instanceof IPlayer) {
                System.out.println("Loser!");
                System.exit(8086);
            }
        }

    }

    public int getHealth() {
        return health;
    }


    @Override
    public IMob clone() throws CloneNotSupportedException {
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
    public void setHealth(int health) {
        this.health = health;
    }
}
