package com.ayman.jengen.gameCreation;

import com.ayman.jengen.GameController;
import com.ayman.jengen.Graphics.Screen;
import com.ayman.jengen.entity.mob.Mob;
import com.ayman.jengen.entity.projectile.WizardProjectile;
import com.ayman.jengen.gameCreation.contexts.*;
import com.ayman.jengen.gameCreation.contexts.levelcontexts.ILevelContext;
import com.ayman.jengen.gameCreation.contexts.levelcontexts.RandomLevelContext;
import com.ayman.jengen.gameCreation.contexts.levelcontexts.SpawnLevelContext;
import com.ayman.jengen.gui.AppFrame;
import com.ayman.jengen.gui.states.AboutState;
import com.ayman.jengen.level.Level;
import com.ayman.jengen.level.RandomLevel;
import com.ayman.jengen.level.SpawnLevel;
import com.ayman.jengen.level.effects.CoinEffect;
import com.ayman.jengen.level.effects.Effect;
import com.ayman.jengen.level.tile.Tile;
import com.ayman.jengen.level.winning.ItemsCollected;
import com.ayman.jengen.level.winning.MobsKilled;
import com.ayman.jengen.level.winning.TargetReached;

public class Jengen {

    private final GameContext gameContext;

    public Jengen(GameContext gameContext){
        this.gameContext = gameContext;
    }

    public void start(){
        processGUI(gameContext.getGuiContext());
        processPlayer(gameContext.getPlayerContext());
        processProjectiles(gameContext.getProjectileContext());
        processAI(gameContext.getAiContext());
        processLevel(gameContext.getLevelContext());
        processWinningState(gameContext.getWinnigStateContext());
        processScreenContext(gameContext.getScreenContext());

        AppFrame.getInstance();
    }

    private void processScreenContext(ScreenContext screenContext) {
        Tile.costumedTiles = screenContext.getCostumedTiles();
    }

    private void processAI(AIContext aiContext) {
        GameController.aiType = aiContext.getType();
    }

    private void processGUI(GUIContext guiContext){
        GameController.width = guiContext.getWidth();
        GameController.height = guiContext.getHeight();
        GameController.scaleFactor = guiContext.getScaleFactor();
        AboutState.aboutText = guiContext.getAboutText();
        Screen.MiniMapAlpha = guiContext.getMiniMapAlpha();
    }

    private void processPlayer(PlayerContext playerContext){
        GameController.playerName = playerContext.getName();
        Mob.CHASING_RANGE = playerContext.getChasingRange();
        Mob.SHOOTING_RANGE = playerContext.getShootingRange();
        CoinEffect.COIN_VALUE = playerContext.getCoinValue();
        Effect.EFFECT_TIME = playerContext.getEffectTime();
        Mob.ORIGINAL_SPEED = playerContext.getOriginalSpeed();
    }

    private void processProjectiles(ProjectileContext projectileContext){
        WizardProjectile.DAMAGE = projectileContext.getDamage();
        WizardProjectile.SPEED = projectileContext.getSpeed();
        WizardProjectile.FIRE_INTERVAL = projectileContext.getFireInterval();
        WizardProjectile.RANGE = projectileContext.getRange();
    }
    private void processLevel(ILevelContext levelContext){
        if(levelContext instanceof RandomLevelContext randomLevelContext){
            processRandomLevelContext(randomLevelContext);
        }else if(levelContext instanceof SpawnLevelContext spawnLevelContext){
            processSpawnLevelContext(spawnLevelContext);
        }

    }
    private void processRandomLevelContext(RandomLevelContext levelContext){
        RandomLevel.WIDTH = levelContext.getWidth();
        RandomLevel.HEIGHT = levelContext.getHeight();
    }
    private void processSpawnLevelContext(SpawnLevelContext levelContext){
        SpawnLevel.numberOfLevels = levelContext.getNumberOfLevels();
        SpawnLevel.levelsLocation = levelContext.getPath();

    }

    private void processWinningState(WinnigStateContext winnigStateContext){
        switch(winnigStateContext.getType()){
            case CollectAllCoins -> {
                Level.winningState = new ItemsCollected();
            }
            case KillAllEnemies -> {
                Level.winningState = new MobsKilled();
            }
            case ReachEnd -> {
                Level.winningState = new TargetReached();
            }

        }
    }



}
