package com.ayman.fightEnemies.gui.states;

import com.ayman.fightEnemies.gui.AppFrame;

import javax.swing.*;

public class SettingsState extends GuiState {

    JLabel settingsLabel;
    JButton backButton;

    public SettingsState() {
        settingsLabel = new JLabel("Settings");
        backButton = new JButton("Back");

        settingsLabel.setBounds(100, 100, 100, 100);
        backButton.setBounds(100, 200, 100, 100);
    }

    @Override
    public void update(AppFrame frame) {
        frame.getContentPane().removeAll();

        frame.add(settingsLabel);
        frame.add(backButton);

        frame.repaint();
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.setGuiState(new MainMenuState());
        });
    }
}
