package GameController;

public class PurpleGhost extends Ghost{

    Direction currentDirection = Direction.UP;
    int moveCounter = 0;

    public PurpleGhost(GameBoard board, int xPosition, int yPosition)
    {
        super("purple", board, xPosition, yPosition);
        ghostsStarted = true;
    }
    @Override
    boolean moveAlgorithm()
    {
        if(canMove(currentDirection) && moveCounter < 8)
        {
            moveCounter++;
            return move(currentDirection);
        }else
        {
            int decision = (int)(Math.random()*2);
            if(currentDirection == Direction.UP || currentDirection == Direction.DOWN)
            {
                if(decision == 0) {
                    if (canMove(Direction.RIGHT)) {
                        moveCounter = 0;
                        currentDirection = Direction.RIGHT;
                        return move(Direction.RIGHT);
                    } else if (canMove(Direction.LEFT)) {
                        moveCounter = 0;
                        currentDirection = Direction.LEFT;
                        return move(Direction.LEFT);
                    }
                }else {
                    if (canMove(Direction.LEFT)) {
                        moveCounter = 0;
                        currentDirection = Direction.LEFT;
                        return move(Direction.LEFT);
                    } else if (canMove(Direction.RIGHT)) {
                        moveCounter = 0;
                        currentDirection = Direction.RIGHT;
                        return move(Direction.RIGHT);
                    }
                }
            }else if(currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT)
            {
                if(decision == 0) {
                    if (canMove(Direction.UP)) {
                        moveCounter = 0;
                        currentDirection = Direction.UP;
                        return move(Direction.UP);
                    } else if (canMove(Direction.DOWN)) {
                        moveCounter = 0;
                        currentDirection = Direction.DOWN;
                        return move(Direction.DOWN);
                    }
                }else {
                    if (canMove(Direction.DOWN)) {
                        moveCounter = 0;
                        currentDirection = Direction.DOWN;
                        return move(Direction.DOWN);
                    } else if (canMove(Direction.UP)) {
                        moveCounter = 0;
                        currentDirection = Direction.UP;
                        return move(Direction.UP);
                    }
                }
            }
        }
        moveCounter = 0;

        if (ghostsStarted) {
            switch ((int) (Math.random() * 4)) {
                case 0:
                    currentDirection = Pawn.Direction.UP;
                    break;
                case 1:
                    currentDirection = Pawn.Direction.DOWN;
                    break;
                case 2:
                    currentDirection = Pawn.Direction.LEFT;
                    break;
                case 3:
                    currentDirection = Pawn.Direction.RIGHT;
                    break;
            }
            return move(currentDirection);
        }
        return true;
    }
}
