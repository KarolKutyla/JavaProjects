import Wagons.Wagon;

import java.util.LinkedList;

public class Train{

    static double acceleration = 3d/100;

    static private int staticId = 1;
    final int id = staticId++;
    final String name;
    final public TrainStation homeStation;
    final public TrainStation destinationStation;
    public TrainStation sourceStation;

    final int maxWagons;
    final int torsion;
    final int maxElectricWagons;
    final LinkedList<Wagon> wagons = new LinkedList<Wagon>();
    int currentElectricity = 0;
    public LinkedList<Route> list;
    public double fullPath;

    public Train(String name, TrainStation homeStation, TrainStation destinationStation, int maxWagons, int torsion, int maxElectricWagons)
    {
        this.name = name;
        this.homeStation = homeStation;
        this.destinationStation = destinationStation;
        this.sourceStation = homeStation;
        this.maxWagons = maxWagons;
        this.torsion = torsion;
        this.maxElectricWagons = maxElectricWagons;
    }


    public  void addWagon(Wagon wagon) throws WagonOverflowException{
        if (torsion > countMass() + wagon.getWeight() && currentElectricity < maxElectricWagons && !wagons.contains(wagon))
            wagons.add(wagon);
        else throw new WagonOverflowException();
    }

    public Wagon removeWagon(int wagonNumber)
    {
        Wagon w = wagons.get(wagonNumber);
        wagons.remove(w);
        if(w.isRequiresElectricity())
            currentElectricity--;
        return w;
    }

    private double countMass()
    {
        double mass = 0;
        for(Wagon w : wagons)
        {
            mass+=w.getWeight();
        }
        return mass;
    }

    public void countFullPath(LinkedList<Route> list)
    {
        double sum = 0;
        for(Route r : list)
        {
            sum+=r.distance;
        }
        fullPath = sum;
    }

    public String printWagons()
    {
        LinkedList<Wagon> tempList = new LinkedList<>(wagons);
        tempList.sort((w1, w2) -> {return (int)(w1.getWeight() - w2.getWeight());});
        StringBuilder builder = new StringBuilder();
        for(Wagon w : tempList)
            builder.append(w.getClass().getTypeName().toString()).append(" o id: ").append(w.getId()).append(" i masie: ").append(w.getWeight()).append(", ");
        return builder.toString();
    }

    public String Detailed()
    {
        LinkedList<Wagon> tempList = new LinkedList<>(wagons);
        tempList.sort((w1, w2) -> {return (int)(w1.getWeight() - w2.getWeight());});
        StringBuilder builder = new StringBuilder();
        for(Wagon w : tempList)
            builder.append(w.getInfo()+", ");
        return builder.toString();
    }
}

class WagonOverflowException extends Exception
{
    @Override
    public void printStackTrace() {
        System.out.println("Przekroczono limit wagonów!");
    }
}
