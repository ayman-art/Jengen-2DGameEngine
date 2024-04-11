package com.ayman.fightEnemies.level.snapshots;

import java.util.Optional;

public record InputSnapshot(int[] keys, Optional<Double> mouseCoords) {


    public InputSnapshot(int[] keys) {
        this(keys, Optional.empty());
    }



}
