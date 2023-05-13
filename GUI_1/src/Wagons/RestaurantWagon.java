package Wagons;

public class RestaurantWagon extends Wagon{

    private static final boolean requiresElectricity = true;

    String menu;
    String schedule;

    public RestaurantWagon(int mass, String menu, String schedule)
    {
        super(mass);
        this.menu = menu;
        this.schedule = schedule;
    }

    @Override
    public String getInfo() {
        return "Wagon " + id + " to wagon " + this.getClass().getTypeName() + ". Dzisiaj w menu: " + menu + "Grafik: " + schedule + ".";
    }


}
