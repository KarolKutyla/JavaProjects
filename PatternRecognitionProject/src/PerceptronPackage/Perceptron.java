package PerceptronPackage;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Perceptron implements Serializable {
    static String path;
    LinkedList<Double> weights = new LinkedList<>();
    static double alpha = 0.1;
    double bias = 1;
    String name;

    public Perceptron(String name, int size) {
        this.name = name;
        for (int i = 0; i < size; i++) {
            weights.add(1d);
        }
    }

    public Perceptron(String path) {
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String str = null;
            while ((str = bf.readLine()) != null) {
                weights.add(Double.parseDouble(str));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (double d : weights) {
                bw.write("" + d + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double count(Vector vector) {
        System.out.println(name);
        System.out.println(vector.name);
        System.out.println(":");
        double sum = 0;
        for (int i = 0; i < weights.size(); i++) {
            System.out.println(vector.get(i));
            sum += (double) vector.get(i) * weights.get(i);
        }
        System.out.println(sum-bias>0);
        return sum - bias;
    }

    public void learn(Vector vector) {
        boolean result = count(vector) >= 0;
        boolean match = this.name.equals(vector.name);
        int k = 0;
        if (result && !match)
            k = -1;
        else if (match && !result)
            k = 1;
        if (k == 0)
            return;
        for (int i = 0; i < weights.size(); i++) {
            weights.set(i, weights.get(i) + alpha * vector.get(i) * k);
        }

    }

    public void learnAll(List<Vector> list)
    {
        for(Vector v : list)
        {
            this.learn(v);
        }
    }
}
