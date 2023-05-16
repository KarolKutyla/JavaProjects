package GameController;

public class PinkGhost extends Ghost{
    int mode = 0;
    int moveCounter = 0;
    public PinkGhost(GameBoard board, int xPosition, int yPosition) {
        super("pink", board, xPosition, yPosition);
    }

    @Override
    boolean moveAlgorithm() {
        if(mode < 20) {
            if (canMove(currentDirection) && moveCounter < 8) {
                moveCounter++;
                mode++;
                return move(currentDirection);
            } else {
                int decision = (int) (Math.random() * 2);
                if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) {
                    if (decision == 0) {
                        if (canMove(Direction.RIGHT)) {
                            moveCounter = 0;
                            currentDirection = Direction.RIGHT;
                            mode++;
                            return move(Direction.RIGHT);
                        } else if (canMove(Direction.LEFT)) {
                            moveCounter = 0;
                            currentDirection = Direction.LEFT;
                            mode++;
                            return move(Direction.LEFT);
                        }
                    } else {
                        if (canMove(Direction.LEFT)) {
                            moveCounter = 0;
                            currentDirection = Direction.LEFT;
                            mode++;
                            return move(Direction.LEFT);
                        } else if (canMove(Direction.RIGHT)) {
                            moveCounter = 0;
                            currentDirection = Direction.RIGHT;
                            mode++;
                            return move(Direction.RIGHT);
                        }
                    }
                } else if (currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) {
                    if (decision == 0) {
                        if (canMove(Direction.UP)) {
                            moveCounter = 0;
                            currentDirection = Direction.UP;
                            mode++;
                            return move(Direction.UP);
                        } else if (canMove(Direction.DOWN)) {
                            moveCounter = 0;
                            currentDirection = Direction.DOWN;
                            mode++;
                            return move(Direction.DOWN);
                        }
                    } else {
                        if (canMove(Direction.DOWN)) {
                            moveCounter = 0;
                            currentDirection = Direction.DOWN;
                            mode++;
                            return move(Direction.DOWN);
                        } else if (canMove(Direction.UP)) {
                            moveCounter = 0;
                            currentDirection = Direction.UP;
                            mode++;
                            return move(Direction.UP);
                        }
                    }
                }
            }
            moveCounter = 0;


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
                mode++;
                return move(currentDirection);

        }else if(mode < 40)
        {
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
                mode++;
                return this.move(currentDirection);
            }
            else
            {
                mode++;
                return move(Direction.none);
            }
        }else {
            mode = 0;
            return false;
        }
    }
}
