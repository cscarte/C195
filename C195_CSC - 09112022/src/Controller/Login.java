package Controller;

import DAO.appointmentDAO;
import DAO.userDAO;
import Model.Appointment;
import Utilities.Logger;
import Utilities.timezone;
import javafx.collections.ObservableList;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;

public class Login implements Initializable, timezone {

    private final static ResourceBundle LoginResourceBundle = ResourceBundle.getBundle("Resources/login", Locale.getDefault());
    @FXML private Label userNameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label zoneIDLabel;
    @FXML private Label locationLabel;
    @FXML private Label appTitleLabel;

    @FXML private TextField userNameTextField;
    @FXML private TextField passwordTextField;

    @FXML private Button loginButton;
    @FXML private Button exitButton;

    private Connection connection;

    private appointmentDAO appointmentDAO = new appointmentDAO();

    private ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();

    private LocalDateTime currentTimePlus15 = LocalDateTime.now().plusMinutes(15);
    private LocalDateTime currentTimeMinus15 = LocalDateTime.now().minusMinutes(15);

    private LocalDateTime appointmentStartTime;

    private int appointmentID;
    private LocalDateTime appointmentTime;

    private boolean appointmentAboutToStart;

    private ObservableList allUsers;
    private ObservableList appointmentListAboutToStart=null;

    public static int userID;
    public static String loggedInUserName;

    public Login() throws SQLException {
    }

    userDAO userDAO;

    /**
     * Populates info on the screen.
     *
     * The timezone setTimeZone = () -> {...} Lambda displays the user's timezone on the login screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            userNameLabel.setText(LoginResourceBundle.getString("username"));
            passwordLabel.setText(LoginResourceBundle.getString("password"));
            //zoneIDLabel.setText(ZoneId.systemDefault().toString());
            locationLabel.setText(LoginResourceBundle.getString("location"));
            appTitleLabel.setText(LoginResourceBundle.getString("apptitle"));

            userNameTextField.setPromptText(LoginResourceBundle.getString("username"));
            passwordTextField.setPromptText(LoginResourceBundle.getString("password"));

            loginButton.setText(LoginResourceBundle.getString("login"));
            exitButton.setText(LoginResourceBundle.getString("exit"));

            //Lambda, displays user's timezone on the main menu.
            timezone setTimeZone = () -> {
              zoneIDLabel.setText(String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            };

            setTimeZone.printTimeZone();
            //allUsers = userDAO.getAllUsers();
            //System.out.println(allUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates user in database when trying to make connection to database.
     * Pulls text from the username and password fields on the login screen upon clicking login button.
     * @param event
     * @throws IOException
     */
    public void loginButtonClicked(javafx.event.ActionEvent event) throws IOException, SQLException {
        String userNameInputField = userNameTextField.getText();
        String passwordInputField = passwordTextField.getText();
        String loginMessage;

        userID = userDAO.validateUserInDatabase(userNameInputField, passwordInputField);

        if (userID >= 1){
            System.out.println("User "+userNameTextField.getText()+" is in database!");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/MainMenu.fxml"));

            Logger.initializeLogManager();
            java.util.logging.Logger.getLogger(Login.class.getName()).log(Level.INFO, "user "+ userNameTextField.getText() + " logged in successfully.");

            Parent root =fxmlLoader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            loggedInUserName = userNameInputField;
            System.out.println(loggedInUserName);
            checkAppointmentUpcoming();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Incorrect username / password pairing");
            java.util.logging.Logger.getLogger(Login.class.getName()).log(Level.INFO, "user "+ userNameTextField.getText() + " did not log in successfully.");
            alert.show();
            //System.out.println("Incorrect username / password pair");
        }
    }

    /**
     * Closes the client schedule program
     * @param event
     */
    public void exitProgram(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void checkAppointmentUpcoming() throws SQLException {
        //appointmentListAboutToStart.clear();
        for (Appointment appointment : appointmentDAO.getAllAppointments()){
            appointmentStartTime = (appointment.getAppointmentStartDateTime());
            if ((appointmentStartTime.isBefore(currentTimePlus15)) && (appointmentStartTime.isAfter(currentTimeMinus15))){
                appointmentID = appointment.getAppointmentID();
                //appointmentListAboutToStart.add(appointment);
                appointmentTime = appointmentStartTime;
                appointmentAboutToStart = true;
            }
        }

        if(appointmentAboutToStart != false){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment ID "+appointmentID+" starts at: "+appointmentTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))+"\n\n"+"Starting time in 24 hour time is: "+appointmentTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")));
            Optional<ButtonType> appointmentConfirmation = alert.showAndWait();
            System.out.println("Appointment about to start");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No appointments within 15 minutes");
            Optional<ButtonType> appointmentConfirmation = alert.showAndWait();
            System.out.println("No appointments within 15 minutes");
        }
    }

    @Override
    public void printTimeZone() {
        System.out.println();
    }
}