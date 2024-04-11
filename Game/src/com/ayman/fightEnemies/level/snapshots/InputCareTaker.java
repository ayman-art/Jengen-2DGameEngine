package com.ayman.fightEnemies.level.snapshots;

import java.util.LinkedList;
import java.util.Queue;

public class InputCareTaker {

    private final Queue<InputSnapshot> inputSnapshots = new LinkedList<>();
    private static int MAX_SIZE = 1000;


    public void addSnapshot(InputSnapshot snapshot) {
        if (inputSnapshots.size() >= MAX_SIZE) {
            inputSnapshots.poll();
        }
        inputSnapshots.add(snapshot);
    }

    public InputSnapshot getNextSnapshot() {
        return inputSnapshots.poll();
    }

    public boolean hasNext() {
        return !inputSnapshots.isEmpty();
    }

    public void reset() {
        inputSnapshots.clear();
    }

    public int getNumberOfSnapshots() {
        return inputSnapshots.size();
    }

}
