import Wagons.HeavyCargoWagon;
import Wagons.Wagon;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    final static int xCordSize = 1000;
    final static int yCordSize = 1000;

    public static void main(String[] args) {

        //Arrays.stream(locales).forEach((x)->{x.toString();});
        Generator.generateSim();
        TrainThread.begin();
    }

}
