package com.ayman.fightEnemies.game;

import com.ayman.fightEnemies.game.contexts.*;
import com.ayman.fightEnemies.game.contexts.levelcontexts.ILevelContext;

public class Game {

    private final GUIContext guiContext;
    private final ILevelContext levelContext;
    private final PlayerContext playerContext;
    private final ScreenContext screenContext;

    private final ProjectileContext projectileContext;
    private final AIContext aiContext;
    private final WinnigStateContext winnigStateContext;

    private Game(Builder builder) {
        this.guiContext = builder.GUIContext;
        this.levelContext = builder.levelContext;
        this.playerContext = builder.playerContext;
        this.screenContext = builder.screenContext;
        this.projectileContext = builder.projectileContext;
        this.aiContext = builder.aiContext;
        this.winnigStateContext = builder.winnigStateContext;
    }

    public PlayerContext getPlayerContext() {
        return playerContext;
    }

    public GUIContext getGuiContext() {
        return guiContext;
    }

    public ProjectileContext getProjectileContext() {
        return projectileContext;
    }
    public AIContext getAiContext() {
        return aiContext;
    }
    public ILevelContext getLevelContext() {
        return levelContext;
    }
    public ScreenContext getScreenContext() {
        return screenContext;
    }

    public WinnigStateContext getWinnigStateContext() {
        return winnigStateContext;
    }


    public static class Builder {
        private GUIContext GUIContext;
        private ILevelContext levelContext;
        private PlayerContext playerContext;
        private ScreenContext screenContext;
        private ProjectileContext projectileContext;
        private AIContext aiContext;
        private WinnigStateContext winnigStateContext

        public Builder setGUIContext(GUIContext GUIContext) {
            this.GUIContext = GUIContext;
            return this;
        }


        public Builder setLevelContext(ILevelContext levelContext) {
            this.levelContext = levelContext;
            return this;
        }

        public Builder setPlayerContext(PlayerContext playerContext) {
            this.playerContext = playerContext;
            return this;
        }

        public Builder setScreenContext(ScreenContext screenContext) {
            this.screenContext = screenContext;
            return this;
        }

        public Builder setProjectileContext(ProjectileContext projectileContext) {
            this.projectileContext = projectileContext;
            return this;
        }
        public Builder setAIContext(AIContext aiContext) {
            this.aiContext = aiContext;
            return this;
        }
        public Builder setWinningStateContext(WinnigStateContext winnigStateContext) {
            this.winnigStateContext = winnigStateContext;
            return this;
        }


        public Game build() {
            return new Game(this);
        }
    }
}
