package GameView;
import GameController.GameThreads;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MenuFrame extends JFrame{

    static final int width = 800;
    static final int height = 600;
    public boolean started = false;
    GameThreads gameThreads;

    public MenuFrame(GameThreads gameThreads) {
        this.gameThreads = gameThreads;
        setName("Pac-Man Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setLocationRelativeTo(null); // Wyśrodkuj okno na ekranie

        JPanel firstPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("Resources/Graphics/Frames/pacman_opening.jpg");
                BufferedImage b = scaledImage(background);
                g.drawImage(b, 0, 0, null);
            }
        };
        this.setContentPane(firstPanel);
        this.setVisible(true);


        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("Resources/Graphics/Frames/pacman_menu.png");
                BufferedImage b = scaledImage(background);
                g.drawImage(b, 0, 0, null);
            }
        };

        JButton newGameButton = new JButton("New Game");
        JButton highScoresButton = new JButton("High Scores");
        JButton exitButton = new JButton("Exit");

        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        newGameButton.setFont(buttonFont);
        highScoresButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        Dimension buttonSize = new Dimension(200, 50);
        newGameButton.setPreferredSize(buttonSize);
        highScoresButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PreGameFrame(gameThreads);
            }
        });

        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoreFrame();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        buttonPanel.add(newGameButton);
        buttonPanel.add(highScoresButton);
        buttonPanel.add(exitButton);

        try {
            MusicBoard.playClip("pacman_beginning");
            Thread.sleep(5000);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        this.setContentPane(backgroundPanel);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();



    }

    static BufferedImage scaledImage(ImageIcon icon)
    {
        Image image = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bufferedImage;
    }
}
