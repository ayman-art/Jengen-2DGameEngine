package com.ayman.fightEnemies.level.snapshots;

import java.util.Collection;
import java.util.List;

public class LevelCareTaker {
    private List<LevelSnapshot> levelSnapshots;

    public void addSnapshot(LevelSnapshot snapshot) {
        levelSnapshots.add(snapshot);
    }

    public LevelSnapshot getSnapshot(int index) {
        return levelSnapshots.get(index);
    }

    public int getNumberOfSnapshots() {
        return levelSnapshots.size();
    }
}
