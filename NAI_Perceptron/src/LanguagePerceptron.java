import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public class LanguagePerceptron {

    final String name;

    static String trainingFilePath = "src/Data/Test.txt";
    static String testFilePath="src/Data/Test.txt";
    double threshold = 4;
    double alpha = 0.2;
    Vector<Double> balance;

    public LanguagePerceptron(String name) throws IOException {
        this.name = name;
        balance = new Vector<Double>();
        for (int i = 0; i <= 'Z' - 'A'; i++)
            balance.add(0d);
        //MyWindow window = new MyWindow(this);
    }

    private void train() throws IOException
    {
//        try(BufferedReader br = new BufferedReader(new FileReader(trainingFilePath)))
//        {
//            String line;
//            while ((line = br.readLine()) != null)
//            {
//                learn(new LanguageVector(line));
//            }
//        }
        for(LanguageVector vec : InputController.trainingVectors)
            learn(vec);
    }
    private void test() throws IOException
    {
//        try(BufferedReader br = new BufferedReader(new FileReader(testFilePath)))
//        {
//            String line;
//            while ((line = br.readLine()) != null)
//            {
//                LanguageVector vec = new LanguageVector(line);
//                //System.out.println("Policzono: " + count(vec) + ". Poprawny wynik to: " + vec.getName() + ".");
//            }
//        }
        for(LanguageVector vec : InputController.testVectors)
            System.out.println("Perceptron " + name + " policzył: " + count(vec) + ". Poprawny wynik to: " + vec.getName() + ".");

    }


    public void learn(LanguageVector languageVector) {
        int flow;
        boolean isMatching = languageVector.getName().equals(name);
        if (count(languageVector) == isMatching)
            return;
        else if (isMatching)
            flow = 1;
        else flow = -1;
        Vector<Double> newVector = new Vector<>();
        int i = 0;
        for (double old : balance)
            newVector.add(old + alpha * languageVector.get(i++) * flow);
        balance = newVector;
        threshold = threshold - alpha * flow;
    }

    public boolean count(LanguageVector languageVector) {
        Iterator<Double> iterator = balance.iterator();
        double sum = 0;
        int i = 0;
        while (iterator.hasNext())
            sum += iterator.next() * languageVector.get(i++);
        return sum >= threshold;
    }
    public double returnNET(LanguageVector languageVector)
    {
        Iterator<Double> iterator = balance.iterator();
        double sum = 0;
        int i = 0;
        while (iterator.hasNext())
            sum += iterator.next() * languageVector.get(i++);
        return sum-threshold;
    }
}
