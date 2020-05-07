package utilities;

import model.DataStorage;
import model.User;

public class CheckUserPassword {
    public static boolean checkPassword(String userName, String password){
        User user = DataStorage.lookupUser(userName);
        if(user != null){
            if(user.getPassword().equals(password)) {
                return true;
            }
            else{
                System.out.println("incorrect password");
            }
        }
        else{
            System.out.println("user not found");
        }
        return false;
    }

}
