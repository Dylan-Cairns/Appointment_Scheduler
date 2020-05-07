package controller;

import dataAccessObjects.AppointmentDAO;
import model.Appointment;
import model.DataStorage;
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
    private ToggleGroup group = new ToggleGroup();

    @FXML
    private RadioButton viewAllRadioBttn = new RadioButton();

    @FXML
    private RadioButton viewMonthRadioBttn = new RadioButton();

    @FXML
    private RadioButton viewWeekRadioBttn = new RadioButton();

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
                ViewApptTableview.setItems(model.DataStorage.getAllAppointments());
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
    void onActionViewAll(ActionEvent event) {
        ViewApptTableview.getItems().clear();
        ViewApptTableview.getItems().addAll(DataStorage.getAllAppointments());
    }

    @FXML
    void onActionViewMonth(ActionEvent event) {
        ViewApptTableview.getItems().clear();
        ViewApptTableview.getItems().addAll(DataStorage.getApptsThisMonth());
    }

    @FXML
    void onActionViewWeek(ActionEvent event) {
        ViewApptTableview.getItems().clear();
        ViewApptTableview.getItems().addAll(DataStorage.getApptsThisWeek());
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
            alert.setContentText("Please select an appointment to edit.");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ViewApptTableview.getItems().addAll(DataStorage.getAllAppointments());

        ViewApptTableviewNameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCustomer().getCustomerName()));
        ViewApptTableviewDateTimeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStartTime().toString()));
        ViewApptTableviewTypeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getApptType()));

        ViewApptTableview.getSortOrder().add(ViewApptTableviewDateTimeCol);
        ViewApptTableview.sort();

        viewAllRadioBttn.setSelected(true);
    }
}