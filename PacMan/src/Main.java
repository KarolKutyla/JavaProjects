import GameController.GameBoard;
import GameController.GameThreads;
import GameView.GameFrame;

public class Main {
    public static void main(String[] args) {
        //GameBoard board = new GameBoard(60);
        GameThreads gameThreads = new GameThreads(100);
        gameThreads.launch();
    }


}