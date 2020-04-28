package DAO;

import Model.*;
import Utils.DBConnection;
import Utils.DBQuery;

import java.sql.*;
import java.time.LocalDateTime;

import static java.lang.Integer.parseInt;

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

    public static int addNewAddress(Address address) {
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String insertStatement = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            //Set prepared statement in DBQuery class
            //this method will return the primary key created for the address in the DB
            DBQuery.setPreparedStatementReturnKeys(conn, insertStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //PreparedStatement ps = conn.prepareStatement(insertStatement, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, address.getAddressLine1());
            ps.setString(2, address.getAddressLine2());
            ps.setInt(3, address.getCity().getCityID());
            ps.setString(4, address.getPostalCode());
            ps.setString(5, address.getPhone());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(7, "test");
            ps.setString(8, LocalDateTime.now().toString());
            ps.setString(9, "test");
            //Check save was successful
            int res = ps.executeUpdate();
            if (res == 1) {//one row was affected
                System.out.println("Address added");
            } else {
                System.out.println("Address not added");
            }
            //resultset contains addressId of the new address
            ResultSet genKeys = ps.getGeneratedKeys();
            genKeys.next();
            int generatedKey = genKeys.getInt(1);
            //return the addressId
            return generatedKey;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}