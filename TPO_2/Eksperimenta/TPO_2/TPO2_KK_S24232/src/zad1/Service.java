/**
 *
 *  @author Kutyła Karol S24232
 *
 */

package zad1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Service {


    static final HashMap<String, DatabaseLine> cities = new HashMap<>();
    static final HashMap<String, String> countryCurrencyTag = new HashMap<>();
    static final HashSet<String> countries = new HashSet<>();
    String countryName;
    String cityName;
    String currentKey;
    CityInfo info;
    JSONObject parseWeather;
    JSONObject parseCurrency;
    JSONObject parseNBP;
    Set<String> currencySet;
    Double currency;
    String currentRate;


    static {
        try {
            BufferedReader br = new BufferedReader(new FileReader("TPO2_KK_S24232\\src\\zad1\\cities.txt"));
            String line;
            while((line = br.readLine())!=null) {
                DatabaseLine databaseLine = new DatabaseLine(line);
                cities.put(databaseLine.countryName.toUpperCase() + ":" + databaseLine.cityName.toUpperCase(), databaseLine);
                countries.add(databaseLine.cityName);
            }
            BufferedReader b = new BufferedReader(new FileReader("TPO2_KK_S24232\\src\\zad1\\countries.txt"));
            String l;
            while((l = b.readLine())!=null) {
                countryCurrencyTag.put(l.substring(2,4), l.substring(7,l.length()-2));
            }
        }catch (IOException e)
        {
            System.out.println("What exception?");
        }
    }

    Service(String countryName)
    {
        if(!countries.contains(countryName))
        {
            System.out.println("Nie znaleziono kraju");
            System.exit(1);
        }
        this.countryName = countryName;
        //String key = "437300fc6ead4fbca3c586c1e71f9925";
        URL url;

        try {
            String url_str = "https://api.exchangerate.host/latest";
            url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            String str = br.lines().collect(Collectors.joining());
            JSONParser parser = new JSONParser();
            //System.out.println(str);
            JSONObject parse = (JSONObject) parser.parse(str);
            //System.out.println(parse.get("rates").toString());
            parseCurrency = (JSONObject)parse.get("rates");
            currencySet = (Set<String>) parseCurrency.keySet();
        }catch (IOException | ParseException e)
        {

        }

        try {
            String urlStrA = "https://api.nbp.pl/api/exchangerates/tables/A/";
            String urlStrB = "https://api.nbp.pl/api/exchangerates/tables/B/";
            URL urlA = new URL(urlStrA);
            BufferedReader BrA = new BufferedReader(new InputStreamReader(urlA.openConnection().getInputStream()));
            String firstLine = BrA.lines().collect(Collectors.joining());
            BrA.close();

            URL urlB = new URL(urlStrB);
            BufferedReader BrB = new BufferedReader(new InputStreamReader(urlB.openConnection().getInputStream()));
            String secondLine = BrB.lines().collect(Collectors.joining());
            BrB.close();

            JSONParser parser = new JSONParser();
            JSONObject commonJSON = new JSONObject();
            JSONArray tempJsonObject1 = (JSONArray) ((JSONObject) (((JSONArray)parser.parse(firstLine)).get(0))).get("rates");
            for (Object k : tempJsonObject1) {
                JSONObject temp = (JSONObject) k;
                commonJSON.put(temp.get("code"), temp.get("mid"));
            }

            JSONArray tempJsonObject2 = (JSONArray) ((JSONObject) (((JSONArray)parser.parse(secondLine)).get(0))).get("rates");
            for (Object k : tempJsonObject2) {
                JSONObject temp = (JSONObject) k;
                commonJSON.put(temp.get("code"), temp.get("mid"));
            }

            parseNBP = commonJSON;

            //System.out.println(commonJSON);

        }catch (IOException | ParseException e)
        {
            System.out.println("coo?");
        }




    }

    String getWeather(String cityName)
    {
        this.cityName = cityName;

        this.currentKey = countryName.toUpperCase()+":"+this.cityName.toUpperCase();
        System.out.println(currentKey);
        for(String s : cities.keySet())
            System.out.println(s);
        System.out.println(cities.get(currentKey).iso3);
        System.out.println(countryCurrencyTag.get(cities.get(currentKey).iso3));
        this.currency = Double.parseDouble(parseCurrency.get(countryCurrencyTag.get(cities.get(currentKey).iso3)).toString());
        //cities.get(currentKey);
        System.out.println(currentKey);
        String cords = cities.get(currentKey).getCoordinates();
        String urlString = "https://api.openweathermap.org/data/2.5/weather?"+cords+"&appid=437300fc6ead4fbca3c586c1e71f9925";
        System.out.println(urlString);
        URL url;
        try {
            url = new URL(urlString);
            try(
                    InputStream inputStream = url.openConnection().getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));)
            {

                String jsonString = bufferedReader.lines().collect(Collectors.joining());
                JSONParser parser = new JSONParser();
                JSONObject parse = (JSONObject) parser.parse(jsonString);
                info = new CityInfo(parse);
                return parse.toJSONString();

            }catch (IOException | ParseException e)
            {

            }
        }catch (MalformedURLException e)
        {

        }


        return info.weather;
    }

    Double getRateFor(String currency)
    {
        currentRate = currency;
        Double value = (Double)parseCurrency.get(currency);
        System.out.println(currency);
        return value / this.currency;
    }

    Double getNBPRate()
    {
        System.out.println(currentRate);
        return (Double) parseNBP.get(currentRate);
    }
}  
