package com.ayman.jengen.level.snapshots;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is used to create the InputCareTaker which is a class that is used to store the input snapshots
 */
public class InputCareTaker {

    private final Queue<InputSnapshot> inputSnapshots = new LinkedList<>();
    private static final int MAX_SIZE = 1000;


    public void addSnapshot(InputSnapshot snapshot) {
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

    public void removeOldSnapshots(int i) {
        for (int j = 0; j < i; j++) {
            inputSnapshots.poll();
        }
    }
}
