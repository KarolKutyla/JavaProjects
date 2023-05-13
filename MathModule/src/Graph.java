import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Graph {
    private int[][] arr;

    public Graph(String filename, boolean directed, boolean hasWeights) throws IOException
    {
        Scanner scr = new Scanner(new File(filename));
        int numberOfVertices = scr.nextInt();
        arr = new int[numberOfVertices][numberOfVertices];
        while (scr.hasNext())
        {
            int vertex = scr.nextInt();
            int edge = scr.nextInt();
            arr[vertex][edge] = 1;
        }
    }
}
