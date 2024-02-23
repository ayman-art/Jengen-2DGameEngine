package com.ayman.fightEnemies;


import javax.swing.*;
import java.util.concurrent.atomic.AtomicReference;


public class MainMenu {

    JFrame frame;

    JPanel mainMenuPanel;
    JPanel gamePanel;
    JPanel settingsPanel;
    JPanel aboutPanel;


    enum State {
        MAIN_MENU,
        GAME,
        SETTINGS,
        ABOUT
    }

    State state;

    public MainMenu() {
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        AtomicReference<State> state = new AtomicReference<>(State.MAIN_MENU);

        mainMenuPanel = new JPanel();
        gamePanel = new JPanel();
        settingsPanel = new JPanel();
        aboutPanel = new JPanel();

        mainMenuPanel.setBounds(0, 0, 500, 500);
        gamePanel.setBounds(0, 0, 500, 500);
        settingsPanel.setBounds(0, 0, 500, 500);
        aboutPanel.setBounds(0, 0, 500, 500);

//        mainMenuPanel.setOpaque(true);
//        gamePanel.setOpaque(true);
//        settingsPanel.setOpaque(true);
//        aboutPanel.setOpaque(true);


        JButton playButton = new JButton("Play");
        playButton.setBounds(100, 100, 100, 100);
        playButton.addActionListener(e -> {
            state.set(State.GAME);
            handleState(frame, state.get());
        });

        JButton settingsButton = new JButton("Settings");
        settingsButton.setBounds(100, 200, 100, 100);
        settingsButton.addActionListener(e -> {
            state.set(State.SETTINGS);
            handleState(frame, state.get());
        });

        JButton aboutButton = new JButton("About");
        aboutButton.setBounds(100, 300, 100, 100);
        aboutButton.addActionListener(e -> {
            state.set(State.ABOUT);
            handleState(frame, state.get());
        });


        mainMenuPanel.add(playButton);
        mainMenuPanel.add(settingsButton);
        mainMenuPanel.add(aboutButton);


        frame.add(mainMenuPanel);


    }

    private void handleState(JFrame frame, State state) {

        switch (state) {
            case MAIN_MENU -> {
                frame.add(mainMenuPanel);
                frame.remove(gamePanel);
                frame.remove(settingsPanel);
                frame.remove(aboutPanel);
            }
            case GAME -> {
                frame.add(gamePanel);
                frame.remove(mainMenuPanel);
                frame.remove(settingsPanel);
                frame.remove(aboutPanel);
            }
            case SETTINGS -> {
                frame.add(settingsPanel);
                frame.remove(mainMenuPanel);
                frame.remove(gamePanel);
                frame.remove(aboutPanel);
            }
            case ABOUT -> {
                frame.add(aboutPanel);
                frame.remove(mainMenuPanel);
                frame.remove(gamePanel);
                frame.remove(settingsPanel);
            }
        }
        frame.repaint();

    }
    public static void main(String[] args) {
        new MainMenu();
    }
}
