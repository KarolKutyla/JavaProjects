import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigLoader {
    static String rawGraphicsCataloguePath;
    static String formattedGraphicsCataloguePath;
    public static void configure()
    {
        try(BufferedReader br = new BufferedReader(new FileReader("config.jaml"));){
            rawGraphicsCataloguePath = br.readLine().split("=")[1];
            formattedGraphicsCataloguePath = br.readLine().split("=")[1];
        }catch (Exception e)
        {
            System.exit(1);
        }

    }
}
