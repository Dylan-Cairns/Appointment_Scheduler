package appointment_Scheduler;

import dataAccessObjects.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.Reports;

import javax.swing.text.Utilities;
import java.io.IOException;
import java.util.ResourceBundle;

public class Main extends Application {

    static Stage stage;

    @Override
    public void start(Stage stage) throws Exception{

        Main.stage = stage;
        //the next line sets the locale to Chinese
        //Locale.setDefault(new Locale("zh", "CN"));
        ResourceBundle rb = ResourceBundle.getBundle("utilities/LanguageFiles/rb");

        Parent main;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginScreen.fxml"));
            loader.setResources(rb);
            main = loader.load();

            Scene scene = new Scene(main);

            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    public static void main(String[] args) {

        System.out.println(Reports.apptSchedulePerUser());

        System.out.println("done yea.");

        //launch GUI
        //launch(args);

    }

    public static Stage getStage() {
        return stage;
    }
}
