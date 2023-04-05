package zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyGUI extends JFrame {

    private Service service;

    private JTextField cityTextField;
    private JTextField countryTextField;
    private JTextField currencyTextField;
    private JButton searchButton;
    private JTextArea resultTextArea;

    public MyGUI(Service service) {
        super("Customers of Web Services");
        this.service = service;

        // create components
        cityTextField = new JTextField(20);
        countryTextField = new JTextField(20);
        currencyTextField = new JTextField(20);
        searchButton = new JButton("Search");
        resultTextArea = new JTextArea(20, 40);
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        // create layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Country:"));
        inputPanel.add(new JLabel("City:"));
        inputPanel.add(new JLabel("Currency:"));
        inputPanel.add(countryTextField);
        inputPanel.add(cityTextField);
        inputPanel.add(currencyTextField);
        inputPanel.add(searchButton);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // add action listener for search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //WebView webView = new WebView();
                //WebEngine webEngine = webView.getEngine();
                String city = cityTextField.getText();
                String country = countryTextField.getText();
                service.countryName = country;
                String currency = currencyTextField.getText();
                String weatherJson = service.getWeather(city);
                Double rate1 = service.getRateFor(currency);
                Double rate2 = service.getNBPRate();
                String wikiUrl = "https://en.wikipedia.org/wiki/" + city;
                //webEngine.load(wikiUrl);
                //Scene scene = new Scene(webView, 800, 600);
                //Stage stage = new Stage();
                //stage.setScene(scene);
                //stage.show();
                String wikiHtml = "<html><body><iframe src='" + wikiUrl + "'></iframe></body></html>";
                resultTextArea.setText("Weather JSON:\n" + weatherJson
                        + "\n\nExchange rate for " + currency + ":\n" + rate1
                        + "\n\nNBP rate:\n" + rate2
                        + "\n\nWikipedia page:\n" + wikiHtml);
            }
        });

        // set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
