package com.ayman.fightEnemies.game.contexts;

import com.ayman.fightEnemies.Graphics.SpriteSheet;

import java.io.IOException;

public class MySpriteSheets {

    public static  SpriteSheet balls;
    public static SpriteSheet sun;

    static {
        try {
            balls = new SpriteSheet("/Sheets/balls.png", 16);
            sun = new SpriteSheet("/Sheets/sun.png", 16);
            // Add more sprite sheets here
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
