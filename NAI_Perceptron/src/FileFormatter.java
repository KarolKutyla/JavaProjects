import java.io.*;
import java.util.LinkedList;

public class FileFormatter {

    public static void main(String[] args) {
//        String filepath = "src/Data/aaa";
//        changeFormat(filepath, "German");
        shuffle();
    }

    static void changeFormat(String filepath, String name)
    {
        LinkedList<String> list = new LinkedList<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath))))
        {
            String line;
            while ((line = br.readLine())!= null)
            {
                list.add(line+";"+name);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        for(String s : list)
        {
            System.out.println(s);
        }
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath)))){
            for (String s : list)
            {
                bw.write(s);
                bw.newLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void shuffle()
    {
        LinkedList<String> list = new LinkedList<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/Data/Test.txt"))))
        {
            String line;
            while ((line = br.readLine())!= null)
            {
                list.add(line);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        for(String s : list)
        {
            System.out.println(s);
        }
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/Data/Training.txt")))){
            while (list.size()>0)
            {
                bw.write(list.remove((int)(Math.random()%list.size())));
                bw.newLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
