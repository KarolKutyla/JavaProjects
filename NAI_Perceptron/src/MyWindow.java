import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame {
    private JTextField textField1;
    private JButton button;
    private JLabel label;
    private OutputController controller;
    LanguagePerceptron languagePerceptron;

    public MyWindow(OutputController controller) {
        super("Okno z parametrami");
        this.controller = controller;
        // ustawienie rozmiaru okna
        setSize(400, 200);

        // ustawienie layoutu na GridLayout z 5 wierszami i 1 kolumną
        setLayout(new GridLayout(5, 1));

        // inicjalizacja pól tekstowych i przycisku
        textField1 = new JTextField();
        button = new JButton("Oblicz");
        label = new JLabel();

        // dodanie komponentów do okna
        add(new JLabel("Put text:"));
        add(textField1);
        add(button);
        add(label);

        this.languagePerceptron = languagePerceptron;

        // dodanie akcji do przycisku
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // pobranie wartości z pól tekstowych i ich zamiana na double
                    String param1 = textField1.getText();
                    // wyświetlenie komunikatu z sumą

                        label.setText(controller.whichCountry(new LanguageVector(param1, "")));

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
