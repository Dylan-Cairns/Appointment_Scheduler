package Controller;

import Model.Customer;
import Model.DataStorage;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
        scene = FXMLLoader.load(getClass().getResource("/view/AddEditCustomer.fxml"));
        stage.setTitle("View customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void onActionSearchCustomer(ActionEvent event) {
        ViewCustTableview.setItems(DataStorage.lookupCustomer(customerSearchTxtBox.getText()));
    }

    @FXML
    void onActionViewCustomer(ActionEvent event) {

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

        ViewCustTableview.getItems().addAll(DataStorage.getAllCustomers());
    }
}