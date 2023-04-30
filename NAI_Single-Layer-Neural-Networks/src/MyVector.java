import java.util.Vector;

public class MyVector extends Vector<Double> {
    final boolean correct;

    public MyVector(String line)
    {
        String[] arr = line.split(",");
        for (int i = 0; i < 4; i++)
        {
            this.add(Double.parseDouble(arr[i]));
        }
        correct = arr[5].equals("correct");
    }

    public MyVector(double... arr)
    {
        for (int i = 0; i < 4; i++)
        {
            this.add(arr[i]);
        }
        correct = false;
    }


}
