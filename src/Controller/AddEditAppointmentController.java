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
            int apptLength = Integer.parseInt(apptLengthComboBox.getSelectionModel().getSelectedItem().toString().substring(0, 2));
            LocalDateTime endTime = startTime.plusMinutes(apptLength);
            /* check if there is a appointment value in the storedappt variable.
            If so,update the existing appt rather than saving a new one.
            save a boolean value into the variable savesuccessfull
             to determine what message to show to the user.
             */
            boolean savesuccesfull;
            if (DataStorage.getStoredAppointment().getUserId() != -1){
                int apptId = DataStorage.getStoredAppointment().getAppointmentId();
                Appointment appointment = new Appointment(customer, userId, apptType, startTime, endTime);
                savesuccesfull = AppointmentDAO.updateAppointment(appointment);
            }
            else {
                Appointment appointment = new Appointment(customer,
                        DataStorage.getStoredUser().getUserID(), apptType, startTime, endTime);
                savesuccesfull = AppointmentDAO.addNewAppointment(appointment);
            }

            if(savesuccesfull == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Saved");
                alert.setContentText("Appointment Successfully saved.");
                CustomerDAO.getAllCustomerswithAddress();
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
        startTimeComboBox.getItems().clear();
        startTimeComboBox.getItems().addAll(TimeFunctions.getTimeslots(datePickerBox.getValue()));
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
