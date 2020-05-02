package Controller;

import DAO.CustomerDAO;
import Model.*;
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

public class AddEditCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField nameTextBox;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField address1TextField;

    @FXML
    private TextField address2TextField;

    @FXML
    private ComboBox<City> cityComboBox;

    @FXML
    private TextField postalCodeTextBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // confirm before returning to main menu
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Forget changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/ViewCustomers.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionSaveCustomer(ActionEvent event) {
        try {
            String address1 = address1TextField.getText();
            String address2 = address2TextField.getText();
            City city = cityComboBox.getSelectionModel().getSelectedItem();
            String postalCode = (postalCodeTextBox.getText());
            String phone = phoneTextField.getText();
            String name = (nameTextBox.getText());
            Address address = new Address(address1, address2, city, postalCode, phone);
            /* check if there is a customer value in the getCustomertoSave variable.
            If so, use the constructor that sets customerId and update the existing user
            rather than saving a new one. save a boolean value into the variable savesuccessfull
             to determine what message to show to the user.
             */
            boolean savesuccesfull;
            if (DataStorage.getStoredCustomer() != null){
                int customerId = DataStorage.getStoredCustomer().getCustomerID();
                Customer customer = new Customer(customerId, name, address);
                savesuccesfull = CustomerDAO.updateCustomer(customer);
            }
            else {
                Customer customer = new Customer(name, address);
                savesuccesfull = CustomerDAO.addNewCustomer(customer);
            }

            if(savesuccesfull == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Customer Saved");
                alert.setContentText("Customer Successfully saved.");
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/ViewCustomers.fxml"));
                stage.setTitle("View customers");
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Save failed");
                alert.setContentText("Customer not saved.");
            }
            //clear the customer variable saved in storage
            DataStorage.clearStoredCustomer();
        }
        catch(NumberFormatException | IOException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values in all fields.");
            alert.showAndWait();
        }

    }

    public void receiveCustomer(Customer customer)
    {
        DataStorage.setStoredCustomer(customer);
        nameTextBox.setText(customer.getCustomerName());
        phoneTextField.setText(customer.getAddress().getPhone());
        address1TextField.setText(customer.getAddress().getAddressLine1());
        address2TextField.setText(customer.getAddress().getAddressLine2());
        cityComboBox.setItems(DataStorage.getAllCities());
        cityComboBox.getSelectionModel().select(customer.getAddress().getCity());
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cityComboBox.getItems().addAll(DataStorage.getAllCities());
    }
}
