package Wagons;

import java.util.Objects;

public abstract class Wagon {

    static private int staticId = 0;
    protected int mass;
    protected final int id = ++staticId;
    private static boolean requiresElectricity;

    public Wagon (int mass)
    {
        this.mass = mass;
    }

    public double getWeight()
    {
        return mass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wagon)) return false;
        Wagon wagon = (Wagon) o;
        return id == wagon.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public boolean isRequiresElectricity()
    {
        return requiresElectricity;
    }

    public int getId()
    {
        return id;
    }

    abstract public String getInfo();
}
