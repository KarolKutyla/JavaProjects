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
    private static int WIDTH;
    private static int HEIGHT;
    private final static String GRAPHIC_PATH = "Resources/Graphics/Animations/";
    private static HashMap<String, BufferedImage> images = new HashMap<>();
    private JLabel scoreLabel;
    private ShowUpgradesPanel speedPanel;
    private ShowUpgradesPanel hPPanel;
    private ShowUpgradesPanel voidWalkPanel;
    private ShowUpgradesPanel teleportPanel;
    private ShowUpgradesPanel berserkPanel;
    static {
        try {
            File dir = new File(GRAPHIC_PATH);
            for(File f : dir.listFiles())
            {
                images.put(f.getName().substring(0, f.getName().length()-4), ImageIO.read(f));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        Toolkit toolkit = Toolkit.getDefaultToolkit();
            HEIGHT = toolkit.getScreenSize().height;
            WIDTH = toolkit.getScreenSize().width;
    }

    private int SQUARE_SIZE; // Rozmiar pojedynczego kwadratu w pikselach
    private GameTable gameTable;
    private GameBoard gameBoard;
    private Field[][] board;
    private Pawn.Direction direction = Pawn.Direction.none;

    public GameFrame(Field[][] board, GameBoard gameBoard) {
        this.board = board;
        this.gameBoard = gameBoard;
        SQUARE_SIZE = HEIGHT/board.length;
        gameTable = new GameTable(board, SQUARE_SIZE, this, gameBoard);
        gameTable.requestFocusInWindow();
        gameTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if(keyCode == KeyEvent.VK_LEFT)
                {
                    gameBoard.setDirection(Pawn.Direction.LEFT);
                }else if(keyCode == KeyEvent.VK_RIGHT)
                {
                    gameBoard.setDirection(Pawn.Direction.RIGHT);
                }
                else if(keyCode == KeyEvent.VK_UP)
                {
                    gameBoard.setDirection(Pawn.Direction.UP);;
                }
                else if(keyCode == KeyEvent.VK_DOWN)
                {
                    gameBoard.setDirection(Pawn.Direction.DOWN);
                }else if(keyCode == KeyEvent.VK_T)
                {
                    gameBoard.pacman.saveTeleportCoordinate();
                }else if(keyCode == KeyEvent.VK_SPACE)
                {
                    gameBoard.pacman.teleport();
                }
            }

        });
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        screenPanel.add(gameTable, gbc);
        gbc.gridx = 1;
        gbc.weightx = 100.0;
        gbc.weighty = 0;
        JPanel additionalPanel = new JPanel();
        additionalPanel.setLayout(new GridLayout(6,1));
        scoreLabel = new JLabel(gameBoard.getScore());
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        speedPanel = new ShowUpgradesPanel(10,gameBoard.pacman.speedValue,"speed_icon");
        hPPanel = new ShowUpgradesPanel(3,gameBoard.pacman.HP,"HP_icon");
        voidWalkPanel = new ShowUpgradesPanel(5,gameBoard.pacman.voidWalk,"voidWalk_icon");
        teleportPanel = new ShowUpgradesPanel(1,0,"teleport_icon");
        berserkPanel = new ShowUpgradesPanel(20,0,"berserk_icon");
        additionalPanel.add(scoreLabel);
        additionalPanel.add(speedPanel);
        additionalPanel.add(hPPanel);
        additionalPanel.add(voidWalkPanel);
        additionalPanel.setBackground(Color.YELLOW);
        screenPanel.add(additionalPanel, gbc);
        {

        }
        setContentPane(screenPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if(gd.isFullScreenSupported())
        {
            gd.setFullScreenWindow(this);
        } else
        {
            setSize(WIDTH, HEIGHT);
            setVisible(true);
        }

        new Thread(() -> {while (true) {
            refresh();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        }).start();
    }

    public void refresh() {
        scoreLabel.setText(gameBoard.getScore());
        speedPanel.checkGraphics(gameBoard.pacman.speedValue);
        hPPanel.checkGraphics(gameBoard.pacman.HP);
        voidWalkPanel.checkGraphics(gameBoard.pacman.voidWalk);
        if(gameBoard.pacman.teleport) {
            teleportPanel.checkGraphics(1);
        }
        else
        {
                teleportPanel.checkGraphics(0);
        }
        berserkPanel.checkGraphics(gameBoard.pacman.berserkCounter/30);
        gameTable.repaint();
    }

    public Pawn.Direction getDirection() {
        return direction;
    }
}

