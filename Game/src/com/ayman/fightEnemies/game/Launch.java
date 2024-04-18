package com.ayman.fightEnemies.game;

import com.ayman.fightEnemies.game.contexts.*;
import com.ayman.fightEnemies.game.contexts.levelcontexts.ILevelContext;
import com.ayman.fightEnemies.game.contexts.levelcontexts.RandomLevelContext;
import com.ayman.fightEnemies.game.contexts.levelcontexts.SpawnLevelContext;

public class Launch {
    public static void main(String[] args) {

        // Setting the GUIContext
        GUIContext guiContext = new GUIContext.Builder()
                .setWidth(300)
                .setHeight(300 * 8 / 12)
                .setScaleFactor(3) // Optional (determines the scale factor of the GUI)
                .setMiniMapAlpha(100) // Optional (determines the transparency of the minimap)
                .build();

        // Setting the AIContext
        AIContext aiContext = new AIContext.Builder()
                .setType(AIContext.AIType.AStar)
                .build();

        // Setting the PlayerContext
        PlayerContext playerContext = new PlayerContext.Builder()
                .setName("Ayman")
                .setEffectTime(200) // Optional (determines the time of effects)
                .setCoinValue(10) // Optional (determines the value of the coin)
                .setOriginalSpeed(1) // Optional (determines the original speed of the player)
                .setChasingRange(100) // Optional (determines the chasing range of the player)
                .setShootingRange(100) // Optional (determines the shooting range of the player)
                .build();

        ProjectileContext projectileContext = new ProjectileContext.Builder()
                .setSpeed(2)
                .setDamage(20)
                .setRange(1000)
                .setFireInterval(10)
                .build();

//        ILevelContext LevelContext = new RandomLevelContext.Builder()
//                .setSize(20, 20)
//                .build();


        ILevelContext LevelContext = new SpawnLevelContext.Builder()
                .setNumberOfLevels(2)
                .setPath("C:\\Users\\ayman\\Desktop\\FightLevels")
                .build();
        WinnigStateContext winningStateContext = new WinnigStateContext.Builder()
                .setType(WinnigStateContext.WinningStateType.CollectAllCoins)
                .build();


        Game game = new Game.Builder()
                .setGUIContext(guiContext)
                .setLevelContext(LevelContext)
                .setPlayerContext(playerContext)
                .setScreenContext(new ScreenContext())
                .setProjectileContext(projectileContext)
                .setAIContext(aiContext)
                .setWinningStateContext(winningStateContext)
                .build();

        
        
        Jengen jengen = new Jengen(game);
        jengen.start();
    }
}
