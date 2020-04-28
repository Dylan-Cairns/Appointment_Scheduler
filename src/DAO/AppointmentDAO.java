package DAO;

import Model.Address;
import Model.Appointment;
import Model.Customer;
import Model.DataStorage;
import Utils.DBConnection;
import Utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss.S");
                String txtStartTime = rs.getString("start");
                LocalDateTime startTime = LocalDateTime.parse(txtStartTime, df);
                String txtEndTime = rs.getString("end");
                LocalDateTime endTime = LocalDateTime.parse(txtStartTime, df);

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
}
