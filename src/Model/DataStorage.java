package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataStorage {
    private static ObservableList<User> userList = FXCollections.observableArrayList();
    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private static ObservableList<City> cityList = FXCollections.observableArrayList();
    private static ObservableList<Country> countryList = FXCollections.observableArrayList();
    private static ObservableList<Customer> customerSearchResults = FXCollections.observableArrayList();
    private static Customer customerToSave = null;

    public static ObservableList<User> getAllUsers() {
        return userList;
    }

    public static ObservableList<Customer> getAllCustomers() {
        return customerList;
    }

    public static ObservableList<City> getAllCities() { return cityList; }

    public static ObservableList<Country> getAllCountries() {
        return countryList;
    }

    public static void addUser(User user) {
        userList.add(user);
    }

    public static void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    public static void addCity(City city) {
        cityList.add(city);
    }

    public static void addCountry(Country country) {
        countryList.add(country);
    }

    public static User lookupUser(int userID) {
        for (User user : getAllUsers()) {
            if (user.getUserID() == userID)
                return user;
        }
        return null;
    }

    //this is a strict lookup using .equals because we are checking the username in order to login
    public static User lookupUser(String userName) {
        for (User user : getAllUsers()) {
            if (user.getUserName().equalsIgnoreCase(userName)) {
                return user;
            }
        }
        return null;
    }

    public static ObservableList<Customer> lookupCustomer(String customerName) {
        if (customerName == "") {
            return getAllCustomers();
        }
        if (!(customerSearchResults.isEmpty())) ; // if list is not empty, make it empty
        {
            customerSearchResults.clear();
        }
        for (Customer customer : getAllCustomers()) {
            if (customer.getCustomerName().contains(customerName)) {
                customerSearchResults.add(customer);
            }
        }
        return customerSearchResults;
    }

    //find the index of a city in the cityList
    public static int lookupCityIndex(City searchCity) {
        for (City city: getAllCities()) {
            if (city.getCityID() == (searchCity.getCityID())) {
                return getAllCities().indexOf(city);
            }
        }
        return -1;
    }

    public static void emptyStoredData() {
        customerList.clear();
        cityList.clear();
        countryList.clear();
    }

    public static Customer getCustomerToSave() {
        return customerToSave;
    }

    public static void setCustomerToSave(Customer customerToSave) {
        DataStorage.customerToSave = customerToSave;
    }

    public static void clearCustomerToSave() {
        DataStorage.customerToSave = null;
    }
}