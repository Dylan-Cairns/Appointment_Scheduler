package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataStorage {
    private static ObservableList<User> userList = FXCollections.observableArrayList();

    public static ObservableList<User> getAllUsers()
    {
        return userList;
    }

    public static void addUser(User user)
    {
        userList.add(user);
    }

    public static User lookupUser(int userID)
    {
        for(User user : getAllUsers())
        {
            if(user.getUserID() == userID)
                return user;
        }
        return null;
    }

    public static User lookupUser(String userName)
    {
        for(User user : getAllUsers())
        {
            if(user.getUserName().equalsIgnoreCase(userName))
            {
                return user;
            }
        }
        return null;
    }
}
