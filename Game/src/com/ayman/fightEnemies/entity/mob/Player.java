package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.GameController;
import com.ayman.fightEnemies.Graphics.AnimatedSprite;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.SpriteSheet;
import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.Input.Mouse;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.entity.projectile.WizardProjectile;

public class Player extends Mob implements IPlayer {


    private String name;

    private final Keyboard keyboard;
    private final Mouse mouse;
    private Projectile projectile;
    private int fireInterval = 0;

    private boolean visible = true;

    private boolean tileBreaker = false;
    private int coins = 0;

    private boolean moving = false;
    private final AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 3);
    private final AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 3);
    private final AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 3);
    private final AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 3);


    public Player(Keyboard keyboard, Mouse mouse) {
        this.keyboard = keyboard;
        this.mouse = mouse;
    }
    public Player(Player copy) {
        this(copy.x, copy.y, copy.keyboard, copy.mouse); //call the constructor that takes x, y, and input (Keyboard, Mouse)
        this.name = copy.name;
        this.projectile = copy.projectile;
        this.fireInterval = copy.fireInterval;
        this.visible = copy.visible;
        this.tileBreaker = copy.tileBreaker;
        this.coins = copy.coins;
        this.moving = copy.moving;
        this.currentAnimatedSprite = down;

    }
    public Player() {
        keyboard = null;
        mouse = null;
    }
    public Player(int x, int y, Keyboard keyboard, Mouse mouse) { //if we want to spawn the player at a specific location
        this.x = x;
        this.y = y;
        this.keyboard = keyboard;
        this.mouse = mouse;
        this.currentAnimatedSprite = down;

        fireInterval = WizardProjectile.FIRE_INTERVAL;
    }



    public Player(String name, int x, int y, Keyboard keyboard, Mouse mouse) {
        this(x, y, keyboard, mouse);
        this.name = name;
    }


    public void update() {
        if(keyboard == null) return;
        int xa = 0, ya = 0;
        if(keyboard.up) {
            ya--;
            currentAnimatedSprite = up;
        }
        if(keyboard.down) {
            ya++;
            currentAnimatedSprite = down;
        }
        if(keyboard.left) {
            xa--;
            currentAnimatedSprite = left;
        }
        if(keyboard.right) {
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
                i--; //to avoid skipping the next projectile in the same loop
            }
        }

    }


    protected void updateShoot() {

        if(fireInterval > 0) {
            return;
        }

        assert mouse != null;
        if(mouse.getButton() == 1) {
            double dx = mouse.getX() - (double) (GameController.width * GameController.scaleFactor) / 2;
            double dy = mouse.getY() - (double) (GameController.height * GameController.scaleFactor) / 2;

//            System.out.println("dx: " + dx + ", dy: " + dy);
            double dir = Math.atan2(dy, dx);
            shoot(x, y, dir);


        }

        for(IMob mob : level.getMobs()) {
            for(Projectile projectile : mob.getProjectiles()) {
                if(projectile.isRemoved()) continue;
                for(IMob otherMob : level.getMobs()) {
                    if(mob != otherMob) {

                        int xProjectile = projectile.getX() + 8;
                        int yProjectile = projectile.getY() + 8;
                        if(level.isInside(xProjectile, yProjectile, otherMob.getX(), otherMob.getY(), 16)
                                || level.isInside(xProjectile, yProjectile, otherMob.getX() + 16, otherMob.getY(), 16)
                                || level.isInside(xProjectile + 16, yProjectile, otherMob.getX(), otherMob.getY(), 16)
                                || level.isInside(xProjectile, yProjectile + 16, otherMob.getX(), otherMob.getY(), 16)
                                || level.isInside(xProjectile + 16, yProjectile + 16, otherMob.getX(), otherMob.getY(), 16)
                                || level.isInside(xProjectile + 8, yProjectile + 8, otherMob.getX(), otherMob.getY(), 16)) {

                            projectile.remove();
//                            level.removeProjectile(projectile);

                            if(otherMob instanceof IPlayer player) {
                                player.updateHealth(projectile.getDamage());
                                if(player.getHealth() <= 0) {
//                                    System.exit(11);
                                }
                            }
                            else {
                                otherMob.updateHealth(projectile.getDamage() * 20);
                                if(otherMob.getHealth() <= 0) {
                                    otherMob.remove();
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
    public boolean isTileBreaker() {
        return this.tileBreaker;
    }
    public void setTileBreaker(boolean tileBreaker) {
        this.tileBreaker = tileBreaker;
    }

    @Override
    public void addCoins(int coins) {
        this.coins += coins;
    }

    @Override
    public int getCoins() {
        return coins;
    }
}