package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewAppointmentsController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton viewAllRadioBttn;

    @FXML
    private RadioButton viewMonthRadioBttn;

    @FXML
    private RadioButton viewWeekRadioBttn;

    @FXML
    private Button viewApptButton;

    @FXML
    private Button deleteApptButton;

    @FXML
    private Button addApptButton;

    @FXML
    private TableView<?> ViewCustTableview;

    @FXML
    private TableColumn<?, ?> ViewApptTableviewNameCol;

    @FXML
    private TableColumn<?, ?> ViewApptTableviewDateCol;

    @FXML
    private TableColumn<?, ?> ViewApptTableviewTimeCol;

    @FXML
    private TableColumn<?, ?> ViewApptTableviewTypeCol;

    @FXML
    private Button backToMenuButton;

    @FXML
    void onActionAddAppt(ActionEvent event) {

    }

    @FXML
    void onActionBackToMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteAppt(ActionEvent event) {

    }

    @FXML
    void onActionRadioBttn(ActionEvent event) {

    }

    @FXML
    void onActionViewAll(ActionEvent event) {

    }

    @FXML
    void onActionViewCustomeAppt(ActionEvent event) {

    }

    @FXML
    void onActionViewMonth(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}