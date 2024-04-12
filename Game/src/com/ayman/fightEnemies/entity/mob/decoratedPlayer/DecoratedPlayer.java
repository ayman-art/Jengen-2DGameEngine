package com.ayman.fightEnemies.entity.mob.decoratedPlayer;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;
import com.ayman.fightEnemies.entity.mob.IMob;
import com.ayman.fightEnemies.entity.mob.IPlayer;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.level.Level;

import java.util.List;

public  abstract class DecoratedPlayer implements IPlayer {


  IPlayer player;
  int time;

  boolean stillDecorated;
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
        return false;
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
    public void setSpeed(int speed) {
        player.setSpeed(speed);
    }

    public boolean timeOut() {
        return time <= 0;
    }


    public IPlayer restorePlayer() {
        if(isStillDecorated()) {
            removeDecoration();
        }
//        if(player instanceof DecoratedPlayer decoratedPlayer && decoratedPlayer.timeOut()) {
//            return decoratedPlayer.restorePlayer();
//        }
        return player;
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

    public boolean isStillDecorated(){
        return stillDecorated;
    }
    public void setStillDecorated(boolean stillDecorated){
        this.stillDecorated = stillDecorated;
    }

    public void removeDecoration(){};

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
}
