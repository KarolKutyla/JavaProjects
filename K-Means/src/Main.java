import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String[] args) throws Exception {
        String filePath = args[0];
        int k = Integer.parseInt(args[1]);
        int number = 4;
        KMeans.init(number, k);
        LinkedList<IrisVector> vectorList = new LinkedList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line = null;
            while ((line = br.readLine()) != null)
            {
                IrisVector i = new IrisVector(line, number);
                vectorList.add(i);
                KMeans.addVector(i);
            }
        }catch (IOException e)
        {

        }
//        KMeans.addVector(new Vector(5,6));
//        KMeans.addVector(new Vector(10,12));
//        KMeans.addVector(new Vector(11,11));
//        KMeans.addVector(new Vector(0,-3));
//        KMeans.addVector(new Vector(-1,-2));
//        KMeans.addVector(new Vector(6,5));
//        KMeans.addVector(new Vector(-1,-2));
//        KMeans.addVector(new Vector(5,5));
//        KMeans.addVector(new Vector(6,5));
//        KMeans.addVector(new Vector(12,10));
        //KMeans.addVector(new Vector(100,100));
        List<Group> list = KMeans.count();
        for(Group g : list)
        {
            System.out.println("Centroid: " + g.countCentroid());
        }
        for(Group group : list)
        {
            group.printGroup();
        }

//        KMeans.init(2, 1);
//        KMeans.addVector(new Vector(5,6));
//        KMeans.addVector(new Vector(10,12));
//        KMeans.addVector(new Vector(11,11));
//        KMeans.addVector(new Vector(0,-3));
//        KMeans.addVector(new Vector(-1,-2));
//        KMeans.addVector(new Vector(6,5));
//        KMeans.addVector(new Vector(-1,-2));
//        KMeans.addVector(new Vector(5,5));
//        KMeans.addVector(new Vector(6,5));
//        KMeans.addVector(new Vector(12,10));
//        List<Group> list = KMeans.count();
//        for(Group g : list)
//        {
//            System.out.println(g.countCentroid());
//        }

    }
}