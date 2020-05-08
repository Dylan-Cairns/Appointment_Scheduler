package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.DataStorage;
import utilities.Reports;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewReportsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button userApptSchedules;

    @FXML
    private Button apptTypesPM;

    @FXML
    private Button avgApptLength;

    @FXML
    private TextArea ViewReportsTextBox;

    @FXML
    private Button backToMenuButton;

    @FXML
    void onActionApptTypesPM(ActionEvent event) {
        ViewReportsTextBox.setText(Reports.numberOfApptTypesbyMonth(6));
    }

    @FXML
    void onActionAvgApptLength(ActionEvent event) {
        ViewReportsTextBox.setText(Reports.avgApptLengthPerMonth(6));
    }

    @FXML
    void onActionuserApptSchedules(ActionEvent event) {
        ViewReportsTextBox.setText(Reports.apptSchedulePerUser());
    }

    @FXML
    void onActionBackToMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ViewReportsTextBox.setText("Choose an option from the choices above.");
    }
}
