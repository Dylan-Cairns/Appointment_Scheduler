package Software2;

import DAO.*;
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
        CustomerDAO.getAllCustomers();

        City city = DataStorage.getAllCustomers().get(1).getAddress().getCity();
        Address address = new Address("Waffle Shop", "waffle St", city, "8008", "1234567899");
        Customer customer = new Customer("Dunkin Stunkin", address);
        CustomerDAO.addNewCustomer(customer);


        //launch GUI
        launch(args);

    }
}
