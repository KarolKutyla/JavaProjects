package GameController;

import GameView.EndGameFrame;
import GameView.GameFrame;
import GameView.MenuFrame;

import java.lang.management.GarbageCollectorMXBean;
import java.util.concurrent.Semaphore;

public class GameThreads {
    public static Semaphore gameSemaphore;
    public static Semaphore startSemaphore;
    private MenuFrame menuFrame;
    private GameFrame gameFrame;
    private GameBoard gameBoard;
    final int timeout = 15;
    boolean stopped = false;
    public GameThreads()
    {
        this.menuFrame = new MenuFrame(this);
//        this.gameBoard = new GameBoard(boardSize);
//        this.gameFrame = new GameFrame(gameBoard.board, gameBoard);
    }

    public void launch(int boardSize)
    {
        System.gc();
        gameSemaphore = new Semaphore(5,true);
        startSemaphore = new Semaphore(1, true);
        this.gameBoard = new GameBoard(boardSize);
        this.gameFrame = new GameFrame(gameBoard.board, gameBoard);
        Thread t = new Thread(new GameCycle());
        t.start();
        //this.run();
    }

    class GameCycle implements Runnable
    {
        @Override
        public void run() {
            while (!gameBoard.finished)
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
            if(gameBoard.pacman.HP <= 0)
            {
                stopped = true;
                new EndGameFrame(gameBoard.score);
                gameFrame.dispose();
            }
        }
    }
}
