public class Vector extends java.util.Vector<Double> {

    public Vector()
    {
        super();
    }

    public Vector(Integer... arr)
    {
        for(Integer i : arr)
        {
            this.add((double)i);
        }
    }

    public Vector(Double... arr)
    {
        for(Double d : arr)
        {
            this.add(d);
        }
    }
    public void addVector(Vector vector) throws Exception {
        if(this.size() == vector.size())
        {
            for(int i = 0; i < this.size(); i++)
            {
                this.set(i, this.get(i)+vector.get(i));
            }
        }else
        {
            throw new SizesNotTheSameException();
        }
    }
    public void resultant(Vector vector) throws Exception {
        if(this.size() == vector.size())
        {
            for(int i = 0; i < this.size(); i++)
            {
                this.set(i, (this.get(i)+vector.get(i))/2);
            }
        }else
        {
            throw new SizesNotTheSameException();
        }
    }
}
