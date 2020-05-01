package DAO;

import Model.*;
import Utils.DBConnection;
import Utils.DBQuery;
import Utils.TimeFunctions;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDAO {
    public static Appointment getAppointmentByID(int appointmentId) {
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT appointmentId, customerId, userId, type, start, end FROM U06NwI.appointment WHERE appointmentId = ?";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Set value cityName to be passed to function in select statement
            ps.setInt(1, appointmentId);
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Check there is an entry, if so, return entry
            if(rs.next()) {
                //use customerId to get Customer object
                int customerId =rs.getInt("customerId");
                Customer customer = CustomerDAO.getCustomerByID(customerId);

                int userId=rs.getInt("userId");
                String apptType=rs.getString("type");

                //convert date string to LocalDateTime object for start and end time
                LocalDateTime startTime = TimeFunctions.DBTimeToLocalTime(rs.getTimestamp("start"));
                LocalDateTime endTime = TimeFunctions.DBTimeToLocalTime(rs.getTimestamp("end"));

                Appointment returnedAppointment = new Appointment(appointmentId, customer, userId, apptType, startTime, endTime);
                rs.close();
                return returnedAppointment;
            }
            else {
                System.out.println("No matching appointment found");
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void getAllAppointments() {
        try {
            //Clear the old entries from the customer list before adding new entries from database
            DataStorage.emptyStoredData();
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT appointmentId FROM U06NwI.appointment";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Create user objects from results, save in an observable list
            while (rs.next()) {
                int appointmentId=rs.getInt("appointmentId");
                Appointment appointment = getAppointmentByID(appointmentId);
                if(appointment != null) {
                    //add customer to customer list
                    DataStorage.addAppointment(appointment);
                }
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static boolean addNewAppointment(Appointment appointment) {
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String insertStatement = "INSERT INTO appointment (customerId, userId, title, description, " +
                    "location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, insertStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, appointment.getCustomer().getCustomerID());
            ps.setInt(2, appointment.getUserId());
            ps.setString(3, "na");
            ps.setString(4, "na");
            ps.setString(5, "na");
            ps.setString(6, "na");
            ps.setString(7, appointment.getApptType());
            ps.setString(8, "na");
            //fix time functions here
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(5, "na");
            ps.setString(6, LocalDateTime.now().toString());
            ps.setString(7, "na");
            //Save results into result set
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

    public static boolean deleteAppointment(int appointmentId) {
        try {
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "DELETE FROM U06NwI.appointment WHERE appointmentId = ?";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Set value cityName to be passed to function in select statement
            ps.setInt(1, appointmentId);
            int res = ps.executeUpdate();
            if (res == 1) {//one row was affected; namely the one that was deleted!
                System.out.println("Appointment deleted");
                return true;
            } else {
                System.out.println("Appointment not deleted");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static void getAllApptTypes() {
        try {
            //Clear local data before downloading new appt type list
            DataStorage.emptyStoredData();
            // start the database connection with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT type FROM U06NwI.appointment";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Create user objects from results, save in an observable list
            while (rs.next()) {
                String apptType = rs.getString("type");
                DataStorage.addApptType(apptType);
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
