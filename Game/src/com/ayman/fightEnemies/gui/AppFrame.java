package com.ayman.fightEnemies.gui;

import com.ayman.fightEnemies.gui.states.GuiState;
import com.ayman.fightEnemies.gui.states.MainMenuState;

import javax.swing.*;

public class AppFrame extends JFrame {

    private GuiState currentState;


    public AppFrame() {
        super("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);

        setGuiState(new MainMenuState());
    }

    public void setGuiState(GuiState state) {

        this.currentState = state;
        state.update(this);

    }
}


class RunningApp {
    public static void main(String[] args) {
        new AppFrame();
    }
}
