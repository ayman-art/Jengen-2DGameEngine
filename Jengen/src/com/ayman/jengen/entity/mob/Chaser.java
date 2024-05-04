package com.ayman.jengen.entity.mob;

import com.ayman.jengen.Graphics.AnimatedSprite;
import com.ayman.jengen.Graphics.Screen;
import com.ayman.jengen.Graphics.Sprite;
import com.ayman.jengen.Graphics.SpriteSheet;
import com.ayman.jengen.entity.projectile.WizardProjectile;
import com.ayman.jengen.level.Node;
import com.ayman.jengen.level.tile.Tile;
import com.ayman.jengen.util.Vector2i;

import java.util.List;
import java.util.Set;


/**
 * Chaser is a concrete class from the Mob class that represents the enemy that chases the player.
 */
public class Chaser extends Mob {


    protected AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 3);
    protected AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 3);
    protected AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 3);
    protected AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 3);

    protected int fireInterval = WizardProjectile.FIRE_INTERVAL;

    /**
     * The current enemy that the chaser is chasing and shooting.
     */
    protected IMob currentEnemy;
    /**
     * A flag to check if the player has come near the chaser. If true, the chaser will start chasing the player forever.
     */
    boolean near = false;


    /**
     * The path that the chaser will follow to reach the player. It is updated only when reaching a tile edge to avoid recalculating the path every frame.
     */
    List<Node> path = null;
    /**
     * The set of visited nodes in the path. It is used for debugging purposes and rendering them shows the efficiency of the pathfinding algorithm.
     */
    Set<Vector2i> visited = null;
    protected int time = 0;

    protected Vector2i vec;

    public Chaser(int x, int y) {
        init(level);
        this.x = x << 4;
        this.y = y << 4;
        this.vec = new Vector2i(x, y);
        this.currentAnimatedSprite = down;
    }

    public void update() {


        time++;
        if (currentEnemy == null) {
            currentEnemy = level.getPlayer();
        }


        int distancePow2 = (int) (Math.pow(level.getPlayer().getX() - x, 2) + Math.pow(level.getPlayer().getY() - y, 2));
        int distance = (int) Math.sqrt(distancePow2);
        if (distance <= CHASING_RANGE)
            near = true;

        if (!near) {
            return;
        } else if (this.currentAnimatedSprite.getCurrentSPrite().SIZE > distance) {
            if(time % 60 == 0)
                shoot(x, y, random.nextGaussian());
            return;
        }

        int xa = 0, ya = 0;

        if (x != vec.getX() * Sprite.TILE_SIZE || y != vec.getY() * Sprite.TILE_SIZE) {
            if (x < vec.getX() * Sprite.TILE_SIZE) xa++;
            if (x > vec.getX() * Sprite.TILE_SIZE) xa--;
            if (y < vec.getY() * Sprite.TILE_SIZE) ya++;
            if (y > vec.getY() * Sprite.TILE_SIZE) ya--;

        } else if (level.getPlayer().isVisible())
        {
            path =
                    level.findPath(new Vector2i(x >> 4, y >> 4),
                            new Vector2i(level.getPlayer().getX() >> 4, level.getPlayer().getY() >> 4));
//            visited = level.findVis(new Vector2i(x >> 4, y >> 4),
//                    new Vector2i(level.getPlayer().getX() >> 4, level.getPlayer().getY() >> 4)); // for debugging purposes

            if (path != null && !path.isEmpty()) {

                vec = path.get(0).tileCoordinate;

                if (x / Sprite.TILE_SIZE < vec.getX()) xa++;
                if (x / Sprite.TILE_SIZE > vec.getX()) xa--;
                if (y / Sprite.TILE_SIZE < vec.getY()) ya++;
                if (y / Sprite.TILE_SIZE > vec.getY()) ya--;

            }
        }


        if (xa < 0) {
            xa = -1;
            currentAnimatedSprite = left;
        } else if (xa > 0) {
            xa = 1;
            currentAnimatedSprite = right;
        }
        if (ya < 0) {
            ya = -1;
            currentAnimatedSprite = up;
        } else if (ya > 0) {
            ya = 1;
            currentAnimatedSprite = down;
        }


        if (xa != 0 || ya != 0) {
            moving = true;
            move(xa, ya);
        } else {
            moving = false;
            currentAnimatedSprite.restart();
        }


        if (moving) {
            currentAnimatedSprite.update();
        } else {
            currentAnimatedSprite.restart();
        }

        if (fireInterval >= 0)
            fireInterval--;

        updateShoot();

        clear();

    }


    protected void updateShoot() {
        if (this.currentEnemy == null) {
            return;
        }
        if (this.currentEnemy.isRemoved()) {
            this.currentEnemy = null;
            return;
        }
        if (this.currentEnemy instanceof IPlayer p && !p.isVisible())
            return;


        if (fireInterval > 0) {
            return;
        }


        double dx = currentEnemy.getX() - this.x;
        double dy = currentEnemy.getY() - this.y;

        if (dx * dx + dy * dy > Mob.SHOOTING_RANGE * Mob.SHOOTING_RANGE)
            return;
        double dir = Math.atan2(dy, dx);
        shoot(x, y, dir);


        fireInterval += WizardProjectile.FIRE_INTERVAL * (random.nextInt(3) + 2);
    }

    void clear() {
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).isRemoved()) {

                var del = projectiles.get(i);
                projectiles.remove(del);
                level.removeProjectile(del);
                i--; //to avoid skipping the next projectile in the list
            }
        }
    }
    public void render(Screen screen) {
        screen.renderMob(x, y, this, false);
    }

    /**
     * Renders the path that the chaser is following to reach the player for debugging purposes.
     * @param screen
     */
    public void renderPath(Screen screen) {
        if (visited != null) {
            for (Vector2i v : visited) {
                screen.renderTile(v.getX() << 4, v.getY() << 4, Tile.voidTile);
            }
        }
    }
}
