package Controller;

import DAO.appointmentDAO;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    @FXML private Button mainMenuButton;
    @FXML private Button addAppointmentButton;
    @FXML private Button updateAppointmentButton;
    @FXML private Button deleteAppointmentButton;

    @FXML private RadioButton allRadialButton;
    @FXML private RadioButton withinMonthRadialButton;
    @FXML private RadioButton withinWeekRadialButton;

    @FXML private ToggleGroup radioButtonToggleGroup;

    @FXML private TableView appointmentTableView;

    @FXML private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML private TableColumn<Appointment, String> appointmentTitleColumn;
    @FXML private TableColumn<Appointment, String> appointmentDescriptionColumn;
    @FXML private TableColumn<Appointment, String> appointmentLocationColumn;
    @FXML private TableColumn<Appointment, String> appointmentContactColumn;
    @FXML private TableColumn<Appointment, String> appointmentTypeColumn;
    @FXML private TableColumn<Appointment, String> appointmentStartDateColumn;
    @FXML private TableColumn<Appointment, LocalTime> appointmentStartTimeColumn;
    @FXML private TableColumn<Appointment, String> appointmentEndDateColumn;
    @FXML private TableColumn<Appointment, LocalTime> appointmentEndTimeColumn;
    @FXML private TableColumn<Appointment, Integer> customerIDColumn;
    @FXML private TableColumn<Appointment, Integer> userIDColumn;

    @FXML private ObservableList<Model.Appointment> allAppointmentsObservableList = FXCollections.observableArrayList();
    @FXML private ObservableList<Model.Appointment> getAppointmentsWithinAMonthObservableList = FXCollections.observableArrayList();
    @FXML private ObservableList<Model.Appointment> getAppointmentsWithinAWeekObservableList = FXCollections.observableArrayList();

    public static Appointment appointmentControllerToUpdate;
    private appointmentDAO appointmentDAO = new appointmentDAO();

    public AppointmentController() throws SQLException {
        allAppointmentsObservableList = appointmentDAO.getAllAppointments();
    }

    /**
     * Populates info on the screen
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println("Appointment Table View Initialized");
        viewAllAppointments();

        Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utczdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldtIn = utczdt.toLocalDateTime();

        ZonedDateTime zdtOut = ldtIn.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtOutToLocalTZ = zdtOut.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldtOutFinal = zdtOutToLocalTZ.toLocalDateTime();

        System.out.println(ts);
        System.out.println(ldt);
        System.out.println(zdt);
        System.out.println(utczdt);
        System.out.println(ldtIn);
        System.out.println("-----------------------------------------------------");
        System.out.println(zdtOut);
        System.out.println(zdtOutToLocalTZ);
        System.out.println(ldtOutFinal);
    }

    /**
     * Displays all appointments in the database in the table view.
     */
    @FXML public void viewAllAppointments(){
        appointmentTableView.setItems(allAppointmentsObservableList);

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));

        appointmentStartDateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("FormattedStartDateTime"));
        appointmentStartTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalTime>("StartTimeLocally"));
        appointmentEndDateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("FormattedEndDate"));
        appointmentEndTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalTime>("EndTimeLocally"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        appointmentTableView.getSelectionModel().selectFirst();
    }

    /**
     * Displays all appointments in the database within the current month in the table view.
     * @param event
     * @throws IOException
     */
    @FXML public void viewAppointmentsWithinMonth(javafx.event.ActionEvent event) throws IOException {
        Month currentMonth = LocalDate.now().getMonth();
        int currentYear = LocalDate.now().getYear();

        if(!getAppointmentsWithinAMonthObservableList.isEmpty()){
            getAppointmentsWithinAMonthObservableList.clear();
        }

        for(Appointment appointment : allAppointmentsObservableList){
            if(appointment.getAppointmentStartDateTime().getMonth().equals(currentMonth) && appointment.getAppointmentStartDateTime().getYear()==currentYear){
                getAppointmentsWithinAMonthObservableList.add(appointment);
            }
        }

        appointmentTableView.setItems(getAppointmentsWithinAMonthObservableList);
        appointmentTableView.getSelectionModel().selectFirst();

        if(getAppointmentsWithinAMonthObservableList.isEmpty()){
            Alert noAppointmentsWithinAMonth = new Alert(Alert.AlertType.INFORMATION);
            noAppointmentsWithinAMonth.setTitle("No appointments within a month");
            noAppointmentsWithinAMonth.setContentText("No appointments within a month");
            noAppointmentsWithinAMonth.showAndWait();
        }
    }

    /**
     * Displays all appointments in the database within the current week.
     * @param event
     * @throws IOException
     */
    @FXML public void viewAppointmentsWithinWeek(javafx.event.ActionEvent event) throws IOException{
        TemporalField currentWeekBasedYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        LocalDate currentDate = LocalDate.now();
        int currentWeek = currentDate.get(currentWeekBasedYear);
        int currentYear = LocalDate.now().getYear();

        if(!getAppointmentsWithinAWeekObservableList.isEmpty()){
            getAppointmentsWithinAWeekObservableList.clear();
        }

        for (Appointment appointment : allAppointmentsObservableList){
            TemporalField appointmentWeek = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
            int appointmentWeekNumber = appointment.getAppointmentStartDateTime().toLocalDate().get(appointmentWeek);
            if (appointmentWeekNumber == currentWeek && appointment.getAppointmentStartDateTime().getYear() == currentYear){
                getAppointmentsWithinAWeekObservableList.add(appointment);
            }
        }

        appointmentTableView.setItems(getAppointmentsWithinAWeekObservableList);
        appointmentTableView.getSelectionModel().selectFirst();

        if(getAppointmentsWithinAWeekObservableList.isEmpty()){
            Alert noWeeklyAppointments = new Alert(Alert.AlertType.INFORMATION);
            noWeeklyAppointments.setTitle("No appointments for the current week");
            noWeeklyAppointments.setContentText("No appointments for the current week");
            noWeeklyAppointments.showAndWait();
        }
    }



    /**
     * Moves to the main menu page
     * @param event
     */
    public void moveToMainMenu(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/MainMenu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) mainMenuButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves to the appointment add page
     * @param event
     */
    public void moveToAppointmentAdd(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/AppointmentAdd.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) addAppointmentButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }

    /**
     * Moves to the appointment update page
     * @param event
     */
    public void moveToAppointmentUpdate(javafx.event.ActionEvent event) throws IOException{
        try {
            appointmentControllerToUpdate = (Appointment) appointmentTableView.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/AppointmentUpdate.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) updateAppointmentButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the selected appointment from the table view in the database.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML public void deleteAppointmentButtonAction(javafx.event.ActionEvent event) throws IOException, SQLException{
        try{
            Appointment appointmentDeleting = (Appointment) appointmentTableView.getSelectionModel().getSelectedItem();

            int appointmentID = appointmentDeleting.getAppointmentID();

            Alert deleteAppointment = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAppointment.setResizable(true);
            deleteAppointment.setWidth(600);
            deleteAppointment.setTitle("Confirm Appointment Deletion");
            deleteAppointment.setContentText("Do you want to delete appointment ID: "+appointmentDeleting.getAppointmentID()+" , appointment Type: "+appointmentDeleting.getAppointmentType());

            //deleteAppointment.showAndWait();
            Optional <ButtonType> alertChoice = deleteAppointment.showAndWait();

            if(alertChoice.isPresent() && alertChoice.get() == ButtonType.OK){
                allAppointmentsObservableList.remove(appointmentDeleting);
                appointmentDAO.deleteAppointment(appointmentDeleting.getAppointmentID());

                Alert appointmentCancelled = new Alert(Alert.AlertType.INFORMATION);
                appointmentCancelled.setTitle("Appointment Cancelled");
                appointmentCancelled.setContentText("Appointment has been cancelled");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
