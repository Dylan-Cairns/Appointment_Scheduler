package model;

import dataAccessObjects.AppointmentDAO;
import dataAccessObjects.CityDAO;
import dataAccessObjects.CustomerDAO;
import dataAccessObjects.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class DataStorage {
    private static final ObservableList<User> userList = FXCollections.observableArrayList();
    private static final ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private static final ObservableList<City> cityList = FXCollections.observableArrayList();
    private static Customer storedCustomer = null;
    private static User storedUser = null;
    private static Appointment storedAppointment = null;
    private static boolean customerAddressesDownloaded;
    private static final ObservableList<Appointment> apptList = FXCollections.observableArrayList();

    public static ObservableList<User> getAllUsers() {
        if(userList.isEmpty()) {
            UserDAO.getAllUsers();
        }
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

    public static ObservableList<Appointment> getApptsThisWeek() {
        ObservableList<Appointment> apptsThisWeek = FXCollections.observableArrayList();
        LocalDateTime oneWeekFromNow = LocalDateTime.now().plusDays(7);
        for(Appointment appointment: getAllAppointments()) {
            if (appointment.getStartTime().isAfter(LocalDateTime.now()) && appointment.getStartTime().isBefore(oneWeekFromNow)) {
                apptsThisWeek.add(appointment);
            }
        }
        return apptsThisWeek;
    }

    public static ObservableList<Appointment> getApptsThisMonth() {
        ObservableList<Appointment> apptsThisMonth = FXCollections.observableArrayList();
        LocalDateTime oneMonthFromNow = LocalDateTime.now().plusMonths(1);
        for(Appointment appointment: getAllAppointments()){
            if (appointment.getStartTime().isAfter(LocalDateTime.now()) && appointment.getStartTime().isBefore(oneMonthFromNow)) {
                apptsThisMonth.add(appointment);
            }
        }
        return apptsThisMonth;
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

    public static ObservableList<Customer> lookupCustomer(String searchName) {
        ObservableList<Customer> customerSearchResults = FXCollections.observableArrayList();
        if (searchName.equals("")) {
            return getAllCustomers();
        }
        for (Customer customer : getAllCustomers()) {
            if (customer.getCustomerName().toLowerCase().contains(searchName.toLowerCase())) {
                customerSearchResults.add(customer);
            }
        }
        return customerSearchResults;
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

    public static boolean areCustomerAddressesDownloaded() {
        return customerAddressesDownloaded;
    }

    public static void setCustomerAddressesDownloaded(boolean customerAddressesDownloaded) {
        DataStorage.customerAddressesDownloaded = customerAddressesDownloaded;
    }
}