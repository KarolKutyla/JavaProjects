import Wagons.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Random;

public class Generator {

    static int trainNumber = 0;
    static LinkedList<TrainStation> startingStations = new LinkedList<>();
    static LinkedList<TrainStation> endingStations = new LinkedList<>();
    public static void generateSim()
    {
        generateRails();
        generateComposition();
    }

    public static void generateTestingSim()
    {
        generateTestingRails();
        generateTestingComposition();
    }

    private static void generateRails()
    {
        int i = 0;
        Locale[] locales = Locale.getAvailableLocales();
        while (TrainStation.stations.size() < 100)
        {
            try {
                TrainStation s = TrainStation.createStation((int)(Math.random()*(Main.xCordSize+1)), (int)(Math.random()*(Main.yCordSize+1)), locales[(i)%100].toString());
                if(i%4 == 0)
                    startingStations.add(s);
                if(i%4 == 2)
                    endingStations.add(s);
                i++;
            }catch (Exception e)
            {}
        }
        for (TrainStation trainStation : TrainStation.stations)
        {
            trainStation.addNearestStation();
            trainStation.addNearestStation();
        }
        i = 0;
        ArrayList<TrainStation> tempList = new ArrayList<>(TrainStation.stations);
        tempList.get(0).hasConnection=true;
        tempList.remove(0);
        while (!tempList.isEmpty())
        {
            for(TrainStation station : tempList) {
                if(station.connect())
                {
                    i = 0;
                }
            }
            tempList.removeIf((t) -> t.hasConnection);
            i++;
            if(i >= 10)
            {
                for(TrainStation t : tempList)
                {
                    t.addNearestStation();
                }
                i = 0;
            }
        }
    }

    private static void generateTestingRails()
    {
        int i = 0;
        Locale[] locales = Locale.getAvailableLocales();
        while (TrainStation.stations.size() < 10)
        {
            try {
                TrainStation s = TrainStation.createStation((int)(Math.random()*(Main.xCordSize+1)), (int)(Math.random()*(Main.yCordSize+1)), locales[(i)%100].toString());
                if(i%4 == 0)
                    startingStations.add(s);
                if(i%4 == 2)
                    endingStations.add(s);
                i++;
            }catch (Exception e)
            {}
        }
        for (TrainStation trainStation : TrainStation.stations)
        {
            trainStation.addNearestStation();
            trainStation.addNearestStation();
        }
        i = 0;
        ArrayList<TrainStation> tempList = new ArrayList<>(TrainStation.stations);
        tempList.get(0).hasConnection=true;
        tempList.remove(0);
        while (!tempList.isEmpty())
        {
            for(TrainStation station : tempList) {
                if(station.connect())
                {
                    i = 0;
                }
            }
            tempList.removeIf((t) -> t.hasConnection);
            i++;
            if(i >= 10)
            {
                for(TrainStation t : tempList)
                {
                    t.addNearestStation();
                }
                i = 0;
            }
        }
    }

    private static void generateComposition()
    {
        for(int i = 0; i < 25; i++)
        {
            Train t = generateTrain();
            while (t.wagons.size() < t.maxWagons)
            {
                try {
                    Wagon tempWagon = generateWagon();
                    t.addWagon(tempWagon);
                }catch (WagonOverflowException e)
                {
                    break;
                }catch (Exception e)
                {}
            }
            new TrainThread(t).start();
        }

    }

    private static void generateTestingComposition()
    {
        for(int i = 0; i < 1; i++)
        {
            Train t = generateTrain();
            while (t.wagons.size() < t.maxWagons)
            {
                try {
                    Wagon tempWagon = generateWagon();
                    t.addWagon(tempWagon);
                }catch (WagonOverflowException e)
                {
                    break;
                }catch (Exception e)
                {}
            }
            new TrainThread(t).start();
        }

    }

    private static Train generateTrain()
    {
        int val = trainNumber++;
        int maxWagons = (int)(Math.random()*6+5);
        return new Train("XM:"+(val+1), startingStations.get(val), endingStations.get(val), maxWagons, (int)(Math.random()*100000+25000),(int)(Math.random()*(maxWagons+1)));
    }

    private static Wagon generateWagon() throws Exception
    {
        WagonEnum[] values = WagonEnum.values();
        int val = (int)(Math.random()*values.length);
        Random random = new Random();
        switch (values[val])
        {
            case PassengerWagon:
                return new PassengerWagon(10000, random.nextInt(51)+50, Math.random()*5+3);
            case MailWagon:
                return new MailWagon(4000,Math.random()*1000+500, Math.random()*1000+500);
            case BaggageAndMailWagon:
                return new BaggageAndMailWagon(3000,random.nextDouble()%1000+500, random.nextDouble()%1000+500, Math.random()*3000+1000);
            case RestaurantWagon:
                return new RestaurantWagon(3000,"MenuExample", "ScheduleExample");
            case BasicCargoWagon:
                return new BasicCargoWagon(6000,Math.random()*8000, 80000, "ExampleDescription", 10);
            case HeavyCargoWagon:
                return new HeavyCargoWagon(8000,Math.random()*15000, 15000, "ExampleReservation", "ExamplePriorityDescription");
            case RefrigeratedWagon:
                return new RefrigeratedWagon(6000,Math.random()*15000, 15000, "ExampleDescription", 11, Math.random()*10-20, "ExampleDescription");
            case LiquidWagon:
                return new LiquidWagon(5000,Math.random()*15000, 15000, "ExampleDescription", 11, Math.random()*3, Math.random()*500+300);
            case GasWagon:
                return new GasWagon(4000,Math.random()*15000, 15000, "ExampleDescription", 11, Math.random()*2000+1000, Math.random()*10);
            case ExplosivesWagon:
                return new ExplosivesWagon(10000,Math.random()*15000, 15000, "ExampleReservation", "ExamplePriorityDescription", random.nextInt(10), "ExampleSafetyRules");
            case ToxicWagon:
                return new ToxicWagon(9000,Math.random()*15000, 15000, "ExampleReservation", "ExamplePriorityDescription", random.nextInt(10), "ExampleSafetyRules");
            case ToxicLiquidWagon:
                return new ToxicLiquidWagon(9500, Math.random()*15000, 15000, "ExampleReservation", "ExamplePriorityDescription", random.nextInt(10), "ExampleSafetyRules", random.nextDouble()*3, random.nextDouble()*500+300);
        }
        throw new Exception();
    }

    enum WagonEnum
    {
        PassengerWagon, MailWagon, BaggageAndMailWagon, RestaurantWagon, BasicCargoWagon, HeavyCargoWagon,
        RefrigeratedWagon, LiquidWagon, GasWagon, ExplosivesWagon, ToxicWagon, ToxicLiquidWagon;
    }


}
