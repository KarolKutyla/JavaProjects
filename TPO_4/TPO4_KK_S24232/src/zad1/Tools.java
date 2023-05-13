/**
 * @author Kutyła Karol S24232
 */

package zad1;


import org.yaml.snakeyaml.Yaml;


import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Tools {

    static Options createOptionsFromYaml(String fileName) throws Exception {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = Files.newInputStream(Paths.get(fileName))) {
            HashMap<String, Object> data = yaml.load(inputStream);
            return new Options(data.get("host").toString(), Integer.parseInt(data.get("port").toString()),
                    Boolean.parseBoolean(data.get("concurMode").toString()),
                    Boolean.parseBoolean(data.get("showSendRes").toString()),
                    (HashMap<String, List<String>>) data.get("clientsMap"));
        }
    }
}
