package com.ayman.fightEnemies.gui.states;

import com.ayman.fightEnemies.GameController;
import com.ayman.fightEnemies.gui.AppFrame;

import javax.swing.*;

/**
 * GameState is a class that represents the Game State of the game.
 */
public class GameState implements GuiState {

    public GameState() {
    }

    @Override
    public void update(AppFrame frame) {
        frame.getContentPane().removeAll();
        frame.getContentPane().repaint();


        GameController Game = new GameController(GameController.playerName, frame);

    }
}

class Main {
    public static void main(String[]s ) {
        JFrame frame = new JFrame();
        new GameController("P", frame);
    }
}
