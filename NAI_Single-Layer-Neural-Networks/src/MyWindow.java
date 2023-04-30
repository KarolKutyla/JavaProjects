import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MyWindow extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton button;
    private JLabel label;
    Perceptron perceptron;

    public MyWindow(Perceptron perceptron) {
        super("Okno z parametrami");

        // ustawienie rozmiaru okna
        setSize(400, 200);

        // ustawienie layoutu na GridLayout z 5 wierszami i 1 kolumną
        setLayout(new GridLayout(5, 1));

        // inicjalizacja pól tekstowych i przycisku
        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        textField4 = new JTextField();
        button = new JButton("Oblicz");
        label = new JLabel();

        // dodanie komponentów do okna
        add(new JLabel("Parametr 1:"));
        add(textField1);
        add(new JLabel("Parametr 2:"));
        add(textField2);
        add(new JLabel("Parametr 3:"));
        add(textField3);
        add(new JLabel("Parametr 4:"));
        add(textField4);
        add(button);
        add(label);

        this.perceptron = perceptron;

        // dodanie akcji do przycisku
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // pobranie wartości z pól tekstowych i ich zamiana na double
                    double param1 = Double.parseDouble(textField1.getText());
                    double param2 = Double.parseDouble(textField2.getText());
                    double param3 = Double.parseDouble(textField3.getText());
                    double param4 = Double.parseDouble(textField4.getText());
                    MyVector vector = new MyVector(param1, param2, param3, param4);
                    // wyświetlenie komunikatu z sumą parametrów
                    boolean result = perceptron.count(vector);
                    if(result)
                    {
                        label.setText("Wynik to: Iris-setosa.");

                    }else
                    {
                        label.setText("Wynik to: Iris-versicolor.");
                    }

                } catch (NumberFormatException ex) {
                    // obsługa błędów - wyświetlenie komunikatu o błędnych danych wejściowych
                    label.setText("Błędne dane wejściowe!");
                }
            }
        });

        // wyśrodkowanie okna na ekranie i jego wyświetlenie
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
