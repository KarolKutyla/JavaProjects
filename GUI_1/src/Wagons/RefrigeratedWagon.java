package Wagons;

public class RefrigeratedWagon extends BasicCargoWagon{

    private static final boolean requiresElectricity = true;
    protected double temperature;
    protected String shipmentDescription;

    public RefrigeratedWagon(int mass, double cargoWeight, int maxCargoWeight, String cargoDescription, int deliveryNumber, double temperature, String shipmentDescription) {
        super(mass, cargoWeight, maxCargoWeight, cargoDescription, deliveryNumber);
        this.temperature = temperature;
        this.shipmentDescription = shipmentDescription;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " Temperatura w środku: " + temperature + " Opis załadunku: " + shipmentDescription;
    }
}
