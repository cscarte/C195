package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    @FXML private Button totalAppointmentsButton;
    @FXML private Button scheduleForContactsButton;
    @FXML private Button clientCountButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Opens the ReportTotalAppointments.fxml screen
     * @param event
     */
    public void moveToTotalAppointmentsReport(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/ReportTotalAppointments.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) totalAppointmentsButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the ReportScheduleForContacts.fxml screen
     * @param event
     */
    public void moveToScheduleForContacts(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/ReportScheduleForContacts.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) scheduleForContactsButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the ReportClientCount.fxml screen
     * @param event
     */
    public void moveToCustomerCount(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/ReportClientCount.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) clientCountButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the MainMenu.fxml screen
     * @param event
     */
    public void moveToMainMenu(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/MainMenu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
