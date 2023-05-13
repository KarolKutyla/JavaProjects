import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class KMeans {
    private static List<Vector> vectors = new LinkedList<>();
    private static List<Group> groups = new LinkedList<>();
    private static int vecSize;
    private static int groupNumber;

    public static void init(int vecSize, int groupNumber)
    {
        vectors.clear();
        groups.clear();
        KMeans.groupNumber = groupNumber;
        KMeans.vecSize = vecSize;
        for(int i = 0; i < groupNumber; i++)
        {
            groups.add(new Group(vecSize));
        }
    }
    public static void addVector(Vector vector)
    {
        vectors.add(vector);
    }

    public static List<Group> count() throws Exception {
        randomFromList();
        boolean flag = true;
        Vector[] localCentroids = new Vector[groupNumber];
        for(int i = 0; i < groupNumber; i++)
        {
            localCentroids[i] = groups.get(i).countCentroid();
//            for(Group g : groups)
//            {
//                g.printGroup();
//            }
        }
        while (iteration(localCentroids))
        {
            for(Group g : groups)
            {
                g.printGroup();
            }
            System.out.println("--------------------------------------------");
            for(int i = 0; i < groupNumber; i++)
            {
                localCentroids[i] = groups.get(i).countCentroid();
                groups.get(i).clear();
            }
        }
        for(Group g : groups)
        {
            g.printGroup();
        }
        System.out.println("--------------------------------------------");
        return groups;
    }


    private static boolean iteration(Vector[] centroids) throws Exception {
        boolean hasChanged = false;
        for(Vector vector : vectors)
        {
            Group bestMatch = groups.get(0);
            double currentLength = bestMatch.countDistance(vector);
            for(Group group : groups)
            {
                double tempLength = group.countDistance(vector);
                if(currentLength > tempLength)
                {
                    currentLength = tempLength;
                    bestMatch = group;
                }
            }
            bestMatch.putVector(vector);
        }
        for(int i = 0; i < centroids.length; i++)
        {
//            System.out.println(centroids[i] + ", " + groups.get(i).countCentroid());
            if(!centroids[i].equals(groups.get(i).countCentroid()))
            {
                hasChanged = true;
                break;
            }
        }
        return hasChanged;
    }
    private static void randomFromList() throws SizesNotTheSameException {
        LinkedList<Vector> tempList = new LinkedList<>();
        tempList.addAll(vectors);
        for(Group g : groups)
        {
            g.putVector(tempList.remove((int)(Math.random()*tempList.size())));
        }
    }
}
