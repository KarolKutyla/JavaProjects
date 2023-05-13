package GameController;

import java.util.HashMap;
import java.util.Objects;

abstract public class Pawn implements Animated, Runnable{
    private final static HashMap<String, Pawn> pawns = new HashMap<>();
    static int staticId=0;
    public final int id = ++staticId;
    public final String name;
    int speed = 1;
    int xPosition;
    int yPosition;
    Direction currentDirection;
    GameBoard gameBoard;

    public Pawn(String name, GameBoard board, int xPosition, int yPosition)
    {
        this.name = name;
        this.gameBoard = board;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.currentDirection = Direction.none;
        board.board[yPosition][xPosition].add(this);
        pawns.put(this.name, this);
    }

    public Pawn getByName(String str)
    {
        return pawns.get(str);
    }

    synchronized public boolean move(Direction direction)
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

    synchronized boolean canMove(Direction direction)
    {
        if(!gameBoard.board[(this.yPosition + direction.y + gameBoard.maxY)% gameBoard.maxY][(this.xPosition + direction.x + gameBoard.maxX)%gameBoard.maxX].isWall)
        {
            return true;
        }else {
            //System.out.println(gameBoard);
            return false;
        }
    }

    public enum Direction
    {
        RIGHT(1,0), LEFT(-1,0), UP(0,-1), DOWN(0,1), none(0,0);

        int x;
        int y;
        private Direction(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pawn pawn = (Pawn) o;
        return name.equals(pawn.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
