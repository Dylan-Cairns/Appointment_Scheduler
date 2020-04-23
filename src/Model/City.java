package Model;

public class City {
    private int cityID;
    private String cityName;
    private Country country;

    public City(int cityID, String cityName, Country country) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.country = country;
    }

    public int getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public Country getCountry() {
        return country;
    }
}
