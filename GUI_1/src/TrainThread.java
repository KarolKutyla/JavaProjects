import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class TrainThread extends Thread {

    static private LinkedList<TrainThread> threads = new LinkedList<>();

    private Train train;
    private boolean isReady = false;
    private boolean hasFinished = true;
    static int printComposition = -1;
    static Scanner scr = new Scanner(System.in);

    private double distance;
    private double travelledDistance;
    private double speed = 50;
    private Action action = Action.planning;
    private Route currentRout;
    private int ticks;
    private double allRoad = 0;
    private double currentRoad = 0;
    private static String commandLine = null;

    public TrainThread(Train train) {
        this.train = train;
        threads.add(this);
    }

    public static void begin() {
        threads.sort(Comparator.comparingInt(t -> t.train.id));
        Thread thread = new Thread(() -> {
            int tick = 0;
            while (true) {
                boolean threadFlag = true;
                while (threadFlag) {
                    threadFlag = false;
                    for (TrainThread t : threads) {
                        if (t.isReady || !t.hasFinished) {
                            threadFlag = true;
                            break;
                        }
                    }
                }
                if(tick >= 5)
                {
                    try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File("AppState.txt")))) {
                        LinkedList<TrainThread> tempList = new LinkedList<>(threads);
                        tempList.sort((t1, t2)-> {return (int)(t1.allRoad - t2.allRoad);});
                        for(TrainThread t : tempList)
                        bw.write("Pociąg o nazwie " + t.train.name + " na trasie " + t.train.homeStation.name + " - " + t.train.destinationStation.name + " i uciągu " + t.train.torsion + " ma przyłączone następujące wagony: " + t.train.printWagons() + ".\n");
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    tick=0;
                }else
                {
                    tick++;
                }
                if(commandLine != null) {
                    if (commandLine.matches("print [0-9]*")) {
                        try {
                            int id = Integer.parseInt(commandLine.split(" ")[1]) - 1;
                            threads.get(id).printInfo();
                        } catch (Exception e) {
                        }
                    }
                    commandLine = null;
                }
                if(printComposition!=-1)
                {

                    printComposition =-1;
                }
                threads.forEach((t) -> {
                    t.isReady = true;
                });
                threads.forEach((t) -> {
                    t.hasFinished = false;
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        startReading();
    }

    private static void startReading()
    {
        new Thread(()->{
            while (true)
            {commandLine = scr.nextLine();}}).start();
    }

    private double remainingRoad()
    {
        return allRoad - currentRoad - travelledDistance;
    }

    private double remainingRoadPercent()
    {
        return (currentRoad + travelledDistance)/allRoad * 100;
    }

    private double remainingShortRoadPercent()
    {
        return (travelledDistance / distance) * 100;
    }


    synchronized public void move() {
        travelledDistance += speed;
//        System.out.println(train.name + ": " + travelledDistance);
        if (((int)(Math.random() * 100) % 2) == 0) {
            speed *= 103d / 100;
        }
        else {
            speed *= 97d / 100;
        }
    }


    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (isReady && !hasFinished) {

                    isReady = false;
                    switch (action) {
                        case planning:
                            currentRoad = 0;
                            if (train.sourceStation.equals(train.homeStation)) {
                                train.list = TrainStation.findPath(train.homeStation, train.destinationStation);
                            } else if (train.sourceStation.equals(train.destinationStation)) {
                                train.list = TrainStation.findPath(train.destinationStation, train.homeStation);
                            }
                            LinkedList<Route> tempList = new LinkedList<Route>(train.list);
                            allRoad = 0;
                            for(Route r : tempList)
                                allRoad+=r.distance;
                            train.countFullPath(train.list);
                            currentRout = train.list.removeFirst();
                            travelledDistance = 0;
                            distance = currentRout.distance;
                            speed = 50;
                            action = Action.moving;
                        case moving:
                            if (travelledDistance >= distance) {
                                currentRoad += distance;
//                                System.out.println("Dojechałem");
                                ticks = 1;
                                if (train.sourceStation.equals(currentRout.beginning)) {
                                    train.sourceStation = currentRout.ending;
                                } else if (train.sourceStation.equals(currentRout.ending)) {
                                    train.sourceStation = currentRout.beginning;
                                }
                                if (train.sourceStation.equals(train.destinationStation) || train.sourceStation.equals(train.homeStation)) {
                                    action = Action.longWaiting;
                                    break;
                                } else {
                                    action = Action.shortWaiting;
                                    break;
                                }
                            } else {
//                                System.out.println(distance);
                                move();
                                break;
                            }
                        case shortWaiting:
                            if (ticks >= 2) {
                                currentRout = train.list.removeFirst();
                                travelledDistance = 0;
                                distance = currentRout.distance;
                                speed = 50;
                                action = Action.moving;
                            } else {
                                ticks++;
                            }
                            break;
                        case longWaiting:
                            if (ticks >= 30) {
                                action = Action.planning;
                            } else {
                                ticks++;
                            }
                            break;

                    }
                    hasFinished = true;
                }

            }
        }
    }

    public void printInfo()
    {
        System.out.println("Pociąg o nazwie " + train.name + " na trasie " + train.homeStation.name + " - " + train.destinationStation.name + " i uciągu " + train.torsion + " ma przyłączone następujące wagony: " + train.printWagons() + ".");
        System.out.println("% ukończonej drogi pomiędzy stacją startową i docelową: " + remainingRoadPercent());
        System.out.println(train.Detailed());
        System.out.println("% ukończonej drogi pomiędzy najbliższymi stacjami kolejowymi na swojej trasie wynosi: " + remainingShortRoadPercent());
    }

    public enum Action {
        shortWaiting, longWaiting, moving, waiting, preparing, planning, returning;
    }

    static LinkedList<TrainThread> getThreads()
    {
        return threads;
    }

    static Train getTestingTrain()
    {
        return threads.get(0).train;
    }

    public static void setCommand(String command)
    {
        commandLine = command;
    }
}


