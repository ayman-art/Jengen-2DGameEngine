package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Game;
import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.SpriteSheet;
import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.Input.Mouse;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.entity.projectile.WizardProjectile;

public class Player extends Mob {

    private Keyboard input;

    private Projectile projectile;
    private int fireInterval = 0;

    private boolean moving = false;
    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 3);


    public Player(Keyboard input) {
        this.input = input;
    }
    public Player(int x, int y, Keyboard input) { //if we want to spawn the player at a specific location
        this.x = x;
        this.y = y;
        this.input = input;
        this.currentAnimatedSprite = down;

        fireInterval = WizardProjectile.FIRE_INTERVAL;
    }


    public void update() {
        int xa = 0, ya = 0;
        if(input.up) {
            ya--;
            currentAnimatedSprite = up;
        }
        if(input.down) {
            ya++;
            currentAnimatedSprite = down;
        }
        if(input.left) {
            xa--;
            currentAnimatedSprite = left;
        }
        if(input.right) {
            xa++;
            currentAnimatedSprite = right;
        }
        if(xa != 0 || ya != 0) {
            moving = true;
            int speed = 1;
            int maxXa = 0, maxYa = 0;

            int width = 16, height = 16;
            if (xa != 0) {
                while(!collision(maxXa + width * xa, 0) && Math.abs(maxXa + width * xa) <= speed)
                    maxXa += width * xa;
                while (!collision(maxXa + xa, 0) && Math.abs(maxXa + xa)  <= speed)
                    maxXa += xa;

            }

            if (ya != 0) {
                while(!collision(0, maxYa + height * ya) && Math.abs(maxYa + height * ya ) <= speed)
                    maxYa += height * ya;
                while (!collision(0, maxYa + ya) && Math.abs(maxYa + ya) <= speed)
                    maxYa += ya;

            }

            if(collision(maxXa + xa, 0)) move(maxXa + xa, 0);
            if(collision(0, maxYa + ya)) move(0, maxYa + ya);
            move(maxXa, maxYa);


        } else {
            moving = false;
        }


        if(moving) {
            currentAnimatedSprite.update();
        } else {
            currentAnimatedSprite.restart();
        }

        fireInterval--;





        updateShoot();
        //System.out.println(projectiles.size());

        clear();
    }

    private void clear() {
        for(int i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).isRemoved()) {

                var del = projectiles.get(i);
                projectiles.remove(del);
                level.removeProjectile(del);
                i--; //to avoid skipping the next projectile in the list
            }
        }

    }


    private void updateShoot() {

        if(fireInterval > 0) {
            return;
        }

        if(Mouse.getButton() == 1) {
            double dx = Mouse.getX() - (Game.width * Game.scaleFactor) / 2;
            double dy = Mouse.getY() - (Game.height * Game.scaleFactor)/ 2;

//            System.out.println("dx: " + dx + ", dy: " + dy);
            double dir = Math.atan2(dy, dx);
            shoot(x, y, dir);


        }

        fireInterval += WizardProjectile.FIRE_INTERVAL;
    }


    public void render(Screen screen) {
//        boolean flip = false;
//
//        if(dir == 0) {
//            sprite = Sprite.player_forward;
//            if(moving) {
//                if(anim % 20 > 10) { //change the sprite every 10 frames
//                    sprite = Sprite.player_forward_1;
//                } else {
//                    sprite = Sprite.player_forward_2;
//                }
//            }
//        }
//        if(dir == 1 || dir == 3) {
//            if(dir == 3) flip = true;
//            sprite = Sprite.player_side;
//            if(moving) {
//                if(anim % 20 > 10) { //change the sprite every 10 frames
//                    sprite = Sprite.player_side_1;
//                } else {
//                    sprite = Sprite.player_side_2;
//                }
//            }
//        }
//        if(dir == 2) {
//            sprite = Sprite.player_backwards;
//            if(moving) {
//                if(anim % 20 > 10) { //change the sprite every 10 frames
//                    sprite = Sprite.player_backwards_1;
//                } else {
//                    sprite = Sprite.player_backwards_2;
//                }
//            }
//        }

        screen.renderMob(x - 16, y - 16, this, false);
    }

}