import javafx.util.Pair;

import java.security.Key;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class TrainStation {

    public static final HashSet<TrainStation> stations = new HashSet<>();
    public static final TrainStation[][] mapOfStations = new TrainStation[Main.yCordSize+1][Main.xCordSize+1];

    String name;
    int x_cord;
    int y_cord;
    boolean hasConnection = false;
    boolean visited = false;
    final public HashMap<TrainStation, Route> neighbourStations = new HashMap<>();

    private TrainStation(int x_cord, int y_cord, String name)
    {
        this.name = name;
        this.x_cord = x_cord;
        this.y_cord = y_cord;
    }

    public static TrainStation createStation(int x_cord, int y_cord, String name) throws StationExistsException
    {
        TrainStation trainStation = new TrainStation(x_cord, y_cord, name);
        if(stations.add(trainStation)) {
            mapOfStations[y_cord][x_cord] = trainStation;
//            System.out.println("Pomyślnie dodano nową stację.");
            return trainStation;
        }
        else
        {
            System.out.println("Anulowano duplikat istniejącej stacji.");
            throw new StationExistsException();
        }
    }

//    public void addNeighbour(Route route)
//    {
//        if(route.ending.equals(this))
//            neighbourStations.put(route.ending, route);
//        else if(route.beginning.equals(this))
//            neighbourStations.put(route.beginning, route);
//    }

    public void addNeighbour(TrainStation station)
    {
        try {
            if(!station.equals(this))
            {
                Route r = Route.createRoute(this, station);
//                neighbourStations.put(station, r);
            }else
            {
                System.out.println("Nie można dodać takiej samej stacji.");
            }
        }catch (Exception e)
        {
            System.out.println("Wystąpił błąd przy dodawaniu połączenia pomiędzy trasami.");
        }

    }

    public void addNearestStation()
    {
        int distance = 2000000;
        TrainStation station = null;
        for(int y = 0; y < mapOfStations.length; y++)
        {
            for(int x = 0; x < mapOfStations[y].length; x++)
            {
                if (mapOfStations[y][x] != null && !mapOfStations[y][x].equals(this) && !neighbourStations.containsKey(mapOfStations[y][x]) && distance > Math.abs(y-y_cord)+Math.abs(x-x_cord))
                {
                    station = mapOfStations[y][x];
                    distance = Math.abs(y-y_cord) + Math.abs(x-x_cord);
                }
            }
        }
//        System.out.println(x_cord + " " + y_cord + ", " + station.x_cord + " " + station.y_cord);
        if(station!=null) {
            addNeighbour(station);

        }
    }

    public boolean connect()
    {
        for(TrainStation t : neighbourStations.keySet())
        {
            if(t.hasConnection) {
                this.hasConnection = true;
                break;
            }
        }
        return hasConnection;
    }

    synchronized static public LinkedList<Route> findPath(TrainStation beginning, TrainStation destination)
    {
        stations.forEach((x) -> {x.visited = false;});
        LinkedList<Route> list = new LinkedList<>();
        beginning.findPathIteration(destination, list);
        return list;
    }

    private boolean findPathIteration(TrainStation destination, LinkedList<Route> list)
    {
        this.visited = true;
        LinkedList<TrainStation> queue = new LinkedList<>(neighbourStations.keySet());
        queue.sort((first, second)->{return Math.abs(destination.y_cord-first.y_cord) + Math.abs(destination.x_cord-first.x_cord) - (Math.abs(destination.y_cord-second.y_cord) + Math.abs(destination.x_cord-second.x_cord));});
        for(TrainStation s : queue)
        {
            if(!s.visited) {
                list.add(neighbourStations.get(s));
                if (s.equals(destination)) {
//                    System.out.println("Znalazłem");
                    return true;
                } else {
                    boolean result = s.findPathIteration(destination, list);
                    if (result) {
                        return true;
                    } else {
                        list.removeLast();
                    }
                }
            }
        }
        return false;
    }

    private int countDistance(TrainStation station)
    {
        return Math.abs(station.y_cord-this.y_cord) + Math.abs(station.x_cord-this.x_cord);
    }


    @Override
    public int hashCode() {
        return x_cord * 1000 + y_cord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainStation)) return false;
        TrainStation that = (TrainStation) o;
        return x_cord == that.x_cord && y_cord == that.y_cord;
    }
}

class StationExistsException extends Exception
{
    @Override
    public void printStackTrace() {
        System.out.println("Taka stacja już istnieje.");
    }
}


