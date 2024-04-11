package com.ayman.fightEnemies.level.snapshots;

import java.util.Optional;

public record  InputSnapShot(int[] keys, Optional<Double> mouseCoords) {


    public InputSnapShot(int[] keys) {
        this(keys, Optional.empty());
    }



}
