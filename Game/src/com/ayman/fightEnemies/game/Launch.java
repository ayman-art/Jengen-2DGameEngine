package com.ayman.fightEnemies.game;

import com.ayman.fightEnemies.game.contexts.*;
import com.ayman.fightEnemies.game.contexts.levelcontexts.ILevelContext;
import com.ayman.fightEnemies.game.contexts.levelcontexts.RandomLevelContext;
import com.ayman.fightEnemies.game.contexts.levelcontexts.SpawnLevelContext;

public class Launch {
    static GUIContext guiContext = new GUIContext.Builder()
            .setWidth(300)
            .setHeight(300 * 8 / 12)
            .setScaleFactor(3)
            .setMiniMapAlpha(100) // Optional (determines the transparency of the minimap)
            .setAboutText("This is A Fighting Game using most of the features provided by the Jengen Game Engine")
            .build();

    // Setting the AIContext
    static AIContext aiContext = new AIContext.Builder()
            .setType(AIContext.AIType.AStar) // (determines the type of the AI used in path finding)
            .build();

    // Setting the PlayerContext
    static PlayerContext playerContext = new PlayerContext.Builder()
            .setName("Ayman") // (determines the name of the player the game will be granted to)
            .setEffectTime(200) // (determines the time of effects)
            .setCoinValue(10) //  (determines the value of the coin)
            .setOriginalSpeed(1) // (determines the original speed of the player)
            .setChasingRange(100) //  (determines the chasing range)
            .setShootingRange(100) // (determines the shooting range of the player)
            .build();

    static ProjectileContext projectileContext = new ProjectileContext.Builder()
            .setSpeed(2)
            .setDamage(2)
            .setRange(1000) // (determines the range of the projectile which is the distance it can travel)
            .setFireInterval(10) // (determines the fire interval of the projectile which is the time between each fire)
            .build();

    static ILevelContext levelContext = new RandomLevelContext.Builder()
                .setSize(30, 17)
                .build();


//    static ILevelContext levelContext = new SpawnLevelContext.Builder()
//            .setNumberOfLevels(2)
//            .setPath("C:\\Users\\ayman\\Desktop\\FightLevels")
//            .setLevelExtension("png")
//            .build();
    static WinnigStateContext winningStateContext = new WinnigStateContext.Builder()
            .setType(WinnigStateContext.WinningStateType.CollectAllCoins) // (determines the type of the winning state)
            .build();


    static ScreenContext screenContext = new ScreenContext.Builder()
//                .addTile(0xff00ff00, true, true, new Sprite(16, 0, 0, MySpriteSheets.balls))
//                .addTile(0xff230920, false, false, new Sprite(16, 1, 0, MySpriteSheets.sun))
            .build();


    public static void main(String[] args) {


        Game game = new Game.Builder()
                .setGUIContext(guiContext)
                .setLevelContext(levelContext)
                .setPlayerContext(playerContext)
                .setScreenContext(screenContext)
                .setProjectileContext(projectileContext)
                .setAIContext(aiContext)
                .setWinningStateContext(winningStateContext)
                .build();

        ////////////////////////////////
        Jengen jengen = new Jengen(game);
        jengen.start();
        ////////////////////////////////
    }
}
