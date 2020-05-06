package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.*;
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
        try {
            //get customer
            Customer customer = DataStorage.getStoredAppointment().getCustomer();
            //get current user userID
            int userId = DataStorage.getStoredUser().getUserID();
            //get appt type
            String apptType = apptTypeComboBox.getSelectionModel().getSelectedItem();
            //Get start and finish times
            LocalDate ld = datePickerBox.getValue();
            LocalTime lt = startTimeComboBox.getSelectionModel().getSelectedItem();
            LocalDateTime startTime = ld.atTime(lt);
            int apptLength = Integer.parseInt(apptLengthComboBox.getSelectionModel().getSelectedItem().substring(0, 2));
            LocalDateTime endTime = startTime.plusMinutes(apptLength);
            /* if the userId is -1, that means the appt is new. if not it is an existing appt.
             */
            boolean savesuccesfull;
            if (userId != -1){
                //this is an existing appt. run an update command
                int apptId = DataStorage.getStoredAppointment().getAppointmentId();
                Appointment appointment = new Appointment(apptId, customer, userId, apptType, startTime, endTime);
                savesuccesfull = AppointmentDAO.updateAppointment(appointment);
            }
            else {
                //this is a new appointment. run insert command
                Appointment appointment = new Appointment(customer,
                        DataStorage.getStoredUser().getUserID(), apptType, startTime, endTime);
                savesuccesfull = AppointmentDAO.addNewAppointment(appointment);
            }

            if(savesuccesfull == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Saved");
                alert.setContentText("Appointment Successfully saved.");
                AppointmentDAO.getAllAppointments();
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/ViewAppointments.fxml"));
                stage.setTitle("View Appointments");
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Save failed");
                alert.setContentText("Appointment not saved.");
            }
            //clear the customer variable saved in storage
            DataStorage.clearStoredCustomer();
        }
        catch(NumberFormatException | IOException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values in all fields.");
            alert.showAndWait();
        }

    }



    @FXML
    void onActionSelectDate(ActionEvent event) {
        //make sure the selected date is not today or earlier
        if(datePickerBox.getValue().isEqual(LocalDate.now()) || datePickerBox.getValue().isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a date after the current date.");
            alert.showAndWait();
        }
        else {
            startTimeComboBox.getItems().clear();
            startTimeComboBox.getItems().addAll(TimeFunctions.getTimeslots(datePickerBox.getValue()));
        }
    }

    @FXML
    void onActionStartTimeComboBox(ActionEvent event) {
        apptLengthComboBox.getItems().clear();
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

        apptTypeComboBox.getItems().addAll("Presentation", "Scrum", "Consultation");

    }
}
