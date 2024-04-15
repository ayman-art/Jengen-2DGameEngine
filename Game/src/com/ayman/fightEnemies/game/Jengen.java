package com.ayman.fightEnemies.game;

import com.ayman.fightEnemies.GameController;
import com.ayman.fightEnemies.entity.mob.Mob;
import com.ayman.fightEnemies.entity.projectile.WizardProjectile;
import com.ayman.fightEnemies.game.contexts.*;
import com.ayman.fightEnemies.game.contexts.levelcontexts.ILevelContext;
import com.ayman.fightEnemies.game.contexts.levelcontexts.RandomLevelContext;
import com.ayman.fightEnemies.game.contexts.levelcontexts.SpawnLevelContext;
import com.ayman.fightEnemies.gui.AppFrame;
import com.ayman.fightEnemies.level.RandomLevel;
import com.ayman.fightEnemies.level.effects.CoinEffect;
import com.ayman.fightEnemies.level.effects.Effect;

public class Jengen {

    private Game game;

    public Jengen(Game game){
        this.game = game;
    }

    public void start(){
        processGUI(game.getGuiContext());
        processPlayer(game.getPlayerContext());
        processProjectiles(game.getProjectileContext());
        processAI(game.getAiContext());
        processLevel(game.getLevelContext());



        AppFrame.getInstance();
    }

    private void processAI(AIContext aiContext) {
        GameController.aiType = aiContext.getType();
    }

    private void processGUI(GUIContext guiContext){
        GameController.width = guiContext.getWidth();
        GameController.height = guiContext.getHeight();
        GameController.scaleFactor = guiContext.getScaleFactor();
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
        if(levelContext instanceof RandomLevelContext){
            processRandomLevelContext((RandomLevelContext) levelContext);
        }else if(levelContext instanceof SpawnLevelContext){
            processSpawnLevelContext((SpawnLevelContext) levelContext);
        }

    }
    private void processRandomLevelContext(RandomLevelContext levelContext){
        RandomLevel.WIDTH = levelContext.getWidth();
        RandomLevel.HEIGHT = levelContext.getHeight();
    }
    private void processSpawnLevelContext(SpawnLevelContext levelContext){


    }

}
