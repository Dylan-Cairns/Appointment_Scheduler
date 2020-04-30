package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
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

public class ViewCustomersController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField customerSearchTxtBox;

    @FXML
    private Button customerSearchButton;

    @FXML
    private Button viewCustomerButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button backToMenuButton;

    @FXML
    private TableView<Customer> ViewCustTableview;

    @FXML
    private TableColumn<Customer, String> ViewCustTableviewNameCol;

    @FXML
    private TableColumn<Customer, String> ViewCustTableviewCityCol;

    @FXML
    private TableColumn<Customer, String> ViewCustTableviewCountryCol;

    @FXML
    private TableColumn<Customer, String> ViewCustTableviewPhoneCol;

    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddEditCustomer.fxml"));
        stage.setTitle("View customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        //make sure a customer is selected
        if(ViewCustTableview.getSelectionModel().getSelectedItem() != null)
        {
            //check if customer has a pending appointment in the database.
            int customerId = ViewCustTableview.getSelectionModel().getSelectedItem().getCustomerID();
            AppointmentDAO.getAllAppointments();
            if(!DataStorage.lookupCustAppointments(customerId).isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("This customer has a pending appointment. Customers" +
                        "with pending appointments cannot be deleted.");
                alert.showAndWait();
            }
            // customer has no pending appointments. show standard confirmation dialog
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Delete customer?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK)
                {
                    CustomerDAO.deleteCustomer(ViewCustTableview.getSelectionModel().getSelectedItem().getCustomerID());
                    CustomerDAO.getAllCustomers();
                    ViewCustTableview.setItems(Model.DataStorage.getAllCustomers());
                }
            }

        }
        //no customer was selected:
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionSearchCustomer(ActionEvent event) {
        ViewCustTableview.setItems(DataStorage.lookupCustomer(customerSearchTxtBox.getText()));
    }

    @FXML
    void onActionViewCustomer(ActionEvent event) throws IOException {
        if (ViewCustTableview.getSelectionModel().getSelectedItem() != null)
        {
            Stage stage;
            Parent root;
            stage=(Stage) viewCustomerButton.getScene().getWindow();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/View/AddEditCustomer.fxml"));
            root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            AddEditCustomerController AECController = loader.getController();
            AECController.receiveCustomer(ViewCustTableview.getSelectionModel().getSelectedItem());
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
    void onActionBackToMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ViewCustTableviewNameCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getCustomerName());
        });

        ViewCustTableviewCityCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getAddress().getCity().getCityName());
        });

        ViewCustTableviewCountryCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getAddress().getCity().getCountry().getCountryName());
        });

        ViewCustTableviewPhoneCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getAddress().getPhone());
        });

        CustomerDAO.getAllCustomers();
        ViewCustTableview.getItems().addAll(DataStorage.getAllCustomers());
    }
}