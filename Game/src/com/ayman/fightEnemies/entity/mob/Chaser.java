package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.SpriteSheet;
import com.ayman.fightEnemies.entity.projectile.WizardProjectile;
import com.ayman.fightEnemies.level.Node;
import com.ayman.fightEnemies.level.tile.Tile;
import com.ayman.fightEnemies.util.Vector2i;

import java.util.List;
import java.util.Set;

public class Chaser extends Mob{

    protected AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 3);
    protected AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 3);
    protected AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 3);
    protected AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 3);

    protected int fireInterval = WizardProjectile.FIRE_INTERVAL;

    protected IMob currentEnemy;


    List<Node> path = null;
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
        if(currentEnemy == null) {
            currentEnemy = level.getPlayer();
        }


        IPlayer player = (IPlayer) currentEnemy;
        int distancePow2 = (int) (Math.pow(level.getPlayer().getX() - x, 2) + Math.pow(level.getPlayer().getY() - y, 2));
        int distance = (int) Math.sqrt(distancePow2);
        if(this.currentAnimatedSprite.getCurrentSPrite().SIZE > distance || distance > CHASING_RANGE) {
            return;
        }
        else if(Math.abs(player.getX() - x) <= 16 && Math.abs(player.getY() - y) <= 16) {
            player.updateHealth(20);
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
        else if(level.getPlayer().isVisible())// if(time % 60 == 0)
        {
            path =
                    level.findPath(new Vector2i(x >> 4, y >> 4),
                            new Vector2i(level.getPlayer().getX() >> 4, level.getPlayer().getY() >> 4));
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

        if(fireInterval>=0)
            fireInterval--;

        updateShoot();

        clear();
//        update2();

    }

    public void update2() {
        time++;

        IPlayer player = level.getPlayer();
        int distancePow2 = (int) (Math.pow(level.getPlayer().getX() - x, 2) + Math.pow(level.getPlayer().getY() - y, 2));
        int distance = (int) Math.sqrt(distancePow2);
        if(this.currentAnimatedSprite.getCurrentSPrite().SIZE > distance || distance > CHASING_RANGE || !player.isVisible()) {
            return;
        }
        else if(Math.abs(player.getX() - x) <= 16 && Math.abs(player.getY() - y) <= 16) {
            player.updateHealth(20);
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
        else if(level.getPlayer().isVisible())// if(time % 60 == 0)
        {
            path =
                    level.findPath(new Vector2i(x >> 4, y >> 4),
                            new Vector2i(level.getPlayer().getX() >> 4, level.getPlayer().getY() >> 4));
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

        if(fireInterval>=0)
            fireInterval--;

        updateShoot();

        clear();

    }


    protected void updateShoot() {
        if(this.currentEnemy == null) {
            return;
        }
        if(this.currentEnemy.isRemoved()) {
            this.currentEnemy = null;
            return;
        }
        if(this.currentEnemy instanceof IPlayer p &&  !p.isVisible())
            return;



        if(fireInterval > 0) {
            return;
        }




            double dx = currentEnemy.getX() - this.x;
            double dy = currentEnemy.getY() - this.y;

            if(dx*dx + dy*dy > Mob.SHOOTING_RANGE * Mob.SHOOTING_RANGE) return;
//            System.out.println("dx: " + dx + ", dy: " + dy);
            double dir = Math.atan2(dy, dx);
            shoot(x, y, dir);




        fireInterval += WizardProjectile.FIRE_INTERVAL * random.nextInt(3) + 2;
    }

    void clear() {
        for(int i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).isRemoved()) {

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
    public void renderPath(Screen screen) {
//        if(1 == 1) return ;
        if(visited == null) {
            return;
        } else {
//            System.out.println("working");
            for(Vector2i v : visited) {
                screen.renderTile(v.getX() << 4, v.getY() << 4, Tile.voidTile);}
        }






}}
