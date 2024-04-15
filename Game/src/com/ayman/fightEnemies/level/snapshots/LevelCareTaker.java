package com.ayman.fightEnemies.level.snapshots;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LevelCareTaker {
    private final List<LevelSnapshot> levelSnapshots = new ArrayList<>();

    public void addSnapshot(LevelSnapshot snapshot) {
        levelSnapshots.add(snapshot);
    }

    public LevelSnapshot getSnapshot(int index) {
        return levelSnapshots.get(index);
    }

    public int getNumberOfSnapshots() {
        return levelSnapshots.size();
    }

    public void reset() {
        levelSnapshots.clear();
    }

    public boolean hasNext() {
        return !levelSnapshots.isEmpty();
    }

    public LevelSnapshot getNextSnapshot() {
        LevelSnapshot ret = levelSnapshots.get(0);
        levelSnapshots.remove(0);
        return ret;

    }

    public LevelSnapshot getLastSnapshot() {
        return levelSnapshots.get(levelSnapshots.size() - 1);
    }

    public void removeOldSnapshot() {
        levelSnapshots.remove(0);
    }
}
