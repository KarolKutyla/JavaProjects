package Wagons;

public class GasWagon extends BasicCargoWagon{
    private static final boolean requiresElectricity = false;
    double volume;
    double combustibility;

    public GasWagon(int mass, double cargoWeight, int maxCargoWeight, String cargoDescription, int deliveryNumber, double volume, double combustibility)
    {
        super(mass, cargoWeight, maxCargoWeight, cargoDescription, deliveryNumber);
        this.volume = volume;
        this.combustibility = combustibility;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " Objętość ładunku: " + volume + " Palność: " + combustibility;
    }
}
