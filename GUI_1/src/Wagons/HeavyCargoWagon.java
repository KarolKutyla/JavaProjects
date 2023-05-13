package Wagons;

public class HeavyCargoWagon extends CargoWagon{

    private static final boolean requiresElectricity = false;
    String reservations;
    String priority;
    public HeavyCargoWagon(int mass, double cargoWeight, int maxCargoWeight, String reservations, String priority)
    {
        super(mass, cargoWeight, maxCargoWeight);
        this.reservations = reservations;
        this.priority = priority;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " Restrykcje: " + reservations + " Priorytet: " + priority;
    }


}
