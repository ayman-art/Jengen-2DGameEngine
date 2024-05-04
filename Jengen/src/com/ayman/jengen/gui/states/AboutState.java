package com.ayman.jengen.gui.states;

import com.ayman.jengen.gui.AppFrame;

import javax.swing.*;
import java.awt.*;

/**
 * AboutState is a class that represents the About State of the game.

 */
public class AboutState implements GuiState{

    JLabel aboutLabel;

    JButton backButton;

    public static String aboutText = "This is a game about fighting enemies";



    public AboutState() {
        aboutLabel = new JLabel("<html>" + aboutText + "</html>"); // "<html>" + aboutText + "</html>" allows for multi-line text
        aboutLabel.setBounds(100, 100, 300, 150);
        aboutLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
        aboutLabel.setForeground(Color.GREEN);

        backButton = new JButton("Back");
        backButton.setBounds(100, 350, 400, 100);
        backButton.setBackground(Color.PINK);
        backButton.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 15));
    }
    @Override
    public void update(AppFrame frame) {
        frame.getContentPane().removeAll();
        frame.getContentPane().repaint();

        frame.add(aboutLabel);
        frame.add(backButton);

        backButton.addActionListener(e -> frame.setGuiState(new MainMenuState()));

    }
}
