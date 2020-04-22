package Controller;

import Utils.CheckUserPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginScreenController {

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

        System.exit(0);
    }

    @FXML
    void onActionLogin(ActionEvent event) {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        if(CheckUserPassword.checkPassword(userName, password) == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You win a donut!");
            alert.showAndWait();
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
