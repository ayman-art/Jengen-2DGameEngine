package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Game;
import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.Graphics.SpriteSheet;
import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.Input.Mouse;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.entity.projectile.WizardProjectile;

public class Player extends Mob {

    private Keyboard input;
    private int anim = 0;

    private Projectile projectile;
    private int fireInterval = 0;

    private boolean moving = false;
    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);

    private AnimatedSprite currentAnim = down;


    public Player(Keyboard input) {
        this.input = input;
    }
    public Player(int x, int y, Keyboard input) { //if we want to spawn the player at a specific location
        this.x = x;
        this.y = y;
        this.input = input;
        this.sprite = Sprite.player_forward;

        fireInterval = WizardProjectile.FIRE_INTERVAL;
    }


    public void update() {
        int xa = 0, ya = 0;
        if(input.up) ya--;
        if(input.down) ya++;
        if(input.left) xa--;
        if(input.right) xa++;
        if(xa != 0 || ya != 0){
            moving = true;
            move(xa, ya);
        } else {
            moving = false;
        }


        if(anim < 1000) anim++;
        else anim = 0;

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
        boolean flip = false;

        if(dir == 0) {
            sprite = Sprite.player_forward;
            if(moving) {
                if(anim % 20 > 10) { //change the sprite every 10 frames
                    sprite = Sprite.player_forward_1;
                } else {
                    sprite = Sprite.player_forward_2;
                }
            }
        }
        if(dir == 1 || dir == 3) {
            if(dir == 3) flip = true;
            sprite = Sprite.player_side;
            if(moving) {
                if(anim % 20 > 10) { //change the sprite every 10 frames
                    sprite = Sprite.player_side_1;
                } else {
                    sprite = Sprite.player_side_2;
                }
            }
        }
        if(dir == 2) {
            sprite = Sprite.player_backwards;
            if(moving) {
                if(anim % 20 > 10) { //change the sprite every 10 frames
                    sprite = Sprite.player_backwards_1;
                } else {
                    sprite = Sprite.player_backwards_2;
                }
            }
        }

        screen.renderMob(x - 16, y - 16, sprite, flip);
    }
}