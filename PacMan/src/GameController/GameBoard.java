package GameController;

import GameView.GameFrame;
import GameView.MusicBoard;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class GameBoard {
    final int maxX;
    final int maxY;
    final Field[][] board;
    public Pacman pacman;
    int collectedSnacks = 0;
    private int allSnacks = 0;
    Pawn.Direction direction = Pawn.Direction.none;
    public boolean finished = false;
    public final LinkedList<Ghost> ghosts = new LinkedList<>();
    int score = 0;

    public GameBoard(int size)
    {
        if(size <= 10)
        {
            maxX = 10;
            maxY = 10;
        }else if(size <= 100)
        {
            maxX = size;
            maxY = size;
        }else
        {
            maxX = 100;
            maxY = 100;
        }
        board = new Field[maxY][maxX];
        generate();
        for (Field[] fields : board) {
            for (int j = 0; j < board.length; j++) {
                if (fields[j].containsSnack)
                    allSnacks += 1;
            }
        }
    }

    boolean putPawn()
    {

        return true;
    }

    private void generate()
    {
        for (int i = 0; i < maxY; i++)
        {
            for (int j = 0; j < maxX; j++)
            {
                board[i][j] = new Field(true);
            }
        }
        for (int i = 0; i < maxY/10; i++)
        {
            int position = (int)(Math.random()*8)+1;
            for(int j = 0; j < maxX; j++)
            {
                board[j][position+i*10] = new Field(false);

            }
            position = (int)(Math.random()*8)+1;
            for(int j = 0; j < maxX; j++)
            {
                board[position+i*10][j] = new Field(false);

            }
        }
        for(int i = 0; i < maxY/5; i++)
        {
            int position = (int)(Math.random()*3)+1;
            for(int j = (int)((double)maxX*(1/4d)); j < (int)((double)maxX*(3/4d)); j++)
            {
                board[j][position+i*5] = new Field(false);
            }
            position = (int)(Math.random()*3)+1;
            for(int j = (int)((double)maxX*(1/4d)); j < (int)((double)maxX*(3/4d)); j++)
            {
                board[position+i*5][j] = new Field(false);
            }
        }
        for(int i = 0; i < maxY/5; i++)
        {
            int position = (int)(Math.random()*3)+1;
            for(int j = 0; j < maxX/4+1; j++)
            {
                board[j][position+i*5] = new Field(false);
                board[maxX-j-1][position+i*5] = new Field(false);
            }
            position = (int)(Math.random()*3)+1;
            for(int j = 0; j < maxX/4+1; j++)
            {
                board[position+i*5][j] = new Field(false);
                board[position+i*5][maxX - j-1] = new Field(false);
            }
        }
        loop:
        for(int i = maxY/2; i < maxY; i++)
        {
            for(int j = maxX/2; j < maxX; j++)
            {
                if(!board[i][j].isWall)
                {
                    pacman = new Pacman(this, j, i);
//                    board[i][j].pawns.add(pacman);
                    break loop;
                }
            }
        }
        addGhost("red");
        addGhost("green");
        addGhost("pink");
        addGhost("purple");
    }

    private void addGhost(String name) {
        int x = 1;
        int y = 1;
        loop:
        for (int i = maxY / 4 * y; i < maxY / 4 + maxY / 4 * y; i++) {
            for (int j = maxX / 4 * x; j < maxX / 4 + maxY / 4 * x; j++) {
                if (!board[i][j].isWall) {
                    Ghost g = switch (name) {
                        case "red" -> new RedGhost(this, j, i);
                        case "green" -> new GreenGhost(this, j, i);
                        case "pink" -> new PinkGhost(this, j, i);
                        case "purple" -> new PurpleGhost(this, j, i);
                        default -> null;
                    };
                    ghosts.add(g);
                    break loop;
                }
            }
        }
    }

    private void addGhost(String name, int x, int y) {
        loop:
        for (int i = maxY / 4 * y; i < maxY / 4 + maxY / 4 * y; i++) {
            for (int j = maxX / 4 * x; j < maxX / 4 + maxY / 4 * x; j++) {
                if (!board[i][j].isWall) {
                    Ghost g = switch (name) {
                        case "red" -> new RedGhost(this, j, i);
                        case "green" -> new GreenGhost(this, j, i);
                        case "pink" -> new PinkGhost(this, j, i);
                        case "purple" -> new PurpleGhost(this, j, i);
                        default -> null;
                    };
                    ghosts.add(g);
                    break loop;
                }
            }
        }
    }

    public void again()
    {
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
            {
                board[i][j].refreshForNextRound();
            }
        }
        loop:
        for(int i = maxY/2; i < maxY; i++)
        {
            for(int j = maxX/2; j < maxX; j++)
            {
                if(!board[i][j].isWall)
                {
                    pacman.xPosition = i;
                    pacman.yPosition = j;
//                    board[i][j].pawns.add(pacman);
                    break loop;
                }
            }
        }
        addGhost("red");
        addGhost("green");
        addGhost("pink");
        addGhost("purple");
        finished = false;
    }

    public void spawnGhost(String ghostName)
    {
        int x = 1, y;
        if(pacman.yPosition <= board.length)
        {
            y = 3;
        }else
        {
            y = 1;
        }
        addGhost(ghostName, x, y);
    }

    public synchronized void relocateGhost(Ghost g)
    {
        ghosts.indexOf(g);
        int y;
        if(pacman.yPosition <= board.length)
        {
            y = 3;
        }else
        {
            y = 1;
        }
        board[g.yPosition][g.xPosition].remove(g);
        score += 100;
        MusicBoard.playClip("pacman_eatghost");
        loop:
        for (int i = maxY / 4 * y; i < maxY / 4 + maxY / 4 * y; i++) {
            for (int j = maxX / 4; j < maxX / 4 + maxY / 4; j++) {
                if (!board[i][j].isWall) {
                    board[i][j].add(g);
                    g.xPosition = j;
                    g.yPosition = i;
                    g.vulnerable = false;
                    break loop;
                }
            }
        }

    }


    public synchronized void finishSequence()
    {
        pacman.HP--;
        if(pacman.HP <= 0 || collectedSnacks == allSnacks)
        {
            finished = true;
            pacman.thread.interrupt();
            for(Ghost g : ghosts)
            {
                g.thread.interrupt();
            }
        }else
        {
            for(Ghost g : ghosts)
            {
                g.thread.interrupt();
            }
            again();
        }
    }

    public int getHeight()
    {
        return board.length;
    }

    public int getWidth()
    {
        return board[0].length;
    }

    public class Field
    {
        boolean isWall;
        boolean containsSnack = true;
        Upgrade upgrade = null;
        LinkedList<Pawn> pawns = new LinkedList<>();


        private Field(boolean isWall)
        {
            this.isWall = isWall;
            containsSnack = !isWall;
        }

        synchronized public String getImage()
        {
            if(pawns.size() == 0) {
                    if (upgrade == null) {
                        if (!containsSnack) {
                            if(isWall)
                            {
                                return "wallField";
                            }return "emptyField";
                        }
                        return "snackField";
                    }
                    return "upgradeField";

            }else
            {
                try {
                    return pawns.get(0).nextAnimation();
                }catch (NullPointerException n)
                {
                    return "emptyField";
                }

            }
        }
        private void refreshForNextRound() {
            if (this.upgrade != null) {
                this.upgrade = null;
            }
            if (this.pawns.size() > 0) {
                this.pawns = new LinkedList<>();
            }
        }

        synchronized void remove(Pawn p)
        {
            pawns.remove(p);
        }
        synchronized void add(Pawn p)
        {
            pawns.add(p);
            if(this.playerLost())
            {
                MusicBoard.playClip("pacman_death");
                finishSequence();
            }
            if(p.name.equals("pacman")) {
                if (containsSnack) {
                    collectedSnacks++;
                    score++;
                    containsSnack = false;
                    if(collectedSnacks == allSnacks) {
                        System.out.println("Zebrano wszystkie snacki!");
                        MusicBoard.playClip("pacman_intermission");
                        finishSequence();
                    }
                }
                if(upgrade != null)
                {
                    pacman.addUpgrade(upgrade.name());
                    upgrade = null;
                }
            }
        }
        synchronized boolean playerLost()
        {
            if(pawns.contains(pacman) && pawns.size() >= 2)
            {
                boolean val = false;
                Ghost[] arr = new Ghost[4];
                int i = 0;
                for(Pawn p : pawns)
                {

                    if(p instanceof Ghost)
                    {
                        Ghost g = (Ghost)p;
                        if (!g.vulnerable)
                        {
                            return true;
                        }else
                        {
                            arr[i++] = g;
                        }
                    }
                }
                for(int j = 0; j < i; j++)
                {
                    relocateGhost(arr[j]);
                }
                return false;
            }else
            {
                return false;
            }
        }

        enum Upgrade
        {
            SPEED, VOID_WALK, BERSERK, HP, TELEPORT;

            public static Upgrade generate()
            {
                int number = (int)(Math.random() * 20);
                return switch (number) {
                    case 0 -> SPEED;
                    case 1 -> VOID_WALK;
                    case 2 -> BERSERK;
                    case 3 -> HP;
                    case 4 -> TELEPORT;
                    default -> null;
                };
            }
        }
    }

//    public static void xD()
//    {
//        JTable j = new JTable();
//        TableModel tm = new DefaultTableModel(13,13);
//        tm.setValueAt();
//        j.setModel();
//    }




    public void setDirection(Pawn.Direction direction)
    {
        this.direction = direction;
    }
    public String getScore()
    {
        return "" + score;
    }
}