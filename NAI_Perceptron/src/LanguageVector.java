import java.util.Vector;

public class LanguageVector extends Vector<Integer> {
    private String name;
    final int range = 'Z' - 'A' + 1;

    public LanguageVector(String line, String language)
    {
        char[] arr = line.toUpperCase().toCharArray();
        int[] values = new int[range];
        for(int i : arr)
        {
            try {
                values[i-'A']++;
            }catch (ArrayIndexOutOfBoundsException e)
            {

            }
        }
        for (int i : values)
        {
            this.add(i);
        }
        name = language;
    }

    public LanguageVector(String line)
    {
        String[] data = line.split(";");
        char[] arr = data[0].toUpperCase().toCharArray();
        int[] values = new int[range];
        for(int i : arr)
        {
            try {
                values[i-'A']++;
            }catch (ArrayIndexOutOfBoundsException e)
            {

            }
        }
        for (int i : values)
        {
            this.add(i);
        }
        try
        {
            name = data[1];
        }catch (ArrayIndexOutOfBoundsException e)
        {
            name = "_custom_";
        }
    }

    public String getName() {
        return name;
    }
}
