package Wagons;

public abstract class CargoWagon extends Wagon{


    public CargoWagon(int mass, double cargoWeight, int maxCargoWeight)
    {
        super(mass);
        this.cargoWeight = cargoWeight;
        this.maxWeight = maxCargoWeight;
    }

    protected int maxWeight;
    protected double cargoWeight;
    public double getCargoWeight() {
        return cargoWeight;
    }
    public void loadCargo(double weight) {
        if(cargoWeight + weight <= maxWeight)
            cargoWeight += weight;
    }
    public void disposeCargo() {
        cargoWeight = 0;
    }

    @Override
    public double getWeight() {
        return super.getWeight() + cargoWeight;
    }

    @Override
    public String getInfo() {
        return "Wagon " + id + " to wagon " + this.getClass().getTypeName() + ". Masa ładunku: " + cargoWeight + "Maksymalny uciąg: " + maxWeight + ".";
    }
}
