package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Customer;
import Model.DataStorage;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private ComboBox<Customer> customerNameComboBox;

    @FXML
    private Button continueButton;

    @FXML
    private Button cancelButton;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ViewCustomers.fxml"));
        stage.setTitle("View customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    void onActionContinue(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerNameComboBox.getItems().addAll(DataStorage.getAllCustomers());
    }

}
