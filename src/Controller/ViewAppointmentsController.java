package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Customer;
import Model.DataStorage;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import java.util.Optional;
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
    private TableView<Appointment> ViewApptTableview;

    @FXML
    private TableColumn<Appointment, String> ViewApptTableviewNameCol;

    @FXML
    private TableColumn<Appointment, String> ViewApptTableviewDateTimeCol;

    @FXML
    private TableColumn<Appointment, String> ViewApptTableviewTypeCol;

    @FXML
    private Button backToMenuButton;

    @FXML
    void onActionAddAppt(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ChooseCustomer.fxml"));
        stage.setTitle("Choose Customer");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionBackToMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteAppt(ActionEvent event) {
        if(ViewApptTableview.getSelectionModel().getSelectedItem() != null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Delete Appointment?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                AppointmentDAO.deleteAppointment(ViewApptTableview.getSelectionModel().getSelectedItem().getAppointmentId());
                AppointmentDAO.getAllAppointments();
                ViewApptTableview.setItems(Model.DataStorage.getAllAppointments());
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select an appointment to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionViewWeek(ActionEvent event) {

    }

    @FXML
    void onActionViewAll(ActionEvent event) {

    }

    @FXML
    void onActionEditAppt(ActionEvent event) throws IOException {
        if (ViewApptTableview.getSelectionModel().getSelectedItem() != null)
        {
            Stage stage;
            Parent root;
            stage=(Stage) viewApptButton.getScene().getWindow();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/View/AddEditAppointment.fxml"));
            root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            AddEditAppointmentController AEAController = loader.getController();
            AEAController.receiveAppointment(ViewApptTableview.getSelectionModel().getSelectedItem());
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a customer to edit.");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionViewMonth(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppointmentDAO.getAllAppointments();
        ViewApptTableview.getItems().addAll(DataStorage.getAllAppointments());

        ViewApptTableviewNameCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getCustomer().getCustomerName());});
        ViewApptTableviewDateTimeCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getStartTime().toString());});
        ViewApptTableviewTypeCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getApptType());});

    }
}