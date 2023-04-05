package zad1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CityInfo {

    String icon;
    String weather;
    String description;
    float temperature;
    float windSpeed;
    int pressure;
    int windDegree;

    public CityInfo(JSONObject weatherParse)
    {
        passWeather(weatherParse);
    }

    private void passWeather(JSONObject weatherParse)
    {
        icon = mine("weather", "icon", weatherParse);
        weather = mine("weather", "main", weatherParse);
        description = mine("weather", "description", weatherParse);
        temperature = (float) Float.parseFloat(((JSONObject)weatherParse.get("main")).get("temp").toString());
        windSpeed = ((Double)((JSONObject)weatherParse.get("wind")).get("speed")).floatValue();
        pressure = ((Long)((JSONObject)weatherParse.get("main")).get("pressure")).intValue();
        windDegree = ((Long)((JSONObject)weatherParse.get("wind")).get("deg")).intValue();
    }

    private String mine(String first, String second, JSONObject jsonObject)
    {
        return ((JSONObject)((JSONArray)jsonObject.get(first)).get(0)).get(second).toString();
    }




}
