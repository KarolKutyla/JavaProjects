import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

public class Perceptron {

    double threshold = 4;
    double alpha = 0.5;
    Vector<Double> balance;

    public Perceptron(double alpha, String trainingFile, String testFile)
    {
        this.alpha = alpha;
        balance = new Vector<Double>();
        balance.add(0d);
        balance.add(0d);
        balance.add(0d);
        balance.add(0d);

        try(BufferedReader br = new BufferedReader(new FileReader("src/Data/" + trainingFile)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
//             System.out.println(balance);
//             System.out.println(threshold);
                learn(new MyVector(line));
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        try(BufferedReader br = new BufferedReader(new FileReader("src/Data/" + testFile)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                LinkedList<String> list = new LinkedList<>();
                MyVector vec = new MyVector(line);
                System.out.println("Policzono: " + count(vec) + ". Poprawny wynik to: " + vec.correct + ".");
//                System.out.println(threshold);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        MyWindow window = new MyWindow(this);
    }

    public void learn(MyVector myVector) {
//        System.out.println(myVector.get(0));
        int flow;
        if (count(myVector) == myVector.correct)
            return;
        else if (myVector.correct)
            flow = 1;
        else flow = -1;
        Vector<Double> newVector = new Vector<>();
        int i = 0;
        for (double old : balance)
            newVector.add(old + alpha * myVector.get(i++) * flow);
        balance = newVector;
        threshold = threshold - alpha * flow;
        if(flow!=0)
            System.out.println("Uczenie...");
    }

    public boolean count(MyVector myVector) {
        Iterator<Double> iterator = balance.iterator();
        int sum = 0;
        int i = 0;
        while (iterator.hasNext())
            sum += iterator.next() * myVector.get(i++);
        return sum >= threshold;
    }

    public double length() {
        Iterator<Double> iterator = balance.iterator();
        double sum = 0;
        int i = 0;
        while (iterator.hasNext())
            sum += Math.pow(iterator.next(),2);
        return Math.sqrt(sum);
    }
}
