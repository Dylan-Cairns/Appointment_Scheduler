package Controller;

import Utils.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    Stage stage;
    Parent scene;

    @FXML
    private Button viewCustomersBtn;

    @FXML
    private Button viewAppointmentsBtn;

    @FXML
    private Button viewReportsBtn;

    @FXML
    private Button exitBtn;

    @FXML
    void onActionExit(ActionEvent event) {
        DBConnection.closeConnection();
        System.exit(0);
    }

    @FXML
    void onActionViewAppointments(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ViewAppointments.fxml"));
        stage.setTitle("View Appointments");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionViewCustomers(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ViewCustomers.fxml"));
        stage.setTitle("View customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionViewReports(ActionEvent event) {

    }

}
