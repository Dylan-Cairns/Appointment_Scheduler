package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import Model.DataStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddEditAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField nameTextBox;

    @FXML
    private DatePicker datePickerBox;

    @FXML
    private ComboBox<?> timeComboBox;

    @FXML
    private ComboBox<String> ApptTypeComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // confirm before returning to main menu
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Forget changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/ViewAppointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionSaveAppt(ActionEvent event) {

    }

    @FXML
    void onActionSelectDate(ActionEvent event) {

    }

    @FXML
    void onActionTimeComboBox(ActionEvent event) {

    }

    @FXML
    void selectApptType(ActionEvent event) {

    }

    public void ReceiveAppointment(Appointment appointment) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        AppointmentDAO.getAllApptTypes();
        ApptTypeComboBox.getItems().addAll(DataStorage.getAllApptTypes());

    }
}
