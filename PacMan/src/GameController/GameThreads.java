package GameController;

import GameView.GameFrame;

import java.util.concurrent.Semaphore;

public class GameThreads implements Runnable {
    public static Semaphore gameSemaphore = new Semaphore(5,true);
    public static Semaphore startSemaphore = new Semaphore(1, true);
    private GameFrame gameFrame;
    private GameBoard gameBoard;
    final int timeout = 300;
    public GameThreads(int boardSize)
    {
        this.gameBoard = new GameBoard(boardSize);
        this.gameFrame = new GameFrame(gameBoard.board, gameBoard);
    }

    public void launch()
    {
        this.run();
    }

    @Override
    public void run() {
        while (true)
        {
            //System.out.println(i++);
            while (gameSemaphore.availablePermits() != 0)
            {

            }
            try {
                startSemaphore.acquire(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while (gameSemaphore.availablePermits()!=5)
            {

            }
            startSemaphore.release(1);
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //gameSemaphore.acquireUninterruptibly(1);
        }
    }
}
