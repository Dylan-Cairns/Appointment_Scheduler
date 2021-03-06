package controller;

import dataAccessObjects.AppointmentDAO;
import model.*;
import utilities.TimeFunctions;
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
import java.time.DayOfWeek;
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
/*          When this screen was initialized the currently selected appointment to edit
            was removed from the local appts list in order to make the timeslot available
            for time generation functions. Since the user cancelled the edit, we must
            add the appointment back to the local appts list.*/
            DataStorage.addAppointment(DataStorage.getStoredAppointment());
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionSaveAppt(ActionEvent event) {
        //check that there required information is comlete
        if(datePickerBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a date");
            alert.showAndWait();
        }
        else if(startTimeComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a start time");
            alert.showAndWait();
        }
        else if(apptLengthComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select an appointment length");
            alert.showAndWait();
        }
        else if(apptTypeComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select an appointment type");
            alert.showAndWait();
        }
        else {
            //if all required field are entered, save appointment
            try {
                //get customer
                Customer customer = DataStorage.getStoredAppointment().getCustomer();
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
                if (DataStorage.getStoredAppointment().getUserId() != -1){
                    //this is an existing appt. run an update command
                    int apptId = DataStorage.getStoredAppointment().getAppointmentId();
                    int userId = DataStorage.getStoredAppointment().getUserId();
                    Appointment appointment = new Appointment(apptId, customer, userId, apptType, startTime, endTime);
                    savesuccesfull = AppointmentDAO.updateAppointment(appointment);
                }
                else {
                    //this is a new appointment. run insert command

                    //get current user userID
                    int userId = DataStorage.getStoredUser().getUserID();
                    Appointment appointment = new Appointment(customer,
                            userId, apptType, startTime, endTime);
                    savesuccesfull = AppointmentDAO.addNewAppointment(appointment);
                }

                if(savesuccesfull) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Saved");
                    alert.setContentText("Appointment Successfully saved.");
                    AppointmentDAO.getAllAppointments();
                    stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointments.fxml"));
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
    }



    @FXML
    void onActionSelectDate(ActionEvent event) {
        //make sure the selected date is not today or earlier
        if(datePickerBox.getValue().isBefore(LocalDate.now()) ||
                datePickerBox.getValue().isAfter(LocalDate.now().plusMonths(3))) {
            datePickerBox.setValue(LocalDate.now());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Appointments may only be scheduled for the " +
                    "current date or up to three months in advance");
            alert.showAndWait();
        }
        else if(datePickerBox.getValue().getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                datePickerBox.getValue().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Appointments may only be scheduled for Monday through Friday");
            alert.showAndWait();
        }
        else {
            startTimeComboBox.getItems().setAll(TimeFunctions.getTimeslots(datePickerBox.getValue()));
        }
    }

    @FXML
    void onActionStartTimeComboBox(ActionEvent event) {
        LocalDate ld = datePickerBox.getValue();
        LocalTime lt = startTimeComboBox.getSelectionModel().getSelectedItem();
        LocalDateTime selectedTime = ld.atTime(lt);
        if (selectedTime.isBefore(LocalDateTime.now())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a time later than now");
            alert.showAndWait();
        } else {
            apptLengthComboBox.getItems().setAll(utilities.TimeFunctions.generateApptLengths(selectedTime));
        }
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
        //remove the appointment being edited from the local appointments list. This will
        // cause the timeslot generating functions to make the previously selected timeslot available.
        DataStorage.removeAppointment(appointment);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        apptTypeComboBox.getItems().addAll("Presentation", "Scrum", "Consultation");

    }
}
