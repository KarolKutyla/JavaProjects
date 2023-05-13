package Wagons;

public class ToxicWagon extends HeavyCargoWagon{
    private static final boolean requiresElectricity = false;
    double pollutionIndex;
    String safetyRules;

    public ToxicWagon(int mass, double cargoWeight, int maxCargoWeight, String reservations, String priority, double pollutionIndex, String safetyRules) {
        super(mass, cargoWeight, maxCargoWeight, reservations, priority);
        this.pollutionIndex = pollutionIndex;
        this.safetyRules = safetyRules;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " Wskaźnik zanieczyszczenia: " + pollutionIndex + " Zasady bezpieczeństwa: " + safetyRules;
    }
}
