package Wagons;

public class MailWagon extends Wagon{

    private static final boolean requiresElectricity = true;
    protected double mailWeight;
    protected double mailValue;

    public MailWagon(int mass, double mailWeight, double mailValue)
    {
        super(mass);
        this.mailWeight = mailWeight;
        this.mailValue = mailValue;
    }


    @Override
    public double getWeight() {
        return super.getWeight() + mailWeight;
    }

    @Override
    public String getInfo() {
        return "Wagon " + id + " to wagon " + this.getClass().getTypeName() + ". Masa ładunku: " + mailWeight + "Wartość ładunku: " + mailValue + ".";
    }
}
