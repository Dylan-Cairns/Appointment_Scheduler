package DAO;

import Model.*;
import Utils.DBConnection;
import Utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public static Customer getCustomerByID(int customerID) {
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
                DBConnection.closeConnection();
                return returnedCustomer;
            }
            else {
                System.out.println("No matching customer found");
                rs.close();
                DBConnection.closeConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void getAllCustomers() {
        try {
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
                Customer customer = getCustomerByID(customerID);
                if(customer != null) {
                    DataStorage.addCustomer(customer);
                }
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
