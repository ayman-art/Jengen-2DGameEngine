package com.ayman.fightEnemies.level.snapshots;

import com.ayman.fightEnemies.Input.Keyboard;
import com.ayman.fightEnemies.Input.Mouse;

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

