package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewCustomersController {
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
    private TableColumn<?, ?> ViewCustTableviewNameCol;

    @FXML
    private TableColumn<?, ?> ViewCustTableviewCityCol;

    @FXML
    private TableColumn<?, ?> ViewCustTableviewStateCol;

    @FXML
    private TableColumn<?, ?> ViewCustTableviewPhoneCol;

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

}
