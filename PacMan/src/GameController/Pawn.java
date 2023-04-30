package GameController;

import java.util.HashMap;

public class Pawn {
    public final static HashMap<Integer, Pawn> pawns = new HashMap();
    private static int staticId=0;
    public final int id = ++staticId;
    private int speed = 1;
    private int xPosition;
    private int yPosition;
    private Direction currentDirection;
    private GameBoard gameBoard;

    public Pawn(GameBoard board, int xPosition, int yPosition)
    {
        this.gameBoard = board;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.currentDirection = Direction.none;
        pawns.put(this.id, this);
    }

    public boolean move(Direction direction)
    {
        if(canMove(direction)) {
            gameBoard.board[yPosition][xPosition].remove(this);
            switch (direction) {
                case RIGHT, LEFT:
                    xPosition = (xPosition + direction.x*speed + gameBoard.maxX) % gameBoard.maxX;
                    break;
                case UP, DOWN:
                    yPosition = (yPosition + direction.y*speed + gameBoard.maxY) % gameBoard.maxY;
                    break;
            }
            gameBoard.board[yPosition][xPosition].add(this);
            return true;
        }
        else return false;
    }

    private boolean canMove(Direction direction)
    {
        if(!gameBoard.board[(this.yPosition + direction.y + gameBoard.maxY)% gameBoard.maxY][(this.xPosition + direction.x + gameBoard.maxX)%gameBoard.maxX].isWall)
        {
            System.out.println("can move");
            return true;
        }else {
            System.out.println("can't move");
            System.out.println(gameBoard);
            return false;
        }
    }



    public enum Direction
    {
        RIGHT(1,0), LEFT(-1,0), UP(0,-1), DOWN(0,1), none(0,0);

        private int x;
        private int y;
        private Direction(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
}
