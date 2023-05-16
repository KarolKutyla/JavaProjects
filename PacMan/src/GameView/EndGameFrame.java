package GameView;

import GameController.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGameFrame extends JFrame{
    private JLabel scoreLabel;
    private JTextField playerNameField;

    public EndGameFrame(int score) {
        setTitle("Wynik gracza");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        Font font = new Font("Courier New", Font.BOLD, 16);
        Color textColor = Color.YELLOW;

        scoreLabel = new JLabel("Twój wynik to: " + score);
        scoreLabel.setFont(font);
        scoreLabel.setForeground(textColor);
        mainPanel.add(scoreLabel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.BLACK);
        JLabel nameLabel = new JLabel("Nazwa gracza:");
        nameLabel.setFont(font);
        nameLabel.setForeground(textColor);
        inputPanel.add(nameLabel);

        playerNameField = new JTextField(10);
        playerNameField.setFont(font);
        playerNameField.setBackground(Color.DARK_GRAY);
        playerNameField.setForeground(textColor);
        inputPanel.add(playerNameField);

        JFrame frame = this;
        JButton okButton = new JButton("OK");
        okButton.setFont(font);
        okButton.setBackground(Color.DARK_GRAY);
        okButton.setForeground(textColor);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerNameField.getText();
                FileManager.addScore(playerName+";"+score);
                frame.dispose();
            }
        });
        inputPanel.add(okButton);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
