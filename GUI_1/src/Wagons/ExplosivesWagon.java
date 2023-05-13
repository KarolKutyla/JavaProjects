package Wagons;

public class ExplosivesWagon extends HeavyCargoWagon{
    private static final boolean requiresElectricity = false;
    double threatLevel;
    String safetyRules;
    public ExplosivesWagon(int mass, double cargoWeight, int maxCargoWeight, String reservations, String priority, double threatLevel, String safetyRules) {
        super(mass, cargoWeight, maxCargoWeight, reservations, priority);
        this.threatLevel = threatLevel;
        this.safetyRules = safetyRules;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " Poziom zagrożenia: " + threatLevel + " Zasady bezpieczeństwa: " + safetyRules;
    }
}
