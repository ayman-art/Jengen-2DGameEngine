package com.ayman.jengen.entity.mob.decoratedPlayer;

import com.ayman.jengen.Graphics.Screen;
import com.ayman.jengen.Graphics.Sprite;
import com.ayman.jengen.entity.mob.IMob;
import com.ayman.jengen.entity.mob.IPlayer;
import com.ayman.jengen.entity.mob.Player;
import com.ayman.jengen.entity.projectile.Projectile;
import com.ayman.jengen.level.Level;

import java.util.List;

/**
 * DecoratedPlayer is the abstract decorator class that implements the IPlayer interface.
 * It has a reference to the IPlayer object and a boolean flag to check if the player is still decorated.
 * It implements all the methods of the IPlayer interface and delegates the calls to the IPlayer object.
 * It also has a method to restore the original player object by making recursive calls to the restorePlayer method.
 */
public  abstract class DecoratedPlayer implements IPlayer {

    /**
     * The IPlayer object that is being decorated.
     */
  IPlayer player;
    /**
     * The time left for the decoration effect to wear off.
     */
  int time;
    /**
     * A flag to check if the player is still decorated.
     */
  boolean stillDecorated;

    /**
     * Constructor to create a DecoratedPlayer object with the given IPlayer object.
     * @param player The IPlayer object to be decorated.
     */
    public DecoratedPlayer(IPlayer player) {
        this.player = player;
        stillDecorated = true;
    }

    @Override
    public void init(Level level) {
        player.init(level);
    }

    @Override
    public void remove() {
        player.remove();

    }

    @Override
    public boolean isRemoved() {
        return player.isRemoved();
    }


    @Override
    public void move(int xa, int ya) {
        player.move(xa, ya);
    }

    @Override
    public void renderHealth(Screen screen) {
        player.renderHealth(screen);
    }

    @Override
    public void shoot(int x, int y, double dir) {
        player.shoot(x, y, dir);
    }

    @Override
    public Sprite getSprite() {
        return player.getSprite();
    }

    @Override
    public int getX() {
        return player.getX();
    }

    @Override
    public int getY() {
        return player.getY();
    }

    @Override
    public void setX(int x) {
        player.setX(x);
    }

    @Override
    public void setY(int y) {
        player.setY(y);
    }

    @Override
    public void setXY(int x, int y) {
        player.setXY(x, y);
    }

    @Override
    public void updateHealth(int damage) {
        player.updateHealth(damage);
    }

    @Override
    public IMob clone() throws CloneNotSupportedException {
        return player.clone();
    }

    @Override
    public int getSpeed() {
        return player.getSpeed();
    }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void render(Screen screen) {
        player.render(screen);
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public boolean isVisible() {
       return player.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        player.setVisible(visible);
    }

    @Override
    public int getHealth() {
        return player.getHealth();
    }

    @Override
    public List<Projectile> getProjectiles() {
        return player.getProjectiles();
    }
    @Override
    public void setSpeed(int speed) {
        player.setSpeed(speed);
    }
    @Override
    public boolean collision(int xa, int ya) {
        return player.collision(xa, ya);
    }

    @Override
    public boolean isTileBreaker() {
        return player.isTileBreaker();
    }
    @Override
    public void setTileBreaker(boolean tileBreaker) {
        player.setTileBreaker(tileBreaker);
    }

    public boolean timeOut() {
        return time <= 0;
    }

    public IPlayer restorePlayer() {
        if(isStillDecorated()) {
            removeDecoration();
        }
        return player;
    }
    public IPlayer getPlayer() {
        return player;
    }

    public Player getInnerMostPlayer() {
        if(player instanceof DecoratedPlayer decoratedPlayer) {
            return decoratedPlayer.getInnerMostPlayer();
        }
        return (Player) player;
    }

    public boolean isStillDecorated(){
        return stillDecorated;
    }
    public void setStillDecorated(boolean stillDecorated){
        this.stillDecorated = stillDecorated;
    }

    public abstract void removeDecoration();

    @Override
    public Level getLevel() {
        return player.getLevel();
    }

    @Override
    public void addCoins(int coins) {
        player.addCoins(coins);
    }

    @Override
    public int getCoins() {
        return player.getCoins();
    }

    @Override
    public void setHealth(int health) {
        player.setHealth(health);
    }
}
