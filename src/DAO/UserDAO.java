package DAO;

import Model.User;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            // start the database with an instance variable
            Connection conn = DBConnection.getConnection();
            //Create string to use in prepared statement
            String selectStatement = "SELECT userId, userName, password FROM U06NwI.user";
            //Set prepared statement in DBQuery class
            DBQuery.setPreparedStatement(conn, selectStatement);
            //Instantiate prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //Saver results into result set
            ResultSet rs = ps.executeQuery();
            //Create user objects from results, save in an observable list
            while (rs.next()) {
                int userID=rs.getInt("userid");
                String userName=rs.getString("userName");
                String password=rs.getString("password");
                User user = new User(userID, userName, password);
                userList.add(user);
            }
            rs.close();
            DBConnection.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
}
