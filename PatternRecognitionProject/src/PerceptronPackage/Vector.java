package PerceptronPackage;

public class Vector extends java.util.Vector<Double> {

    String name;
    public Vector(String name)
    {
        this.name = name;
    }
    public void sum(Vector v)
    {
        for(int i = 0; i < v.size(); i++)
        {
            this.set(i, this.get(i)+v.get(i));
        }
    }
}
