import java.io.IOException;
import java.util.LinkedList;

public class OutputController {

    LinkedList<LanguagePerceptron> perceptrons = new LinkedList<>();
    
    public String whichCountry(LanguageVector vector)
    {
        double val = 0;
        String language = "Unknown";
        for(LanguagePerceptron perceptron : perceptrons)
        {
            double temp = perceptron.returnNET(vector);
            if(temp > val)
            {
                val = temp;
                language = perceptron.name;
            }
        }
        return language;
    }

    public void learn() throws IOException {
        InputController.randomTrainSet();
        for(LanguagePerceptron lp : perceptrons)
        {
            for(LanguageVector lv : InputController.trainingVectors)
                lp.learn(lv);
        }
    }

    public void test() throws IOException
    {
        for(LanguagePerceptron lp : perceptrons)
        {
            InputController.randomTestSet();
            for(LanguageVector lv : InputController.testVectors)
                System.out.println("Język pliku/język perceptronu: " + lv.getName() + "/" + lp.name + " .Wynik: " + lp.count(lv));
        }

    }

    public String whichCountry(String text)
    {
        LanguageVector vector = new LanguageVector(text);
        double val = 0;
        String language = "Unknown";
        for(LanguagePerceptron perceptron : perceptrons)
        {
            double temp = perceptron.returnNET(vector);
            if(temp > val)
            {
                val = temp;
                language = perceptron.name;
            }
        }
        return language;
    }
    public void addPerceptron(LanguagePerceptron perceptron)
    {
        perceptrons.add(perceptron);
    }
}
