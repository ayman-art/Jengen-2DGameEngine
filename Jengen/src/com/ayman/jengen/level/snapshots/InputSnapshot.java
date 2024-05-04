package com.ayman.jengen.level.snapshots;

/**
 *  This class is used to create the InputSnapshot which stores the mouse and keyboard snapshots.
 *  It is used along with Level Snapshots to allow the engine to run the game in the same way it was played.
 */
public class InputSnapshot {
    public MouseSnapshot mouseSnapshot;
    public KeyboardSnapshot keyboardSnapshot;

    public InputSnapshot(MouseSnapshot mouseSnapshot, KeyboardSnapshot keyboardSnapshot) {
        this.mouseSnapshot = mouseSnapshot;
        this.keyboardSnapshot = keyboardSnapshot;
    }
    public record MouseSnapshot(int x, int y, int button) {

    }

    public record KeyboardSnapshot(boolean up, boolean down, boolean left, boolean right, boolean s) {
    }


}

