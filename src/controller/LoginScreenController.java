package controller;

import model.Appointment;
import model.DataStorage;
import appointment_Scheduler.Main;
import utilities.CheckUserPassword;
import utilities.DBConnection;
import utilities.Logger;
import utilities.TimeFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Label titleLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitProgramButton;

    ResourceBundle rb;

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        String userName = userNameField.getText();
        String password = passwordField.getText();

        ////check if user is found and password is correct
        if(CheckUserPassword.checkPassword(userName, password)) {
            //store the user for logging and tagging appts
            DataStorage.setStoredUser(DataStorage.lookupUser(userNameField.getText()));
            //make an entry in the log
            Logger.logUserAccess();

            //check if there is an appointment within the next 15 minutes.
            Appointment appointment = TimeFunctions.checkForUpcomingAppt();
            if(appointment != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointment Notification");
                alert.setContentText("There is an appointment scheduled with the customer "
                        + appointment.getCustomer().getCustomerName()
                        + " at " + appointment.getStartTime());
                alert.showAndWait();
            }


            // load the next screen, and store the user as currentuser in data storage
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setTitle("Appointment Scheduler");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("errorTitle"));
            alert.setContentText(rb.getString("errorText"));
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        titleLabel.setText(rb.getString("titleLabel"));
        usernameLabel.setText(rb.getString("usernameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        loginButton.setText(rb.getString("loginButton"));
        exitProgramButton.setText(rb.getString("exitButton"));
        Main.getStage().setTitle(rb.getString("windowTitle"));

        //Use lambda expressions to control the exit button.
        // Lambda expressions are less verbose which makes code easier to read.
        exitProgramButton.setOnAction(event -> System.exit(0));
    }

}
