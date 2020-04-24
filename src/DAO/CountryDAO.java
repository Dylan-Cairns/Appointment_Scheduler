package DAO;

import Model.Country;
import Utils.DBConnection;
import Utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {

    public static Country getCountry(String countryName) {
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT countryId, country FROM U06NwI.country WHERE country = ?";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Set value countryName to be passed to function in select statement
            ps.setString(1, countryName);
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Check there is an entry, if so, return entry
            if(rs.next()) {
                int countryID=rs.getInt("countryId");
                String country=rs.getString("country");
                Country returnedCountry = new Country(countryID, country);
                rs.close();
                DBConnection.closeConnection();
                return returnedCountry;
            }
            else {
                System.out.println("No matching country found");
                rs.close();
                DBConnection.closeConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static Country getCountryByID(int countryID) {
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT countryId, country FROM U06NwI.country WHERE countryId = ?";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Set value countryName to be passed to function in select statement
            ps.setInt(1, countryID);
            //Save results into result set
            ResultSet rs = ps.executeQuery();
            //Check there is an entry, if so, return entry
            if(rs.next()) {
                int tempCountryID=rs.getInt("countryId");
                String country=rs.getString("country");
                Country returnedCountry = new Country(tempCountryID, country);
                rs.close();
                return returnedCountry;
            }
            else {
                System.out.println("No matching country found");
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
