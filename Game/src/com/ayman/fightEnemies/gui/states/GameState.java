package com.ayman.fightEnemies.gui.states;

import com.ayman.fightEnemies.GameController;
import com.ayman.fightEnemies.gui.AppFrame;

import javax.swing.*;

public class GameState extends GuiState {

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

//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(new java.awt.Dimension(800, 600));
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
////        frame.setLayout(null);
//        frame.getContentPane().removeAll();
//        frame.getContentPane().repaint();
//
//        frame.setSize(800,600);
        new GameController("P", frame);
    }
}
