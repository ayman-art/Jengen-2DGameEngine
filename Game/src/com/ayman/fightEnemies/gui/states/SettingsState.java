package com.ayman.fightEnemies.gui.states;

import com.ayman.fightEnemies.gui.AppFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsState extends GuiState {

    JLabel settingsLabel;
    JButton backButton;
    JRadioButton easyRadioButton;
    JRadioButton mediumRadioButton;
    JRadioButton hardRadioButton;
    ButtonGroup difficultyGroup;

    public SettingsState() {
        settingsLabel = new JLabel("Settings");
        backButton = new JButton("Back");

        // Create radio buttons for difficulty levels
        easyRadioButton = new JRadioButton("Easy");
        mediumRadioButton = new JRadioButton("Medium");
        hardRadioButton = new JRadioButton("Hard");

        // Create a button group for radio buttons
        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyRadioButton);
        difficultyGroup.add(mediumRadioButton);
        difficultyGroup.add(hardRadioButton);

        // Set default selection
        mediumRadioButton.setSelected(true);

        // Set font size for radio buttons
        Font radioButtonFont = new Font("Arial", Font.PLAIN, 24); // Larger font size
        easyRadioButton.setFont(radioButtonFont);
        mediumRadioButton.setFont(radioButtonFont);
        hardRadioButton.setFont(radioButtonFont);

        // Set bounds for components
        settingsLabel.setBounds(100, 100, 100, 100);
        backButton.setBounds(100, 250, 400, 100);
        easyRadioButton.setBounds(100, 200, 200, 50); // Adjusted size for readability
        mediumRadioButton.setBounds(300, 200, 200, 50); // Adjusted size for readability
        hardRadioButton.setBounds(500, 200, 200, 50); // Adjusted size for readability

        // Add action listeners to radio buttons
        easyRadioButton.addActionListener(new RadioButtonListener());
        mediumRadioButton.addActionListener(new RadioButtonListener());
        hardRadioButton.addActionListener(new RadioButtonListener());
    }

    @Override
    public void update(AppFrame frame) {
        frame.getContentPane().removeAll();

        frame.add(settingsLabel);
        frame.add(easyRadioButton);
        frame.add(mediumRadioButton);
        frame.add(hardRadioButton);
        frame.add(backButton);

        frame.repaint();
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.setGuiState(new MainMenuState());
        });
    }

    // ActionListener for radio buttons
    private class RadioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset font and color for all radio buttons
            Font defaultFont = new Font("Arial", Font.PLAIN, 24); // Larger font size
            Color defaultColor = UIManager.getColor("RadioButton.foreground");
            easyRadioButton.setFont(defaultFont);
            easyRadioButton.setForeground(defaultColor);
            mediumRadioButton.setFont(defaultFont);
            mediumRadioButton.setForeground(defaultColor);
            hardRadioButton.setFont(defaultFont);
            hardRadioButton.setForeground(defaultColor);

            // Set font and color for the selected radio button
            Font selectedFont = new Font("Arial", Font.BOLD, 24); // Larger font size
            Color selectedColor = Color.GREEN;
            JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
            selectedRadioButton.setFont(selectedFont);
            selectedRadioButton.setForeground(selectedColor);
        }
    }
}
