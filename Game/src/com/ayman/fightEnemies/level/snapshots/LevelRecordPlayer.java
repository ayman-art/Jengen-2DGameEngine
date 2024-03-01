package com.ayman.fightEnemies.level.snapshots;

import com.ayman.fightEnemies.Game;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.entity.mob.Player;
import com.ayman.fightEnemies.level.Level;

import static java.lang.Thread.sleep;

public class LevelRecordPlayer {

    private Game game;
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
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void quitRecording() {
        level.restoreSnapshot(
                levelCareTaker.getSnapshot(levelCareTaker.getNumberOfSnapshots() - 1)
        );
    }
}
