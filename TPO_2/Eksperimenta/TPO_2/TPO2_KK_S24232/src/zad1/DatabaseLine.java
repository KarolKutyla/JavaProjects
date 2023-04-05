package zad1;

public class DatabaseLine {
    final String cityName;
    final float latitude;
    final float longitude;
    final String countryName;
    final String iso3;

    DatabaseLine(String line) {
        String[] arr = line.split(",");
        cityName = arr[0].substring(1, arr[0].length() - 1);
        latitude = (float)(Math.round(Float.parseFloat(arr[1].substring(1, arr[1].length() - 1)) * 100.0) / 100.0);
        longitude = (float)(Math.round(Float.parseFloat(arr[2].substring(1, arr[2].length() - 1)) * 100.0) / 100.0);
        countryName = arr[3].substring(1, arr[3].length() - 1);
        iso3 = arr[4].substring(1, arr[4].length() - 1);
    }

    String getCoordinates() {
        return "lat=" + latitude + "&lon=" + longitude;
    }

}
