import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        Perceptron perceptron = new Perceptron(Double.parseDouble(args[0]), args[1], args[2]);

//        ArrayList<String> arr1 = new ArrayList<>();
//        ArrayList<String> arr2 = new ArrayList<>();
//        try(BufferedReader br = new BufferedReader(new FileReader("src/Data/Current_training.txt"))) {
//            String str;
//
//            int i = 0;
//            while ((str = br.readLine()) != null)
//            {
//                if(i <35)
//                    arr1.add(str);
//                else arr2.add(str);
//                i++;
//            }
//        }catch (IOException e)
//        {
//        e.printStackTrace();
//        }
//        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/Data/Mixed_training.txt"))) {
//
//
//            for(int i = 0; i < 70; i++)
//            {
//                if(i%2 ==0)
//                bw.write(arr1.get(i/2)+"\n");
//                else
//                    bw.write(arr2.get(i/2)+"\n");
//            }
//
//        }catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }
    }
}
