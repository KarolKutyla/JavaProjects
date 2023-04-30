import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        KMeans.init(2, 3);
        KMeans.addVector(new Vector(5,6));
        KMeans.addVector(new Vector(10,12));
        KMeans.addVector(new Vector(11,11));
        KMeans.addVector(new Vector(0,-3));
        KMeans.addVector(new Vector(-1,-2));
        KMeans.addVector(new Vector(6,5));
        KMeans.addVector(new Vector(-1,-2));
        KMeans.addVector(new Vector(5,5));
        KMeans.addVector(new Vector(6,5));
        KMeans.addVector(new Vector(12,10));
        List<Group> list = KMeans.count();
        for(Group g : list)
        {
            System.out.println("Centroid: " + g.countCentroid());
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