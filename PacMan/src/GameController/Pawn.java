package GameController;

import java.util.HashMap;
import java.util.Objects;

abstract public class Pawn implements Animated, Runnable{
    static int staticId=0;
    public final int id = ++staticId;
    public final String name;
    int xPosition;
    int yPosition;
    Direction currentDirection;
    GameBoard gameBoard;
    protected int speedCounter = 0;
    protected int speedCounterValue = 20;

    public Pawn(String name, GameBoard board, int xPosition, int yPosition)
    {
        this.name = name;
        this.gameBoard = board;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.currentDirection = Direction.none;
        board.board[yPosition][xPosition].add(this);
    }

    synchronized public boolean move(Direction direction)
    {
        if(speedCounter >= speedCounterValue)
        {
            speedCounter = 0;
            if(canMove(direction)) {

                gameBoard.board[yPosition][xPosition].remove(this);
                switch (direction) {
                    case RIGHT, LEFT:
                        xPosition = (xPosition + direction.x + gameBoard.maxX) % gameBoard.maxX;
                        break;
                    case UP, DOWN:
                        yPosition = (yPosition + direction.y + gameBoard.maxY) % gameBoard.maxY;
                        break;
                }
                gameBoard.board[yPosition][xPosition].add(this);

                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            speedCounter++;
            return true;
        }
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
