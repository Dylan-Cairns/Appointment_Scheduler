package model;

public class City {
    private final int cityID;
    private final String cityName;
    private final Country country;

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

    //override tostring method so that combobox displays city names in edit menu
    @Override
    public String toString() {
        return this.getCityName();
    }
}
