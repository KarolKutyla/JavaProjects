import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static int k = 2;
    static final HashMap<String, Integer> map = new HashMap<String, Integer>();
    static final Vector[] trainingData = new Vector[105];
    static final Vector[] testData = new Vector[45];

    static
    {
        try (BufferedReader br = new BufferedReader(new FileReader("src/iris.txt"))){
            String line;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                Vector vector = new Vector(line);
                if (counter % 50 < 35)
                    trainingData[(counter / 50) * 35 + counter % 50] = vector;

                else
                    testData[(counter / 50) * 15 + counter % 50 - 35] = vector;
                if(!map.containsKey(vector.name))
                    map.put(vector.name, 0);
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Vector e = new Vector(6.1f, 2.6f, 5.6f, 1.4f);
        e.classification(11);
        System.out.println(e.classification(10));

    }
    static class Vector
    {
        String name;
        final float a;
        final float b;
        final float c;
        final float d;

        Vector(String str) {
            String[] arr = str.split(",");
            this.a = Float.parseFloat(arr[0]);
            this.b = Float.parseFloat(arr[1]);
            this.c = Float.parseFloat(arr[2]);
            this.d = Float.parseFloat(arr[3]);
            this.name = arr[4];
        }

        Vector(float a, float b, float c, float d)
        {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        public String classification(int k)
        {
            map.replaceAll((s, v) -> 0);
            LinkedList<Vector> vectors = new LinkedList<>();
            Arrays.sort(trainingData, Comparator.comparing(this::getDistance));
            for(int i = 0; i < k; i++)
            {
                map.put(trainingData[i].name, map.get(trainingData[i].name)+1);
            }
            String current = trainingData[0].name;
            for(String s : map.keySet())
            {
                if(map.get(current) < map.get(s))
                    current = s;
            }
            return current;
        }

        public float getDistance(Vector v)
        {
            return (float)Math.sqrt(Math.pow(this.a + v.a, 2) + Math.pow(this.b + v.b, 2) + Math.pow(this.c + v.c, 2) + Math.pow(this.d + v.d, 2));
        }


        @Override
        public String toString() {
            return "(" + a + ", " + b + ", " + c + ", " + d + ")";
        }
    }


}
