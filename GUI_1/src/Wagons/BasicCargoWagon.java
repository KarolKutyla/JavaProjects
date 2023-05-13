package Wagons;

public class BasicCargoWagon extends CargoWagon{

    private static final boolean requiresElectricity = false;
    String cargoDescription;
    int deliveryNumber;
    public BasicCargoWagon(int mass, double cargoWeight, int maxCargoWeight, String cargoDescription, int deliveryNumber)
    {
        super(mass, cargoWeight, maxCargoWeight);
        this.cargoDescription = cargoDescription;
        this.deliveryNumber = deliveryNumber;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " Opis ładunku: " + cargoDescription + " Numer dostawy: " + deliveryNumber;
    }





}
