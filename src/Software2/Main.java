package Software2;

import DAO.CityDAO;
import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.*;
import Utils.DBConnection;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static Model.DataStorage.getAllCustomers;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginScreen.fxml"));
        primaryStage.setTitle("Appointment Scheduler");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {

        //download user list from database and store in DataStorage class
        UserDAO.getAllUsers();

        //launch GUI
        launch(args);

    }
}
