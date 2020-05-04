package Controller;

import Model.Appointment;
import Model.DataStorage;
import Utils.TimeFunctions;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private ComboBox<LocalTime> startTimeComboBox;

    @FXML
    private ComboBox<String> apptTypeComboBox;

    @FXML
    private ComboBox<String> apptLengthComboBox;

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
        startTimeComboBox.getItems().addAll(TimeFunctions.getTimeslots(datePickerBox.getValue()));
    }

    @FXML
    void onActionStartTimeComboBox(ActionEvent event) {
        LocalDate ld = datePickerBox.getValue();
        LocalTime lt = startTimeComboBox.getSelectionModel().getSelectedItem();
        LocalDateTime ldt = ld.atTime(lt);
        apptLengthComboBox.getItems().addAll(Utils.TimeFunctions.generateApptLengths(ldt));
    }

    @FXML
    void onActionApptLengthComboBox(ActionEvent event) {

    }

    @FXML
    void selectApptType(ActionEvent event) {

    }

    public void receiveAppointment(Appointment appointment) {
        DataStorage.setStoredAppointment(appointment);
        nameTextBox.setText(appointment.getCustomer().getCustomerName());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        apptTypeComboBox.getItems().addAll(DataStorage.getAllApptTypes());

    }
}
