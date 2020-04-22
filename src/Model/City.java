package Model;

public class City {
    private int cityID;
    private String cityName;
    private int countryID;

    public City(int cityID, String cityName, int countryID) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.countryID = countryID;
    }

    public int getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public int getCountryID() {
        return countryID;
    }
}
