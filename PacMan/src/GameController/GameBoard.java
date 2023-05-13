package GameController;

import GameView.GameFrame;

import java.util.HashMap;
import java.util.LinkedList;

public class GameBoard {
    final int maxX;
    final int maxY;
    final Field board[][];
    Pacman pacman;
    int collectedSnacks = 0;
    private int allSnacks = 0;
    Pawn.Direction direction = Pawn.Direction.none;

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
        for (int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board.length; j++)
            {
                if(board[i][j].containsSnack)
                    allSnacks += 1;
            }
        }
        Ghost.ghostsStarted=true;
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
        int x = 0;
        int y = 0;
        if (name.equals("red")) {
            x = 1;
            y = 1;
        } else if (name.equals("green")) {
            x = 3;
            y = 1;
        } else if (name.equals("pink")) {
            x = 1;
            y = 3;
        } else if (name.equals("purple")) {
            x = 3;
            y = 3;
        }
        loop:
        for (int i = maxY / 4 * y; i < maxY / 4 + maxY / 4 * y; i++) {
            for (int j = maxX / 4 * x; j < maxX / 4 + maxY / 4 * x; j++) {
                if (!board[i][j].isWall) {
                    Ghost g = null;
                    if (name.equals("red")) {
                        g = new RedGhost(this, j, i);
                    } else if (name.equals("green")) {
                        g = new GreenGhost(this, j, i);
                    } else if (name.equals("pink")) {
                        g = new PinkGhost(this, j, i);
                    } else if (name.equals("purple")) {
                        g = new PurpleGhost(this, j, i);
                    }
                    break loop;
                }
            }
        }
    }

    public class Field
    {
        boolean isWall;
        boolean containsSnack = true;
        boolean containsUpgrade = false;
        LinkedList<Pawn> pawns = new LinkedList<>();


        private Field(boolean isWall)
        {
            this.isWall = isWall;
            containsSnack = !isWall;
        }

        synchronized public String getImage()
        {
            if(pawns.size() == 0) {
                    if (!containsUpgrade) {
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
                //return "pacmanClosed";
                try {
                    return pawns.get(0).nextAnimation();
                }catch (NullPointerException n)
                {
                    return "emptyField";
                }

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
                System.out.println("Przegrałeś!");
                System.exit(0);
            }
            if(p.name.equals("pacman")) {
                if (containsSnack) {
                    collectedSnacks++;
                    containsSnack = false;
                    if(collectedSnacks == allSnacks) {
                        System.out.println("Zebrano wszystkie snacki!");
                        System.exit(0);
                    }
                }
            }else{
//                if(pawns.contains(pacman))
//                {
//                    System.out.println("Przegrałeś");
//                    System.exit(0);
//                }
                //System.out.println(p.name);
            }
        }
        boolean playerLost()
        {
            if(pawns.contains(pacman) && pawns.size()>=2)
                return true;
            return false;
        }
    }


    public void setDirection(Pawn.Direction direction)
    {
        this.direction = direction;
    }
}