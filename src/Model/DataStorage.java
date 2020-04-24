package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataStorage {
    private static ObservableList<User> userList = FXCollections.observableArrayList();
    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private static ObservableList<Customer> customerSearchResults = FXCollections.observableArrayList();

    public static ObservableList<User> getAllUsers() {
        return userList;
    }

    public static ObservableList<Customer> getAllCustomers() {
        return customerList;
    }

    public static void addUser(User user) {
        userList.add(user);
    }

    public static void addCustomer(Customer customer) {
        customerList.add(customer);
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
}