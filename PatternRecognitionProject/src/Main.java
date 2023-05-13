import Segments.GraphicReduceLayer;
import Segments.SignSegment;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Main {
    static LinkedList<SignSegment> signs = new LinkedList<>();
    public static void main(String[] args) throws Exception {
//        GraphicReduceLayer gr = new GraphicReduceLayer("src/Data/Images/graphic1.png");
//        LinkedList<File> files = new LinkedList<>();
//        File dir = new File("Data/RawGraphics");
//        for(File f : dir.listFiles())
//        {
//            signs.add(new SignSegment(f.getName()));
//            for(File file : f.listFiles())
//            {
//                files.add(file);
//            }
//        }
        Initialiser i = new Initialiser();
        SignSegment s = i.signs.get(0);
//        System.out.println(s.count(GraphicReduceLayer.getConvolutions(new File("Data/RawGraphics/O/O1.png"))));
//        System.out.println(s.count(GraphicReduceLayer.getConvolutions(new File("Data/RawGraphics/O/O2.png"))));
//        System.out.println(s.count(GraphicReduceLayer.getConvolutions(new File("Data/RawGraphics/O/O3.png"))));
//        System.out.println(s.count(GraphicReduceLayer.getConvolutions(new File("Data/RawGraphics/O/O4.png"))));
//        System.out.println(s.count(GraphicReduceLayer.getConvolutions(new File("Data/RawGraphics/O/O5.png"))));
//        System.out.println(s.count(GraphicReduceLayer.getConvolutions(new File("Data/RawGraphics/T/T1.png"))));
//        System.out.println(s.count(GraphicReduceLayer.getConvolutions(new File("Data/RawGraphics/T/T2.png"))));
//        System.out.println(s.count(GraphicReduceLayer.getConvolutions(new File("Data/RawGraphics/T/T3.png"))));
//        System.out.println(s.count(GraphicReduceLayer.getConvolutions(new File("Data/RawGraphics/T/T4.png"))));
//        System.out.println(s.count(GraphicReduceLayer.getConvolutions(new File("Data/RawGraphics/A/A1.png"))));
//        System.out.println(s.count(GraphicReduceLayer.getConvolutions(new File("Data/RawGraphics/A/A3.png"))));
        System.out.println(s.count(GraphicReduceLayer.getConvolutions(new File("Data/RawGraphics/A/A4.png"))));
        GraphicReduceLayer.saveThisData("Data/RawGraphics/A/A4.png");
    }
}