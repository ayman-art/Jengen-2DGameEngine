package com.ayman.fightEnemies.level.snapshots;

import java.util.Optional;

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

