package DAO;

import Model.City;
import Model.Country;
import Model.Customer;
import Model.DataStorage;
import Utils.DBConnection;
import Utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDAO {

    public static City getCity(String cityName) {
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT cityId, city, countryId FROM U06NwI.city WHERE city = ?";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Set value cityName to be passed to function in select statement
            ps.setString(1, cityName);
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Check there is an entry, if so, return entry
            if(rs.next()) {
                int cityId=rs.getInt("cityId");
                String city=rs.getString("city");
                int countryID=rs.getInt("countryId");
                Country country = CountryDAO.getCountryByID(countryID);
                City returnedCity = new City(cityId, city, country);
                rs.close();
                DBConnection.closeConnection();
                return returnedCity;
            }
            else {
                System.out.println("No matching city found");
                rs.close();
                DBConnection.closeConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static City getCityByID(int cityID) {
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT cityId, city, countryId FROM U06NwI.city WHERE cityId = ?";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Set value cityName to be passed to function in select statement
            ps.setInt(1, cityID);
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Check there is an entry, if so, return entry
            if(rs.next()) {
                String city=rs.getString("city");
                int countryID=rs.getInt("countryId");
                Country country = CountryDAO.getCountryByID(countryID);
                City returnedCity = new City(cityID, city, country);
                rs.close();
                return returnedCity;
            }
            else {
                System.out.println("No matching city found");
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void getAllCities() {
        try {
            //Clear local data before downloading new cities list.
            DataStorage.emptyStoredData();
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT cityId FROM U06NwI.city";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Create user objects from results, save in an observable list
            while (rs.next()) {
                int cityId=rs.getInt("cityId");
                City city = getCityByID(cityId);
                if(city != null) {
                    //add city to cities list
                    DataStorage.addCity(city);
                }
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
