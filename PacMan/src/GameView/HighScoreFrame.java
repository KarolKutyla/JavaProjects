package GameView;

import GameController.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import GameController.FileManager.HighScoreRecord;

public class HighScoreFrame extends JFrame {

    public HighScoreFrame()
    {
        LinkedList<HighScoreRecord> scores = FileManager.readHighScores();
        setTitle("Wyniki gry");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(new GridLayout(10, 1));
        scoresPanel.setBackground(Color.BLACK);

        Font font = new Font("Arial", Font.BOLD, 14);
        Color textColor = Color.YELLOW;

        for (int i = 0; i < scores.size(); i++) {
            HighScoreRecord score = scores.get(i);
            String labelText = String.format("%d. %s - %d", i + 1, score.getPlayerName(), score.getScore());
            JLabel label = new JLabel(labelText);
            label.setFont(font);
            label.setForeground(textColor);
            scoresPanel.add(label);
        }

        mainPanel.add(scoresPanel, BorderLayout.CENTER);
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
