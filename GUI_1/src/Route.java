import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

public class Route {

    public static HashSet<Route> routes = new HashSet<>();

    final static int distanceUnit = 1;
    final TrainStation beginning;
    final TrainStation ending;
    final double distance;
    boolean occupied = false;
    LinkedList<Train> queue = new LinkedList<>();


    private Route(TrainStation beginning, TrainStation ending)
    {
        this.beginning = beginning;
        this.ending = ending;
        distance = distanceUnit * Math.sqrt(Math.pow(beginning.x_cord - ending.x_cord, 2) + Math.pow(beginning.y_cord + ending.y_cord, 2));
    }

    public static Route createRoute(TrainStation beginning, TrainStation ending) throws Exception
    {
        if(beginning.equals(ending)) {
            System.out.println("Nie można stworzyć trasy na tą samą stację.");
            throw new Exception();
        }
        Route route = new Route(beginning, ending);
        if(routes.add(route))
        {
            //System.out.println("Pomyślnie stworzono trasę.");
            beginning.neighbourStations.put(ending, route);
            ending.neighbourStations.put(beginning, route);
            return route;

        }else
        {
            System.out.println("Taka trasa już istnieje.");
            throw new Exception();
        }
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return (beginning.equals(route.beginning) && ending.equals(route.ending)) || (beginning.equals(route.ending) && ending.equals(route.beginning));
    }

    @Override
    public int hashCode() {
        return beginning.hashCode()+ending.hashCode();
    }
}
