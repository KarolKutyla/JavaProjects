package GameController;

class Pacman extends Pawn{

    private String frame = "pacmanRightOpen";

    public Pacman(GameBoard board, int xPosition, int yPosition) {
        super("pacman", board, xPosition, yPosition);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    synchronized public boolean move(Direction direction) {
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
                if(this.frame.equals("pacmanClosed") || !currentDirection.equals(direction))
                {
                    switch (direction)
                    {
                        case UP:
                            frame = "pacmanUpOpen";
                            break;
                        case DOWN:
                            frame = "pacmanDownOpen";
                            break;
                        case LEFT:
                            frame = "pacmanLeftOpen";
                            break;
                        case RIGHT:
                            frame = "pacmanRightOpen";
                            break;
                    }
                }else
                {
                    this.frame = "pacmanClosed";
                }
                currentDirection = direction;
                return true;
            }
            else {
                this.frame = "pacmanClosed";
                currentDirection = direction;
                return false;
            }
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
//            try {
//                GameThreads.pacmanSemaphore.acquire(1);
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            //GameThreads.pacmanSemaphore.tryAcquire(1);
            //GameThreads.gameSemaphore.acquireUninterruptibly(1);
//            System.out.println("aquired");
//            System.out.println(move(gameBoard.direction));
//            System.out.println("Moved");

            try {
                GameThreads.gameSemaphore.acquire(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while (GameThreads.startSemaphore.availablePermits()!=0)
            {

            }
            synchronized (this) {
                move(gameBoard.direction);
            }
            GameThreads.gameSemaphore.release();
            while (GameThreads.startSemaphore.availablePermits()!=1)
            {

            }
            //GameThreads.gameSemaphore.release(1);
        }
    }
}
