package GameView;

import GameController.GameThreads;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class PreGameFrame extends JFrame {

    GameThreads gameThreads;

    public PreGameFrame(GameThreads gameThreads)
    {
        this.gameThreads = gameThreads;
        setName("settings");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        JLabel jLabel = new JLabel("Wybierz liczbę pól.");
        jLabel.setHorizontalAlignment(JLabel.CENTER);

        JSlider jSlider = new JSlider(10,100,40);
        jSlider.setMajorTickSpacing(10);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);
        JLabel valueLabel = new JLabel("Value " + jSlider.getValue());
        jSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                valueLabel.setText("Value: " + jSlider.getValue());
            }
        });
        //what's the name of the game;
        JButton jb = new JButton();
        jb.setText("OK");
        JFrame t = this;
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameThreads.launch(jSlider.getValue());
                t.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(4,1));
        buttonPanel.add(jLabel);
        buttonPanel.add(jSlider);
        buttonPanel.add(valueLabel);
        buttonPanel.add(jb);
        this.setContentPane(buttonPanel);
    }
}
