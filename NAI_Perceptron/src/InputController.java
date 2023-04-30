import java.io.*;
import java.util.*;
import java.util.function.Consumer;

public class InputController{

    static final String trainingPath = "src/Data/Training";
    static final String testPath = "src/Data/Training";

    static List<LanguageVector> trainingVectors;
    static List<LanguageVector> testVectors;

//    static {
//        //System.out.println("XD");
//        try {
//            randomTrainSet();
//            randomTestSet();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static LinkedList<LanguagePerceptron> init() throws IOException {
        HashSet<String> hash = new HashSet<>();
        LinkedList<LanguagePerceptron> returnList = new LinkedList<>();
        File dir = new File(trainingPath);
        //System.out.println(dir.exists());
        for(File f : dir.listFiles())
        {
            hash.add(f.getName().split("_")[0]);
        }
        for(String s : hash)
            returnList.add(new LanguagePerceptron(s));
        return returnList;
    }
    public static void randomTrainSet() throws IOException
    {
        trainingVectors = new LinkedList<>();
        File dir = new File(trainingPath);
        //System.out.println(dir.exists());
        for(File f : dir.listFiles())
        {
            BufferedReader br = new BufferedReader(new FileReader(f));
            //System.out.println(f.getName());
            StringBuilder builder = new StringBuilder();
            String str = null;
            while ((str = br.readLine()) != null)
                builder.append(str);
            br.close();
            trainingVectors.add(new LanguageVector(builder.toString(), f.getName().split("_")[0]));

        }
        trainingVectors.sort((a,b)-> (int)(Math.random()*100-50.5));
    }

    public static void randomTestSet() throws IOException
    {
        testVectors = new LinkedList<>();
        File dir = new File(testPath);
        //System.out.println(dir.exists());
        for(File f : dir.listFiles())
        {
            BufferedReader br = new BufferedReader(new FileReader(f));
            //System.out.println(f.getName());
            StringBuilder builder = new StringBuilder();
            String str = null;
            while ((str = br.readLine()) != null)
                builder.append(str);
            br.close();
            testVectors.add(new LanguageVector(builder.toString(), f.getName().split("_")[0]));

        }
    }
}
