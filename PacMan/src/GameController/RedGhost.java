package GameController;

public class RedGhost extends Ghost{
    Direction currentDirection = Direction.DOWN;
    public RedGhost(GameBoard board, int xPosition, int yPosition)
    {
        super("red", board, xPosition, yPosition);
//        ghostsStarted = true;
//        thread = new Thread(this);
//        thread.start();
    }

    @Override
    boolean moveAlgorithm() {
        if(this.yPosition > gameBoard.pacman.yPosition && canMove(Direction.UP))
        {
            currentDirection = Direction.UP;
        }else if(this.yPosition < gameBoard.pacman.yPosition && canMove(Direction.DOWN))
        {
            currentDirection = Direction.DOWN;
        }else if(this.xPosition > gameBoard.pacman.xPosition && canMove(Direction.LEFT))
        {
            currentDirection = Direction.LEFT;
        }else if(this.xPosition < gameBoard.pacman.xPosition && canMove(Direction.RIGHT))
        {
            currentDirection = Direction.RIGHT;
        }
        if(canMove(currentDirection))
        {
            return this.move(currentDirection);
        }

        else return move(Direction.none);
    }
}
