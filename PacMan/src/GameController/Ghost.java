package GameController;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public abstract class Ghost extends Pawn{
    String currentFrame;
    Thread thread;
    public static boolean ghostsStarted = false;
    public Ghost(String color, GameBoard board, int xPosition, int yPosition) {
        super(color+"Ghost", board, xPosition, yPosition);
        currentFrame=this.name+1;
//        board.board[xPosition][yPosition].pawns.add(this);
        thread = new Thread(this);
        thread.start();
    }

    @Override
    synchronized public String nextAnimation() {
        if (currentFrame.equals(this.name+1))
        {
            currentFrame = this.name+2;
            return this.name+1;
        }else
        {
            currentFrame = this.name+1;
            return this.name+2;
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

            }
            synchronized (this)
            {
                while (!moveAlgorithm()) {

                }
            }
            GameThreads.gameSemaphore.release(1);
            while (GameThreads.startSemaphore.availablePermits() != 1) {

            }

        }
    }
    abstract boolean moveAlgorithm();
}
