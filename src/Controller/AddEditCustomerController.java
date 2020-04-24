package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AddEditCustomerController {

    Stage stage;
    Parent scene;

    @FXML
    private Button viewAppointmentsButton;

    @FXML
    private TextField nameTextBox;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField address1TextField;

    @FXML
    private TextField address2TextField;

    @FXML
    private ChoiceBox<?> cityChoiceBox;

    @FXML
    private ChoiceBox<?> stateChoiceBox;

    @FXML
    private TextField postalCodeTextBox;

    @FXML
    private ChoiceBox<?> countryChoiceBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // confirm before returning to main menu
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Forget changes and return to main menu?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionSaveCustomer(ActionEvent event) {

    }

    @FXML
    void onActionViewAppointments(ActionEvent event) {

    }

}
