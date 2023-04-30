package GameController;

import GameView.GameFrame;

import java.util.HashMap;

public class GameBoard {
    final int maxX;
    final int maxY;
    final Field board[][];
    GameFrame gameFrame;
    Pacman pacman;

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
        gameFrame = new GameFrame(this.board, this);
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
                    board[i][j].containsPacman = true;
                    pacman = new Pacman(this, j, i);
                    break loop;
                }
            }
        }
    }



//    public void testMethod_print()
//    {
//        for(int i = 0; i < maxY; i++)
//        {
//            for (int j = 0; j < maxX; j++)
//            {
//                if(board[i][j] == -1) System.out.print(" ");
//                else System.out.print(".");
//            }
//            System.out.println();
//        }
//    }

    public static class Field
    {
        boolean isWall;
        boolean containsSnack = true;
        boolean containsUpgrade = false;
        boolean containsPacman = false;
        HashMap<String, Pawn> ghosts = new HashMap<>();


        private Field(boolean isWall)
        {
            this.isWall = isWall;
            containsSnack = !isWall;
        }
        public void addPawn(Pawn p)
        {

        }

        public String getImage()
        {
            if(ghosts.size() == 0) {
                if (!containsPacman) {
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
                }
                {
                    return "pacmanClosed";
                }
            }
            return "redGhost";
        }

        void remove(Pawn p)
        {
            if(p.getClass() == Pacman.class)
                containsPacman = false;
        }
        void add(Pawn p)
        {
            if(p.getClass() == Pacman.class)
                containsPacman = true;
        }
    }

    public void movePawn(Pawn.Direction direction)
    {
        if(pacman.move(direction))
        {
            System.out.println("Przemieszczono");
        }
    }


}
