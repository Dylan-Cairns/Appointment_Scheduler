package Controller;

import Model.DataStorage;
import Utils.CheckUserPassword;
import Utils.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreenController {

    Stage stage;
    Parent scene;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitProgramButton;

    @FXML
    void onActionExit(ActionEvent event) {
        DBConnection.closeConnection();
        System.exit(0);
    }

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        if(CheckUserPassword.checkPassword(userName, password) == true) {
            //if user is found and password is correct, load the next screen,
            // and store the user as currentuser in data storage
            DataStorage.setStoredUser(DataStorage.lookupUser(userNameField.getText()));
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
            stage.setTitle("Appointment Scheduler");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Password or username incorrect. " +
                    "Please check and try again. Please note passwords " +
                    "are case sensitive; usernames are not.");
            alert.showAndWait();
        }

    }

}
