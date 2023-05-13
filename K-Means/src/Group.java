import java.util.LinkedList;
import java.util.List;

public class Group {
    private List<Vector> vectors = new LinkedList<>();
    private int vecSize;

    private Vector centroid;

    public Group(int vecSize)
    {
        this.vecSize = vecSize;
    }
    public void putVector(Vector vec) throws SizesNotTheSameException
    {
        if(vec.size() == vecSize) {
            vectors.add(vec);
        }else
        {
            throw new SizesNotTheSameException();
        }
    }
    public Vector countCentroid() throws Exception {
        Vector centroid = new Vector();
        for(int i = 0; i < vecSize; i++)
        {
            centroid.add(0d);
        }
        for(Vector vector : vectors)
        {
            centroid.addVector(vector);
        }
        for(int i = 0; i < vecSize; i++)
        {
            centroid.set(i, centroid.get(i)/vectors.size());
        }
        this.centroid = centroid;
        return centroid;
    }

    public double countDistance(Vector vector)
    {
        double distance = 0d;
        for(int i = 0; i < centroid.size(); i++)
        {
            distance += Math.pow(centroid.get(i)-vector.get(i), 2);
        }
        return distance;
    }

    public void clear()
    {
        vectors = new LinkedList<>();
    }

    public void printGroup()
    {
        double sum = 0;
        for(Vector v : vectors) {
            IrisVector irisVector = (IrisVector) v;
            System.out.println("In this group: " + irisVector.name);
            sum+=Vector.squareDistance(this.centroid, v);
        }
        System.out.printf("Suma kwadratów odległości to %f: \n\n", sum);
    }

}
