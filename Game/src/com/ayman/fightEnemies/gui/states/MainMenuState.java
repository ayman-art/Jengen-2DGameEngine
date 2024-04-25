package com.ayman.fightEnemies.gui.states;

import com.ayman.fightEnemies.gui.AppFrame;

import javax.swing.*;
import java.awt.*;

public class MainMenuState extends GuiState {

    JButton playButton;
    JButton settingsButton;
    JButton aboutButton;
    JButton exitButton;

    JLabel logo;


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

        logo = new JLabel();
        logo.setBounds(540, 200, 320, 245);
//        logo.setSize(200, 150);
        logo.setBackground(Color.WHITE);
        logo.setOpaque(true);


        logo.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/logos/Jengen.png")).getImage().getScaledInstance(320, 245, Image.SCALE_DEFAULT)));








    }

    @Override
    public void update(AppFrame frame) {

        frame.getContentPane().removeAll();
        frame.getContentPane().repaint();
        frame.add(playButton);
        frame.add(settingsButton);
        frame.add(aboutButton);
        frame.add(exitButton);
        frame.add(logo);

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

        //Setting color to Dark Purple
        frame.getContentPane().setBackground(new Color(75, 0, 130));

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
