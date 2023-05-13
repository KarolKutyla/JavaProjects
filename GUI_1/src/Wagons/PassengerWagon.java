package Wagons;

import Wagons.Wagon;

public class PassengerWagon extends Wagon {
    private static final boolean requiresElectricity = true;

    int passengers;
    double ticketPrice;

    public PassengerWagon(int mass, int passengers, double ticketPrice)
    {
        super(mass);
        this.passengers = passengers;
        this.ticketPrice = ticketPrice;
    }

    @Override
    public double getWeight() {

        return super.getWeight()+70*passengers;
    }

    @Override
    public String getInfo() {
        return "Wagon " + id + " to wagon " + this.getClass().getTypeName() + ". Liczba pasażerów: " + passengers + "Cena biletu: " + ticketPrice + ".";
    }
}
