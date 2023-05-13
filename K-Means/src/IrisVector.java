import java.text.ParseException;

public class IrisVector extends Vector{

    String name;
    public IrisVector(String line, int numberOfAttributes) {
        String[] arr = line.split(",");
        for (int i = 0; i < numberOfAttributes; i++) {
            try {
                this.add(Double.parseDouble(arr[i]));
            } catch (Exception e) {
                this.add(0d);
            }
            this.name = arr[arr.length - 1];
        }
    }

}
