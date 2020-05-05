package Model;

import DAO.AppointmentDAO;
import DAO.CityDAO;
import DAO.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataStorage {
    private static ObservableList<User> userList = FXCollections.observableArrayList();
    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private static ObservableList<City> cityList = FXCollections.observableArrayList();
    private static ObservableList<Customer> customerSearchResults = FXCollections.observableArrayList();
    private static Customer storedCustomer = null;
    private static User storedUser = null;
    private static Appointment storedAppointment = null;
    private static boolean customerAddressesDownloaded;
    private static ObservableList<Appointment> apptList = FXCollections.observableArrayList();

    public static ObservableList<User> getAllUsers() {
        return userList;
    }

    public static ObservableList<Customer> getAllCustomers() {
        if(customerList.isEmpty()) {
            CustomerDAO.getAllCustomers();
        }
        return customerList;
    }

    public static ObservableList<City> getAllCities() {
        if(cityList.isEmpty()) {
            CityDAO.getAllCities();
        }
        return cityList;
    }

    public static ObservableList<Appointment> getAllAppointments() {
        if(apptList.isEmpty()) {
            AppointmentDAO.getAllAppointments();
        }
        return apptList;
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

    public static void addAppointment(Appointment appointment) {
        apptList.add(appointment);
    }

    public static User getStoredUser() {
        return storedUser;
    }

    public static void setStoredUser(User storedUser) {
        DataStorage.storedUser = storedUser;
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

    public static ObservableList<Appointment> lookupCustAppointments(int customerId) {
        ObservableList<Appointment> custAppts = FXCollections.observableArrayList();
        for (Appointment appointment: getAllAppointments()) {
            if (appointment.getCustomer().getCustomerID() == customerId)
                custAppts.add(appointment);
        }
        return custAppts;
    }

    public static void clearCustomerList() {
        customerList.clear();
    }

    public static void clearApptList() {
        apptList.clear();
    }

    public static void clearCityList() {
        cityList.clear();
    }


    public static Customer getStoredCustomer() {
        return storedCustomer;
    }

    public static void setStoredCustomer(Customer storedCustomer) {
        DataStorage.storedCustomer = storedCustomer;
    }

    public static void clearStoredCustomer() {
        DataStorage.storedCustomer = null;
    }

    public static Appointment getStoredAppointment() {
        return storedAppointment;
    }

    public static void setStoredAppointment(Appointment storedAppointment) {
        DataStorage.storedAppointment = storedAppointment;
    }

    public static void clearStoredAppointment() {
        DataStorage.storedAppointment = null;
    }

    public static boolean isCustomerAddressesDownloaded() {
        return customerAddressesDownloaded;
    }

    public static void setCustomerAddressesDownloaded(boolean customerAddressesDownloaded) {
        DataStorage.customerAddressesDownloaded = customerAddressesDownloaded;
    }
}