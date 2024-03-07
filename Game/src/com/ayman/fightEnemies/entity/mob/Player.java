package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Game;
import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.SpriteSheet;
import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.Input.Mouse;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.entity.projectile.WizardProjectile;

public class Player extends Mob implements IPlayer {


    private String name;

    private final Keyboard input;
    private Projectile projectile;
    private int fireInterval = 0;

    private boolean visible = true;
    private int coins = 0;

    private boolean moving = false;
    private final AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_up, 1);
    private final AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 1);
    private final AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 1);
    private final AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 1);


    public Player(Keyboard input) {
        this.input = input;
    }
    public Player(Player copy) {
        this(copy.x, copy.y, copy.input); //call the constructor that takes x, y, and input (Keyboard
        this.projectile = copy.projectile;
        this.fireInterval = copy.fireInterval;
        this.visible = copy.visible;
        this.coins = copy.coins;
        this.moving = copy.moving;

        this.currentAnimatedSprite = down;

        fireInterval = WizardProjectile.FIRE_INTERVAL;
    }
    public Player() {input = null;}
    public Player(int x, int y, Keyboard input) { //if we want to spawn the player at a specific location
        this.x = x;
        this.y = y;
        this.input = input;
        this.currentAnimatedSprite = down;

        fireInterval = WizardProjectile.FIRE_INTERVAL;
    }



    public Player(String name, int x, int y, Keyboard input) {
        this(x, y, input);
        this.name = name;
    }


    public void update() {
        if(input == null) return;
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
            int maxXa = 0, maxYa = 0;

            int width = 16, height = 16;
            int speed = getSpeed();
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

    protected void clear() {
        for(int i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).isRemoved()) {

                var del = projectiles.get(i);
                projectiles.remove(del);
                level.removeProjectile(del);
                i--; //to avoid skipping the next projectile in the list
            }
        }

    }


    protected void updateShoot() {

        if(fireInterval > 0) {
            return;
        }

        if(Mouse.getButton() == 1) {
            double dx = Mouse.getX() - (double) (Game.width * Game.scaleFactor) / 2;
            double dy = Mouse.getY() - (double) (Game.height * Game.scaleFactor) / 2;

//            System.out.println("dx: " + dx + ", dy: " + dy);
            double dir = Math.atan2(dy, dx);
            shoot(x, y, dir);


        }

        for(Mob mob : level.getMobs()) {
            for(Projectile projectile : mob.projectiles) {
                for(Mob otherMob : level.getMobs()) {
                    if(mob != otherMob) {
                        int xProjectile = projectile.getX();
                        int yProjectile = projectile.getY();
                        if(xProjectile >= otherMob.getX() && xProjectile <= otherMob.getX() + 16
                                && yProjectile >= otherMob.getY() && yProjectile <= otherMob.getY() + 16) {
//                            otherMob.remove();


//                            projectile.remove();
                            level.removeProjectile(projectile);

                            if(otherMob instanceof Player player) {
                                player.updateHealth(projectile.getDamage());
                                if(player.getHealth() <= 0) {
                                    System.exit(11);
                                }
                            }
                            else {
                                otherMob.updateHealth(projectile.getDamage());
                                if(otherMob.getHealth() <= 0) {
                                    level.removeMob(otherMob);
                                }
                            }
                            break;
                        }
                    }
                }
            }
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

        screen.renderMob(x , y, this, false);
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}