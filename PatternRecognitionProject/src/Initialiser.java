import PerceptronPackage.Perceptron;
import PerceptronPackage.Vector;
import Segments.GraphicReduceLayer;
import Segments.SignSegment;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Initialiser {
    LinkedList<SignSegment> signs = new LinkedList<>();
    LinkedList<File> trainingFiles = new LinkedList<>();
    public Initialiser()
    {
        File dir = new File("Data/RawGraphics");
        for(File f : dir.listFiles())
        {
            signs.add(new SignSegment(f.getName().split("[0-9]")[0]));
            System.out.println(f.getName());
            for(File file : f.listFiles())
            {
                trainingFiles.add(file);
            }
        }
        trainingFiles.sort((x, y)->{return (int)(Math.random()*2)-1;});
        List<Vector> trainingList = trainingFiles.stream().map((x)->{
            try {
                return GraphicReduceLayer.getConvolutions(x);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
        for(SignSegment s : signs)
        {
            s.learnAll(trainingList);
        }
    }

}
