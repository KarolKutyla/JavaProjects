import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Presentation {
    public static void main(String[] args) {
        /** Test generowania pociągów i wagonów. Program generuje także stacje i trasy, ale nie program ich nie wyświetla. Używane są do obliczeń
         * Wywołam cały generator dla uproszczenia. W tej metodzie otrzymamy tylko jeden pociąg na którym będą prezętowane funkcjonalności.*/
        Generator.generateTestingSim();
        System.out.println("Wyświetlamy pociąg z symulacji: ");
        Train train = TrainThread.getTestingTrain();
        System.out.println("Adres: " + train);
        System.out.println("Wypisanie informacji: " + train.Detailed());
        try
        {
            System.out.println("Pobierzmy wagon: " + train.wagons.get(0).getInfo());
        }catch (IndexOutOfBoundsException e)
        {
            System.out.println("Lista wagonów jest pusta.");
        }
        /** Wyszukiwanie ścieżki opiera się na algorytmie DFS który stara się wybierać stacje które znajdują się geograficznie najbliżejs tacji docelowej.
         * Spróbujmy zobaczyć ścieżkę jaka nam się wygeneruje.*/


        List<Route> tempList = TrainStation.findPath(train.homeStation, train.destinationStation);

        System.out.println("Pierwsza stacja: " + train.homeStation.name + " X: " + train.homeStation.x_cord + " Y:" + train.homeStation.y_cord);
        System.out.println("Ostatnia stacja: " + train.destinationStation.name + " X: " + train.destinationStation.x_cord + " Y:" + train.destinationStation.y_cord);
        TrainStation current = train.homeStation;
        System.out.println("Kolejno:");
        for(Route r : tempList) {
            if(r.ending.equals(current))
                current = r.beginning;
            else current = r.ending;
            System.out.println("Stacja: " + current.name + " X: " + current.x_cord + " Y:" + current.y_cord);
        }



        /** Wszystko zostało wygenerowane pseudolosowo. Prezentuje działanie od razu na wygenerowanej symulacji, ponieważ konstruktor pociągu wymaga podania istniejących stacji.
         * Większość konstruktorów to metody prywatne stąd brak możliwości przedstawienia poszczególnych funkcjonalności bez utworzenia symulacji.
         * Zapis do pliku odbuwa się automatycznie, i znajduje się wewnątrz innego wątku który w tym miejscu w kodzie podczas wykonania już pracuje.
         * Sprawdźmy czy pociąg faktycznie się przemieszcza.*/
        try
        {
            TrainThread.begin();
            System.out.println("Stacja początkowa: " + train.homeStation.name);
            System.out.println("Stacja końcowa: " + train.destinationStation.name);
            System.out.println("Stacja z której właśnie odjechał/aktualnie jest: " + train.sourceStation.name);
            Thread.sleep(1000);
            TrainThread.setCommand("print 1");
            Thread.sleep(2000);
            TrainThread.setCommand("print 1");
            System.out.println("Jak można zauważyć pociąg się przemieszcza.");
        }catch (Exception e)
        {

        }







    }
}
