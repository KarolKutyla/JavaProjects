package GameController;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public abstract class Ghost extends Pawn{
    String currentFrame;
    Thread thread;
    long time = System.currentTimeMillis();
    public boolean vulnerable = false;
    private int vulnerableCounter = 0;

    public Ghost(String color, GameBoard board, int xPosition, int yPosition) {
        super(color+"Ghost", board, xPosition, yPosition);
        currentFrame=this.name+1;
//        board.board[xPosition][yPosition].pawns.add(this);
        thread = new Thread(this);
        thread.start();
    }

    @Override
    synchronized public String nextAnimation() {
        String first;
        if(vulnerable)
            first = "vulnerableGhost";
        else
            first = this.name;
        if (currentFrame.equals(this.name+1))
        {
            currentFrame = this.name+2;
            return first+1;
        }else
        {
            currentFrame = this.name+1;
            return first+2;
        }
    }
    @Override
    public void run() {
        while (true) {
            try {
                GameThreads.gameSemaphore.acquire(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while (GameThreads.startSemaphore.availablePermits() != 0) {
                if(Thread.interrupted())
                {
                    return;
                }
            }
            synchronized (this) {
                while (!moveAlgorithm()) {

                }
                if (vulnerable && vulnerableCounter > 0) {
                    vulnerableCounter--;
                } else {
                    vulnerable = false;
                }
            }
            GameThreads.gameSemaphore.release(1);
            while (GameThreads.startSemaphore.availablePermits() != 1) {
                if(Thread.interrupted())
                {
                    return;
                }
            }
        }
    }

    @Override
    public synchronized boolean move(Direction direction) {
        if(System.currentTimeMillis() - time >= 5*1000)
        {
            time = System.currentTimeMillis();
            if(gameBoard.board[yPosition][xPosition].upgrade==null)
            {
                gameBoard.board[yPosition][xPosition].upgrade = GameBoard.Field.Upgrade.generate();
            }
        }

        boolean hasMoved = super.move(direction);
        return hasMoved;
    }

    public void makeVulnerable()
    {
        vulnerable = true;
        vulnerableCounter = 600;
    }

    abstract boolean moveAlgorithm();
}
