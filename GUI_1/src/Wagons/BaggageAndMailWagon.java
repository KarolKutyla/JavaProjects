package Wagons;

public class BaggageAndMailWagon extends MailWagon{

    private static final boolean requiresElectricity = false;
    protected double luggageWeight;

    public BaggageAndMailWagon(int mass, double mailWeight, double mailValue, double luggageWeight) {
        super(mass, mailWeight, mailValue);
        this.luggageWeight = luggageWeight;
    }

    @Override
    public double getWeight() {
        return super.getWeight() + mailWeight+luggageWeight;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " Waga bagaży wynosi:" + luggageWeight;
    }
}
