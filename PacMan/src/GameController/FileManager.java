package GameController;

import GameView.HighScoreFrame;

import java.io.*;
import java.util.LinkedList;

public class FileManager {
    static final String filePath = "Resources/Files/high_scores.txt";

    public static LinkedList<HighScoreRecord> readHighScores()
    {
        LinkedList<HighScoreRecord> list = new LinkedList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String str = null;
            while ((str = br.readLine()) != null)
            {
                list.add(new HighScoreRecord(str));
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
//        list.sort((x1, x2) -> {return x2.getScore() - x1.getScore();});
        return list;
    }

    public static void addScore(String score)
    {
        LinkedList<HighScoreRecord> list = new LinkedList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String str = null;
            while ((str = br.readLine()) != null)
            {
                list.add(new HighScoreRecord(str));
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        list.add(new HighScoreRecord(score));
        list.sort((x1, x2) -> {return x2.getScore() - x1.getScore();});
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            int max;
            if(list.size() > 10)
            {
                max = 10;
            }else {
                max = list.size();
            }
            for(int i = 0; i < max; i++)
            {
                bw.write(list.get(i) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class HighScoreRecord
    {
        private String playerName;
        private int score;
        public HighScoreRecord(String line)
        {
            String[] arr = line.split(";");
            playerName = arr[0];
            score = Integer.parseInt(arr[1]);
        }

        public HighScoreRecord(String playerName, int score)
        {
            this.playerName = playerName;
            this.score = score;
        }

        public String getPlayerName()
        {
            return playerName;
        }

        public int getScore()
        {
            return score;
        }

        @Override
        public String toString() {
            return playerName + ";" +score;
        }
    }
}
