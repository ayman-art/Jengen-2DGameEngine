package com.ayman.fightEnemies.level.snapshots;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.level.Level;

public class LevelRecordPlayer {
    private Level level;
    private Screen screen;
    private LevelCareTaker levelCareTaker;


    public LevelRecordPlayer(Level level, Screen screen,  LevelCareTaker levelCareTaker) {
        this.level = level;
        this.screen = screen;
        this.levelCareTaker = levelCareTaker;
    }

    public void playRecord() {
        for (int i = 0; i < levelCareTaker.getNumberOfSnapshots(); i++) {
            level.restoreSnapshot(levelCareTaker.getSnapshot(i));
            level.render(level.getPlayer().x, level.getPlayer().y, screen);
        }
    }
}
