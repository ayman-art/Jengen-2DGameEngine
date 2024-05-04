package com.ayman.jengen.gui;

import com.ayman.jengen.GameController;
import com.ayman.jengen.gui.states.GuiState;
import com.ayman.jengen.gui.states.MainMenuState;

import javax.swing.*;

public class AppFrame extends JFrame {

    private static AppFrame instance; // Singleton instance
    private GuiState currentState;

    // Private constructor to prevent external instantiation
    public AppFrame() {
        super("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(GameController.width * GameController.scaleFactor, GameController.height * GameController.scaleFactor));
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);

        setGuiState(new MainMenuState());
    }

    // Method to get the singleton instance
    public static synchronized AppFrame getInstance() {
        if (instance == null) {
            instance = new AppFrame();
        }
        return instance;
    }

    public void setGuiState(GuiState state) {
        this.currentState = state;
        state.update(this);
    }
}

class RunningApp {
    public static void main(String[] args) {
        // Use the singleton instance
        AppFrame.getInstance();
    }
}
