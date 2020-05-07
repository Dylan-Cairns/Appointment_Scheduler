package controller;

import model.Appointment;
import model.Customer;
import model.DataStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomers.fxml"));
        stage.setTitle("View customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    void onActionContinue(ActionEvent event) throws IOException {
        if(customerNameComboBox.getSelectionModel().getSelectedItem() != null) {
            Stage stage;
            Parent root;
            stage=(Stage) customerNameComboBox.getScene().getWindow();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/AddEditAppointment.fxml"));
            root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            AddEditAppointmentController AEAController = loader.getController();
            Customer tempCustomer = customerNameComboBox.getSelectionModel().getSelectedItem();
            Appointment tempAppointment = new Appointment(tempCustomer);
            AEAController.receiveAppointment(tempAppointment);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a customer.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerNameComboBox.getItems().addAll(DataStorage.getAllCustomers());
    }

}
