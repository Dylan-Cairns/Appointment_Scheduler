package DAO;

import Model.*;
import Utils.DBConnection;
import Utils.DBQuery;

import java.sql.*;
import java.time.LocalDateTime;

public class CustomerDAO {

    public static Customer getCustomerWithAddress(int customerID) {
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT customerId, customerName, addressId FROM U06NwI.customer WHERE customerId = ?";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Set value cityName to be passed to function in select statement
            ps.setInt(1, customerID);
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Check there is an entry, if so, return entry
            if(rs.next()) {
                String customerName=rs.getString("customerName");
                int addressID=rs.getInt("addressId");
                Address address = AddressDAO.getAddressByID(addressID);
                Customer returnedCustomer = new Customer(customerID, customerName, address);
                rs.close();
                return returnedCustomer;
            }
            else {
                System.out.println("No matching customer found");
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static Customer getCustomer(int customerID) {
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT customerId, customerName, addressId FROM U06NwI.customer WHERE customerId = ?";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Set value cityName to be passed to function in select statement
            ps.setInt(1, customerID);
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Check there is an entry, if so, return entry
            if(rs.next()) {
                String customerName=rs.getString("customerName");
                int addressID=rs.getInt("addressId");
                Customer returnedCustomer = new Customer(customerID, customerName);
                rs.close();
                return returnedCustomer;
            }
            else {
                System.out.println("No matching customer found");
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void getAllCustomerswithAddress() {
        try {
            //Clear the old entries from the customer list before adding new entries from database
            DataStorage.clearCustomerList();
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT customerId FROM U06NwI.customer";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Create user objects from results, save in an observable list
            while (rs.next()) {
                int customerID=rs.getInt("customerId");
                Customer customer = getCustomerWithAddress(customerID);
                if(customer != null) {
                    //add customer to customer list
                    DataStorage.addCustomer(customer);
                }
            }
            rs.close();
            DataStorage.setCustomerAddressesDownloaded(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void getAllCustomers() {
        try {
            //Clear the old entries from the customer list before adding new entries from database
            DataStorage.clearCustomerList();
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT customerId FROM U06NwI.customer";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Create user objects from results, save in an observable list
            while (rs.next()) {
                int customerID=rs.getInt("customerId");
                Customer customer = getCustomer(customerID);
                if(customer != null) {
                    //add customer to customer list
                    DataStorage.addCustomer(customer);
                }
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static boolean addNewCustomer(Customer customer) {
        //call AddressDAO to insert the address into the DB and return it's addressId
        int addressId = AddressDAO.addNewAddress(customer.getAddress());
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String insertStatement = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, insertStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, customer.getCustomerName());
            ps.setInt(2, addressId);
            ps.setInt(3, 1);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(5, "test");
            ps.setString(6, LocalDateTime.now().toString());
            ps.setString(7, "test");
            //Saver results into result set
            //Check save was successful
            int res = ps.executeUpdate();

            if (res == 1) {//one row was affected; namely the one that was inserted!
                System.out.println("Customer added!");
                return true;
            } else {
                System.out.println("Customer not added");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean updateCustomer(Customer customer) {
        //call AddressDAO to insert the address into the DB and return it's addressId
        int addressId = AddressDAO.addNewAddress(customer.getAddress());
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String insertStatement = "UPDATE customer set customerName=? addressId=? " +
                    "active=? createDate=? createdBy=? lastUpdate=? lastUpdateBy=? WHERE customerId = ?";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, insertStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, customer.getCustomerName());
            ps.setInt(2, addressId);
            ps.setInt(3, 1);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(5, "test");
            ps.setString(6, LocalDateTime.now().toString());
            ps.setString(7, "test");
            ps.setInt(8, DataStorage.getStoredCustomer().getCustomerID());
            //Saver results into result set
            //Check save was successful
            int res = ps.executeUpdate();

            if (res == 1) {//one row was affected; namely the one that was inserted!
                System.out.println("Customer updated!");
                return true;
            } else {
                System.out.println("Customer updated");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean deleteCustomer(int customerID) {
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "DELETE FROM U06NwI.customer WHERE customerId = ?";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Set value cityName to be passed to function in select statement
            ps.setInt(1, customerID);
            int res = ps.executeUpdate();
            if (res == 1) {//one row was affected; namely the one that was deleted!
                System.out.println("Customer deleted");
                return true;
            } else {
                System.out.println("Customer not deleted");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
