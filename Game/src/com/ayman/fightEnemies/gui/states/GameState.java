package com.ayman.fightEnemies.gui.states;

import com.ayman.fightEnemies.Game;
import com.ayman.fightEnemies.gui.AppFrame;

public class GameState extends GuiState {

    public GameState() {
    }

    @Override
    public void update(AppFrame frame) {
        frame.getContentPane().removeAll();
        frame.getContentPane().repaint();

        frame.add(new Game("Sasa"));

    }
}
