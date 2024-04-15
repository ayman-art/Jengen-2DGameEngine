package com.ayman.fightEnemies.gui.states;

import com.ayman.fightEnemies.gui.AppFrame;

import javax.swing.*;
import java.awt.*;

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

        JButton[] buttons = {playButton, settingsButton, aboutButton, exitButton};
        Color[] colors = {Color.GREEN, Color.BLUE, Color.MAGENTA, Color.RED};


        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setBounds(100, 100 + i * 100, 400, 100);
            buttons[i].setBackground(colors[i]);
            buttons[i].setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 40));
        }









    }

    @Override
    public void update(AppFrame frame) {

        frame.getContentPane().removeAll();
        frame.getContentPane().repaint();
        frame.add(playButton);
        frame.add(settingsButton);
        frame.add(aboutButton);
        frame.add(exitButton);

        JLabel label = new JLabel("Powered By");
        label.setBounds(640, 480, 400, 100);
        label.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 15));
        label.setForeground(Color.green);
        frame.add(label);
        JLabel label2 = new JLabel("Jengen");
        label2.setBounds(650, 500, 400, 100);
        label2.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 15));
        label2.setForeground(Color.RED);
        frame.add(label2);


        frame.getContentPane().setBackground(Color.YELLOW);

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
