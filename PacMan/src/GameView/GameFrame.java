package GameView;
import GameController.GameBoard;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import GameController.GameBoard.Field;
import GameController.Pawn;

public class GameFrame extends JFrame {
    private HashMap<String, BufferedImage> images = new HashMap<>();
    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;
    private final int SQUARE_SIZE = 10; // Rozmiar pojedynczego kwadratu w pikselach

    private final String GRAPHIC_PATH = "Resources/Graphics/";

    private JPanel panel;
    private GameBoard gameBoard;
    private Field[][] board;

    public GameFrame(Field[][] board, GameBoard gameBoard) {
        this.board = board;
        this.gameBoard = gameBoard;

        try {
//            new Thread(()->SwingUtilities.invokeLater(new Runnable() {
//                public void run() {
//                    while (true) {
//                        repaint();
//                    }
//                }
//            })).start();

            File dir = new File(GRAPHIC_PATH);
            for(File f : dir.listFiles())
            {
                images.put(f.getName().substring(0, f.getName().length()-4), ImageIO.read(f));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, WIDTH, HEIGHT);

                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        int x = j * SQUARE_SIZE;
                        int y = i * SQUARE_SIZE;

                        g.drawImage(images.get(board[i][j].getImage()), x, y, SQUARE_SIZE, SQUARE_SIZE, null);
//                        if (board[i][j] == 0) {
//                            g.drawImage(emptySquareImg, x, y, SQUARE_SIZE, SQUARE_SIZE, null);
//                        } else {
//                            g.drawImage(filledSquareImg, x, y, SQUARE_SIZE, SQUARE_SIZE, null);
//                        }
                    }
                }
            }
        };

        this.requestFocusInWindow();
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("się dzieje");
                int keyCode = e.getKeyCode();
                if(keyCode == KeyEvent.VK_LEFT)
                {
                    gameBoard.movePawn(Pawn.Direction.LEFT);
                }else if(keyCode == KeyEvent.VK_RIGHT)
                {
                    gameBoard.movePawn(Pawn.Direction.RIGHT);
                }
                else if(keyCode == KeyEvent.VK_UP)
                {
                    gameBoard.movePawn(Pawn.Direction.UP);
                }
                else if(keyCode == KeyEvent.VK_DOWN)
                {
                    gameBoard.movePawn(Pawn.Direction.DOWN);
                }
            }
        });

        setContentPane(panel);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        new Thread(() -> {while (true) {
            refresh();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        }).start();
    }

    public void refresh() {
        panel.repaint();
    }
}

