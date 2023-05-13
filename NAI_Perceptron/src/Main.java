import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            List<LanguagePerceptron> lp = InputController.init();
            OutputController controller = new OutputController();
            for(LanguagePerceptron p : lp) {
                System.out.println(p.name);
                controller.addPerceptron(p);
            }
            new InputController();
//            LanguagePerceptron germanPerceptron = new LanguagePerceptron("German");
//            LanguagePerceptron frenchPerceptron = new LanguagePerceptron("French");
//            LanguagePerceptron englishPerceptron = new LanguagePerceptron("English");
//            LanguagePerceptron polishPerceptron = new LanguagePerceptron("Polish");

//            controller.addPerceptron(germanPerceptron);
//            controller.addPerceptron(frenchPerceptron);
//            controller.addPerceptron(englishPerceptron);
//            controller.addPerceptron(polishPerceptron);
            for(int i = 0; i < 5; i++)
            {
                controller.learn();
            }
            controller.test();
            //System.out.println(controller.whichCountry("Der Park in meiner Nähe ist sehr schön."));
            //System.out.println(controller.whichCountry("Le parc près de chez moi est très agréable."));
            new MyWindow(controller);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
