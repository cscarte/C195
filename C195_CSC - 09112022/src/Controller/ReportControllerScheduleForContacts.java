 package Controller;

 import DAO.appointmentDAO;
 import Model.Appointment;
 import Utilities.DBConnection;
 import com.mysql.cj.protocol.Resultset;
 import javafx.collections.FXCollections;
 import javafx.collections.ObservableList;
 import javafx.fxml.FXML;
 import javafx.fxml.FXMLLoader;
 import javafx.fxml.Initializable;
 import javafx.scene.Parent;
 import javafx.scene.Scene;
 import javafx.scene.control.Button;
 import javafx.scene.control.TableColumn;
 import javafx.scene.control.TableView;
 import javafx.scene.control.cell.PropertyValueFactory;
 import javafx.stage.Stage;

 import java.net.URL;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.SQLException;
 import java.time.LocalTime;
 import java.util.Date;
 import java.util.ResourceBundle;

 public class ReportControllerScheduleForContacts implements Initializable {
    @FXML private Button cancelButton;

    @FXML private TableView<Appointment> contactScheduleTableView;
    @FXML private TableColumn<Appointment, Integer> contactIDColumn;
    @FXML private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML private TableColumn<Appointment, String> appointmentTitleColumn;
    @FXML private TableColumn<Appointment, String> appointmentTypeColumn;
    @FXML private TableColumn<Appointment, String> appointmentDescriptionColumn;
    @FXML private TableColumn<Appointment, Date> appointmentStartColumn;
    @FXML private TableColumn<Appointment, LocalTime> appointmentStartTimeColumn;
    @FXML private TableColumn<Appointment, Date> appointmentEndColumn;
    @FXML private TableColumn<Appointment, LocalTime> appointmentEndTimeColumn;
    @FXML private TableColumn<Appointment, Integer> customerIDColumn;

    private PreparedStatement preparedStatement;
    private Resultset resultset;
    private Connection connection = DBConnection.getConnection();

    private ObservableList<Model.Appointment> appointmentsObservableList = FXCollections.observableArrayList();
    private ObservableList<Model.Appointment> appointmentListForContact1 = FXCollections.observableArrayList();
     private ObservableList<Model.Appointment> appointmentListForContact2 = FXCollections.observableArrayList();
     private ObservableList<Model.Appointment> appointmentListForContact3 = FXCollections.observableArrayList();


    private appointmentDAO appointmentDAO = new appointmentDAO();

    public ReportControllerScheduleForContacts() throws SQLException {
        appointmentsObservableList = appointmentDAO.getAllAppointments();
    }

     /**
      * Populates ReportScheduleForContacts.fxml screen with info
      * @param url
      * @param resourceBundle
      */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Write initializer report for contact schedule to populate initialize data (see ReportControllerTotalAppointments for reference)

        contactScheduleTableView.setItems(appointmentsObservableList);
        contactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("FormattedStartDateTime"));
        appointmentStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("StartTimeLocally"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("FormattedEndDate"));
        appointmentEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("EndTimeLocally"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

     /**
      * Returns an observable list for all appointments in the database for all contacts
      * @return appointmentsObservableList
      */
    private ObservableList<Appointment> observableListForContactAppointments() {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM appointments;");
            resultset = (Resultset) preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsObservableList;
    }

     /**
      * Returns the user to the Report.fxml screen
      * @param event
      */
    public void moveToReportMenu(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/Report.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /**
      * Returns all appointments for contact 1 (Anika Costa) in the database
      * @param event
      */
    public void filterAppointmentsByContact1(javafx.event.ActionEvent event){
        try {
            appointmentDAO.getAppointmentsBySpecificContact(1);

            if(!appointmentListForContact1.isEmpty()){
                appointmentListForContact1.clear();
            }

            for(Appointment appointment : appointmentsObservableList){
                if(appointment.getContactID() == 1){
                    appointmentListForContact1.add(appointment);
                }
            }

            contactScheduleTableView.setItems(appointmentListForContact1);
            contactScheduleTableView.getSelectionModel().selectFirst();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     /**
      * Returns all appointments for contact 2 (Daniel Garcia) in the database
      * @param event
      */
     public void filterAppointmentsByContact2(javafx.event.ActionEvent event){
         try {
             appointmentDAO.getAppointmentsBySpecificContact(2);

             if(!appointmentListForContact2.isEmpty()){
                 appointmentListForContact2.clear();
             }

             for(Appointment appointment : appointmentsObservableList){
                 if(appointment.getContactID() == 2){
                     appointmentListForContact2.add(appointment);
                 }
             }

             contactScheduleTableView.setItems(appointmentListForContact2);
             contactScheduleTableView.getSelectionModel().selectFirst();

         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

     /**
      * Returns all appointments for contact 3 (Li Lee) in the database
      * @param event
      */
     public void filterAppointmentsByContact3(javafx.event.ActionEvent event){
         try {
             appointmentDAO.getAppointmentsBySpecificContact(3);

             if(!appointmentListForContact3.isEmpty()){
                 appointmentListForContact3.clear();
             }

             for(Appointment appointment : appointmentsObservableList){
                 if(appointment.getContactID() == 3){
                     appointmentListForContact3.add(appointment);
                 }
             }

             contactScheduleTableView.setItems(appointmentListForContact3);
             contactScheduleTableView.getSelectionModel().selectFirst();

         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

     /**
      * Returns an observable list for all appointments in the database for all contacts
      * @param event
      */
     public void unfilteredAppointmentsList(javafx.event.ActionEvent event){
         contactScheduleTableView.setItems(appointmentsObservableList);
         contactScheduleTableView.getSelectionModel().selectFirst();

     }
}