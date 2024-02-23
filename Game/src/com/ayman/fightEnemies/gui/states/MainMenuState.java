package com.ayman.fightEnemies.gui.states;

import com.ayman.fightEnemies.gui.AppFrame;

import javax.swing.*;

public class MainMenuState extends GuiState {

    JButton playButton;
    JButton settingsButton;
    JButton aboutButton;
    JButton exitButton;


    public MainMenuState() {
        playButton = new JButton("Play");
        settingsButton = new JButton("Settings");
        aboutButton = new JButton("About");
        exitButton = new JButton("Exit");

        playButton.setBounds(100, 100, 100, 100);
        settingsButton.setBounds(100, 200, 100, 100);
        aboutButton.setBounds(100, 300, 100, 100);
        exitButton.setBounds(100, 400, 100, 100);






    }

    @Override
    public void update(AppFrame frame) {

        frame.getContentPane().removeAll();
        frame.getContentPane().repaint();
        frame.add(playButton);
        frame.add(settingsButton);
        frame.add(aboutButton);
        frame.add(exitButton);


        playButton.addActionListener(e -> {
            frame.setGuiState(new GameState());

        });

        settingsButton.addActionListener(e -> {
            frame.setGuiState(new SettingsState());
        });

        aboutButton.addActionListener(e -> {
            frame.setGuiState(new AboutState());
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

    }
}
