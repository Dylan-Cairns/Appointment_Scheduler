package DAO;

import Model.*;
import Utils.DBConnection;
import Utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAO {

    public static Address getAddressByID(int addressID) {
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT addressId, address, address2, " +
                    "cityId, postalCode, phone FROM U06NwI.address WHERE addressId = ?";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Set value cityName to be passed to function in select statement
            ps.setInt(1, addressID);
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Check there is an entry, if so, return entry
            if (rs.next()) {
                int addressId = rs.getInt("addressId");
                String address = rs.getString("address");
                String address2 = rs.getString("address2");
                int cityID = rs.getInt("cityId");
                City city = CityDAO.getCityByID(cityID);
                String postalCode = rs.getString("postalCode");
                String phone = rs.getString("phone");
                Address returnedAddress = new Address(addressID, address, address2, city, postalCode, phone);
                rs.close();
                return returnedAddress;
            } else {
                System.out.println("No matching address found");
                rs.close();
                DBConnection.closeConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}