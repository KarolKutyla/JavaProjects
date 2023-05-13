package Wagons;

public class ToxicLiquidWagon extends ToxicWagon implements LiquidWagonInterface{

    private static final boolean requiresElectricity = false;
    double consistency;
    double liters;

    public ToxicLiquidWagon(int mass, double cargoWeight, int maxCargoWeight, String reservations, String priority, double pollutionIndex, String safetyRules, double consistency, double liters) {
        super(mass, cargoWeight, maxCargoWeight, reservations, priority, pollutionIndex, safetyRules);
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
        return super.getInfo() + " Gęstość ładunku: " + consistency + " Ilość w litrach: " + liters;
    }
}
