package Wagons;

public class LiquidWagon extends BasicCargoWagon implements LiquidWagonInterface{

    private static final boolean requiresElectricity = false;
    protected double consistency;
    protected double liters;

    public LiquidWagon(int mass, double cargoWeight, int maxCargoWeight, String cargoDescription, int deliveryNumber, double consistency, double liters)
    {
        super(mass, cargoWeight, maxCargoWeight, cargoDescription, deliveryNumber);
        this.consistency = consistency;
        this.liters = liters;
    }

    @Override
    public double getConsistency() {
        return consistency;
    }

    @Override
    public double getLiters() {
        return liters;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " Gęstość cieczy: " + consistency + " Objętość: " + liters;
    }

}
