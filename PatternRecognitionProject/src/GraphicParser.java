import java.awt.image.BufferedImage;
import java.io.File;

public class GraphicParser {

    DataSegment[] segments = new DataSegment[12];
    BufferedImage image;

    public GraphicParser()
    {
        File file = new File("aaa");
        image = new BufferedImage(file);
        for(int i = 0; i < segments.length; i++)
        {
            segments[i] = new DataSegment();
        }
    }
}
