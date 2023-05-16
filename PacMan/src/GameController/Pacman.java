package GameController;

import GameView.MusicBoard;

public class Pacman extends Pawn{

    private String frame = "pacmanRightOpen";
    Thread thread;
    public int HP = 1;
    public int voidWalk = 0;
    int voidX;
    int voidY;
    Coordinate coordinate;
    Coordinate voidWalkCoordinate;
    Coordinate teleportCoordinate;
    public boolean teleport = false;
    public int speedValue = 0;
    public int berserkCounter = 0;

    public Pacman(GameBoard board, int xPosition, int yPosition) {
        super("pacman", board, xPosition, yPosition);
        thread = new Thread(this);
        thread.start();
    }

    public void saveTeleportCoordinate()
    {
        teleportCoordinate = new Coordinate(this.xPosition, this.yPosition);
    }

    public void teleport()
    {
        if(teleportCoordinate != null && teleport)
        {
            changeField(teleportCoordinate);
        }
    }

    @Override
    synchronized public boolean move(Direction direction) {
        {
            if(speedCounter >= speedCounterValue - speedValue)
            {
                speedCounter = 0;
                if(gameBoard.board[yPosition][xPosition].isWall && voidWalk == 0)
                {
                    if(canMove(direction))
                    {
                        changeField(direction);
                    }else
                    {
                        if(canMove(Direction.UP))
                        {
                            changeField(Direction.UP);
                            return true;
                        }else if(canMove(Direction.RIGHT))
                        {
                            changeField(Direction.RIGHT);
                            return true;
                        }else if(canMove(Direction.DOWN))
                        {
                            changeField(Direction.DOWN);
                            return true;
                        }else if(canMove(Direction.LEFT))
                        {
                            changeField(Direction.LEFT);
                            return true;
                        }else
                        {
                            xPosition = voidX;
                            yPosition = voidY;
                        }
                    }
                    return true;
                }else if(canMove(direction)) {
                    changeField(direction);
                    return true;
                }
                else {
                    if(voidWalk > 0)
                    {
                        if(!gameBoard.board[yPosition][xPosition].isWall)
                        {
                            voidX = xPosition;
                            voidY =yPosition;
                        }
                        changeField(direction);
                        voidWalk--;
                        return true;
                    }
                    this.frame = "pacmanClosed";
                    currentDirection = direction;
                    return false;
                }
            }
            else
            {
                speedCounter++;
                return true;
            }
        }
    }

    private synchronized void changeField(Direction direction)
    {
            MusicBoard.playClip("pacman_chomp");
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
        if(this.frame.equals("pacmanClosed") || !currentDirection.equals(direction))
        {
            switch (direction) {
                case UP -> frame = "pacmanUpOpen";
                case DOWN -> frame = "pacmanDownOpen";
                case LEFT -> frame = "pacmanLeftOpen";
                case RIGHT -> frame = "pacmanRightOpen";
            }
        }else
        {
            this.frame = "pacmanClosed";
        }
        currentDirection = direction;
    }

    synchronized private void changeField(Coordinate coordinate) {
        if(teleport)
        {
            teleport = false;
            MusicBoard.playClip("pacman_chomp");
            gameBoard.board[yPosition][xPosition].remove(this);
            xPosition=coordinate.x;
            yPosition=coordinate.y;
            coordinate.getField().add(this);
            this.frame = "pacmanClosed";
        }

    }

    public void addUpgrade(String str) {
        switch (str) {
            case "SPEED": {
                if(speedValue < 10)
                    speedValue++;
                break;
            }
            case "VOID_WALK": {
                if(voidWalk < 5)
                    voidWalk++;
                break;
            }
            case "BERSERK": {
                berserkCounter = 600;
                gameBoard.ghosts.forEach(Ghost::makeVulnerable);
                MusicBoard.playClip("pacman_extrapac");
            }
            case "HP": {
                if(HP < 3)
                {
                    HP++;
                }
                break;
            }
            case "TELEPORT": {
                teleport = true;
                break;
            }
        }
    }


    private class Coordinate
    {
        final int x;
        final int y;
        private Coordinate(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        private GameBoard.Field getField()
        {
            return gameBoard.board[y][x];
        }
    }



    @Override
    synchronized public String nextAnimation() {
        return frame;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                GameThreads.gameSemaphore.acquire(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while (GameThreads.startSemaphore.availablePermits()!=0)
            {
                if(Thread.interrupted())
                {
                    return;
                }
            }
            synchronized (this) {
                move(gameBoard.direction);
            }
            if(berserkCounter > 0)
            {
                berserkCounter--;
            }
            GameThreads.gameSemaphore.release();
            while (GameThreads.startSemaphore.availablePermits()!=1)
            {
                if(Thread.interrupted())
                {
                    return;
                }
            }
        }
    }
}
