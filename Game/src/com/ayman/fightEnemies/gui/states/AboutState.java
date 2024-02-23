package com.ayman.fightEnemies.gui.states;

import com.ayman.fightEnemies.gui.AppFrame;

import javax.swing.*;

public class AboutState extends GuiState{

    JLabel aboutLabel;

    JButton backButton;



    public AboutState() {
        aboutLabel = new JLabel("This is a game about fighting enemies");
        aboutLabel.setBounds(100, 100, 300, 100);

        backButton = new JButton("Back");
        backButton.setBounds(100, 200, 100, 100);

    }
    @Override
    public void update(AppFrame frame) {
        frame.getContentPane().removeAll();
        frame.getContentPane().repaint();

        frame.add(aboutLabel);
        frame.add(backButton);

        backButton.addActionListener(e -> {
            frame.setGuiState(new MainMenuState());
        });

    }
}
