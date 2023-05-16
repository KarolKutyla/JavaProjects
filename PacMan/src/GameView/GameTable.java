package GameView;

import GameController.GameBoard;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class GameTable extends JTable {

    private final static String GRAPHIC_PATH = "Resources/Graphics/Animations/";
    private final HashMap<String, ImageIcon> images = new HashMap<>();
    private final int squareSize;
    private GameFrame parent;
    private GameBoard gameBoard;

    AbstractTableModel tableModel;
    GameBoard.Field[][] board;
    DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value instanceof ImageIcon image) {
                ImageIcon icon = image;
                setIcon(icon);
                return new JLabel(icon);
            }
            return component;
        }
    };


    public GameTable(GameBoard.Field[][] board, int SQUARE_SIZE, GameFrame parent, GameBoard gameBoard)
    {
        this.gameBoard = gameBoard;
        this.parent = parent;
        this.squareSize = SQUARE_SIZE;
        this.board = board;
        JLabel label = new JLabel(new ImageIcon(GRAPHIC_PATH + "emptyField.png"));
        JScrollPane scrollPane = new JScrollPane(label);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableModel = new AbstractTableModel() {

            @Override
            public int getRowCount() {
                return board.length;
            }

            @Override
            public int getColumnCount() {
                return board[0].length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return images.get(board[rowIndex][columnIndex].getImage());
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
            @Override public Class<?> getColumnClass(int columnIndex)
            {
                    return BufferedImage.class;
            }
        };
        this.setModel(tableModel);
        for(int i = 0; i < tableModel.getColumnCount(); i++)
        {

            this.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
        }

        this.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                Dimension size = getSize();
                setRowHeight(squareSize);
                for (int i = 0; i < getColumnCount(); i++){
                    getColumnModel().getColumn(i).setMaxWidth(squareSize);
                }
            }
        });
        try {
            File dir = new File(GRAPHIC_PATH);
            for(File f : Objects.requireNonNull(dir.listFiles()))
            {
                images.put(f.getName().substring(0, f.getName().length()-4), new ImageIcon(ImageIO.read(f).getScaledInstance(squareSize, squareSize, Image.SCALE_DEFAULT)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK);
        inputMap.put(keyStroke, "quitGame");
        actionMap.put("quitGame", new AbstractAction() {
            @Override
            synchronized public void actionPerformed(ActionEvent e) {
                gameBoard.pacman.HP = 0;
                gameBoard.finishSequence();
                parent.dispose();
            }
        });
    }
}
