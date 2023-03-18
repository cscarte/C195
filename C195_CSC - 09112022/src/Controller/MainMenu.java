package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenu {
    
    @FXML private Button customerButton;
    @FXML private Button appointmentButton;
    @FXML private Button reportButton;
    @FXML private Button exitProgramButton;
    @FXML private Button logOutButton;

    /**
     * Moves to the Customer tableview page
     * @param event
     */
    public void moveToCustomerPage(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/Customer.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) customerButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Moves to the Appointment tableview page
     * @param event
     */
    public void moveToAppointmentsPage(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/Appointment.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) appointmentButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves to the Report menu page
     * @param event
     */
    public void moveToReportsPage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/Report.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) reportButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * "Logs out" the user, sending them back to the login screen.
     * Database connection remains enabled, but will have to reenter username and password.
     * @param event
     */
    public void moveToLoginScreen(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/Login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the window for the program, shutting down the connection to the database (handled in Main.java).
     * @param event
     */
    public void exitProgram(ActionEvent event){
        Stage stage = (Stage) exitProgramButton.getScene().getWindow();
        stage.close();
    }
}