package Controller;

import DAO.appointmentDAO;
import DAO.contactDAO;
import DAO.customerDAO;
import DAO.userDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import Utilities.moveScreens;
import Utilities.TimeConversion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentControllerUpdate implements Initializable {
    @FXML private TextField appointmentIDTextField;
    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField appointmentESTStart;
    @FXML private TextField appointmentESTEnd;

    @FXML private TextField userTextField;

    @FXML private ComboBox<Customer> customerNameComboBox;
    @FXML private ComboBox<User> userComboBox;
    @FXML private ComboBox<Contact> contactComboBox;
    @FXML private ComboBox<LocalTime> startTimeComboBox;
    @FXML private ComboBox<LocalTime> endTimeComboBox;

    @FXML private ComboBox<ZonedDateTime> startTimeComboBoxZDT;

    @FXML private ComboBox<ZonedDateTime> endTimeComboBoxZDT;
    @FXML private DatePicker dateDatePicker;

    @FXML private Button cancelButton;

    private Contact contact;
    private Customer customer;

    private User user;
    private Appointment selectedAppointmentToUpdate = AppointmentController.appointmentControllerToUpdate;

    private appointmentDAO appointmentDAO = new appointmentDAO();
    private contactDAO contactDAO = new contactDAO();
    private customerDAO customerDAO = new customerDAO();
    private userDAO userDAO = new userDAO();
    private static ZoneId zoneId = ZoneId.of("UTC");
    private static ZoneOffset utcZoneOffset = zoneId.getRules().getOffset(Instant.now());

    private static ZoneId businessZoneID = ZoneId.of("America/New_York");
    private static ZoneOffset businessZoneOffset = businessZoneID.getRules().getOffset(Instant.now());
    private static ZoneId userZoneID = ZoneId.systemDefault();
    private static ZoneOffset userZoneOffset = userZoneID.getRules().getOffset(Instant.now());

    private static ZoneId databaseZoneID = ZoneId.of("UTC");
    private static ZoneOffset databaseZoneOffset = databaseZoneID.getRules().getOffset(Instant.now());

    private LocalTime userLocalTime = LocalTime.now(businessZoneID);
    private LocalTime businessLocalTime = LocalTime.now(businessZoneID);

    private long hoursBetween = ChronoUnit.HOURS.between(userLocalTime, businessLocalTime);

    private final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");

    private ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();



    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private String appointmentTypeTextField;

    private int userID;


    private LocalDateTime appointmentStartDateTime;
    private LocalDateTime appointmentStartDateTime2;
    private LocalDateTime appointmentEndDateTime;

    private LocalDate appointmentDate = LocalDate.now();

    private LocalTime appointmentStartTimeSelection = LocalTime.of(0,0);
    private LocalTime appointmentEndTimeSelection = LocalTime.of(23,0);
    private LocalTime appointmentStartTime2;

    private ZonedDateTime appointmentStartTimeZDT;
    private ZonedDateTime appointmentStartTimeZDT2;

    private ZonedDateTime appointmentEndTimeZDT;
    private ZonedDateTime appointmentEndTimeZDT2;

    private OffsetDateTime offsetDateTime;

    private int timeZoneDifference= ((userZoneOffset.getTotalSeconds()/3600) - businessZoneOffset.getTotalSeconds()/3600);

    public AppointmentControllerUpdate() throws SQLException {
    }

    /**
     * Populates data on the AppointmentUpdate.fxml screen.
     *
     * The dateDatePicker.setDayCellFactory(datePicker -> new DateCell(){...} Lambda is used to prevent users from scheduling a date in the past.
     * Users can only select the current date or future dates.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        int timeZoneDifference = ((userZoneOffset.getTotalSeconds()/3600) - businessZoneOffset.getTotalSeconds()/3600);

        int utcZoneDifference = ((businessZoneOffset.getTotalSeconds() / 3600) - utcZoneOffset.getTotalSeconds() / 3600);

        appointmentIDTextField.setText(String.valueOf(selectedAppointmentToUpdate.getAppointmentID()));

        userTextField.setText(String.valueOf(selectedAppointmentToUpdate.getUserID()));


        try{
            customerNameComboBox.setItems(customerDAO.customerGetAllObservableList());
            customerNameComboBox.getSelectionModel().select(customerDAO.getCustomer(selectedAppointmentToUpdate.getCustomerID()));

            contactComboBox.setItems(contactDAO.getContacts());
            contactComboBox.getSelectionModel().select(contactDAO.getContactByID(selectedAppointmentToUpdate.getContactID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        titleTextField.setText(selectedAppointmentToUpdate.getAppointmentTitle());
        typeTextField.setText(selectedAppointmentToUpdate.getAppointmentType());
        descriptionTextField.setText(selectedAppointmentToUpdate.getAppointmentDescription());
        locationTextField.setText(selectedAppointmentToUpdate.getAppointmentLocation());

        dateDatePicker.setValue(selectedAppointmentToUpdate.getAppointmentStartDateTime().toLocalDate());

        //Lamba used to prevent users from scheduling an appointment at a date in the past. Lambda located in AppointmentControllerUpdate
        dateDatePicker.setDayCellFactory(datePicker -> new DateCell(){
            public void updateItem(LocalDate localDate, boolean b){
                super.updateItem(localDate, b);

                LocalDate currentDate = LocalDate.now();
                setDisable(localDate.compareTo(currentDate) < 0 || b);
            }
        });
        appointmentDate = dateDatePicker.getValue();

        TimeConversion.populateTimeSelectionComboBox(startTimeComboBox, appointmentStartTimeSelection, appointmentEndTimeSelection);
        TimeConversion.populateTimeSelectionComboBox(endTimeComboBox, appointmentStartTimeSelection, appointmentEndTimeSelection);

        startTimeComboBox.getSelectionModel().select(selectedAppointmentToUpdate.getAppointmentStartDateTime().toLocalTime());
        endTimeComboBox.getSelectionModel().select(selectedAppointmentToUpdate.getAppointmentEndDateTime().toLocalTime());


        //populateESTStartTime();
    }

    /**
     * Gather's input data from all text fields and combo boxes
     */
    private void gatherInputInfo(){
        customer = customerNameComboBox.getSelectionModel().getSelectedItem();
        System.out.println("customer gathered");
        contact = contactComboBox.getSelectionModel().getSelectedItem();
        System.out.println("contact gathered");
        userID = Integer.parseInt(userTextField.getText());


        appointmentTitle = titleTextField.getText().trim();
        System.out.println("appointmentTitle gathered");
        appointmentDescription = descriptionTextField.getText().trim();
        System.out.println("appointmentDescription gathered");
        appointmentLocation = locationTextField.getText().trim();
        System.out.println("appointmentLocation gathered");
        appointmentType = typeTextField.getText().trim();
        System.out.println("appointmentType gathered");

        appointmentDate = dateDatePicker.getValue();
        System.out.println("appointmentDate gathered");
        appointmentStartTimeSelection = startTimeComboBox.getValue();
        System.out.println("appointmentStartTime gathered");
        appointmentEndTimeSelection = endTimeComboBox.getValue();
        System.out.println("appointmentEndTime gathered");

        appointmentStartDateTime = LocalDateTime.of(LocalDate.parse(appointmentDate.toString(), formatDate), LocalTime.parse(appointmentStartTimeSelection.toString(), formatTime));
        appointmentEndDateTime = LocalDateTime.of(LocalDate.parse(appointmentDate.toString(), formatDate), LocalTime.parse(appointmentEndTimeSelection.toString(), formatTime));

    }

    /**
     * Checks to make sure appointment for a customer does not conflict with another appointment for that customer.
     * @return true
     */
    private boolean checkExistingAppointmentTimes(){
        try{
            appointmentStartDateTime = LocalDateTime.of(appointmentDate, appointmentStartTimeSelection);
            appointmentEndDateTime = LocalDateTime.of(appointmentDate, appointmentEndTimeSelection);
            appointmentObservableList = appointmentDAO.getAllAppointments();

            for(Appointment appointment : appointmentObservableList){
                if(appointment.getAppointmentID() != selectedAppointmentToUpdate.getAppointmentID()){
                    if (customer.getCustomerID() == appointment.getCustomerID()){
                        if(appointmentStartDateTime.isAfter(appointment.getAppointmentStartDateTime().minusMinutes(1)) && appointmentStartDateTime.isBefore(appointment.getAppointmentEndDateTime())){
                            Alert conflictingAppointments = new Alert(Alert.AlertType.ERROR);
                            conflictingAppointments.setTitle("New appointment conflicts with another appointment");
                            conflictingAppointments.setContentText("New appointment conflicts with another appointment");
                            conflictingAppointments.showAndWait();
                            return false;
                        } else if (appointmentEndDateTime.isAfter(appointment.getAppointmentStartDateTime().minusMinutes(1)) && appointmentEndDateTime.isBefore(appointment.getAppointmentEndDateTime())) {
                            Alert conflictingAppointments = new Alert(Alert.AlertType.ERROR);
                            conflictingAppointments.setTitle("New appointment conflicts with another appointment");
                            conflictingAppointments.setContentText("New appointment conflicts with another appointment");
                            conflictingAppointments.showAndWait();
                            return false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Checks that appointment times on the user's timezone have been converted and compared with the database timezone.
     * @return TimeConversion.compareWithBusinessHours(selectedStartDateTime) && TimeConversion.compareWithBusinessHours(selectedEndDateTime)
     */
    private boolean checkNewAppointmentTimes(){
        LocalDateTime selectedStartDateTime = LocalDateTime.of(appointmentDate, appointmentStartTimeSelection);
        LocalDateTime selectedEndDateTime = LocalDateTime.of(appointmentDate, appointmentEndTimeSelection);
        return TimeConversion.compareLocalToBusinessHours(selectedStartDateTime) && TimeConversion.compareLocalToBusinessHours(selectedEndDateTime);
    }

    /**
     * First if statement takes all info from gatherInputInfo and checks for any text fields / combo boxes not filled out
     * !checkExistingAppointmentTimes checks appointment times to make sure that are valid (start date / time is not after end date / time).
     * !checkNewAppointmentTimes checks that appointment times on the user's timezone have been converted and compared with the database timezone.
     * @return true
     */
    private boolean checkForInfo(){
        gatherInputInfo();

        if (appointmentTitle.isBlank() || appointmentDescription.isBlank() || appointmentLocation.isBlank() || appointmentType.isBlank() || customer == null || contact == null ||  appointmentDate == null || startTimeComboBox == null || endTimeComboBox == null){
            Alert incompleteAppointmentInfo = new Alert(Alert.AlertType.ERROR);
            incompleteAppointmentInfo.setTitle("Not all info is filled out for the appointment");
            incompleteAppointmentInfo.setContentText("Not all info is filled out for the appointment");
            incompleteAppointmentInfo.showAndWait();
            return false;
        } else if (appointmentStartTimeSelection.equals(appointmentEndTimeSelection) || appointmentStartTimeSelection.isAfter(appointmentEndTimeSelection)) {
            Alert invalidStartTime = new Alert(Alert.AlertType.ERROR);
            invalidStartTime.setTitle("Appointment's starting time is invalid");
            invalidStartTime.setContentText("Appointment's starting time is invalid");
            invalidStartTime.showAndWait();
            return false;
        } else if (!checkExistingAppointmentTimes()) {
            return false;
        } else if (!checkNewAppointmentTimes()) {
            Alert invalidAppointmentTime = new Alert(Alert.AlertType.ERROR);
            invalidAppointmentTime.setTitle("Invalid time selected: Business Hours are between 8am to 10pm EST");
            invalidAppointmentTime.setContentText("Invalid time selected: Business Hours are between 8am to 10pm EST");


            System.out.println("The selected appointment start time at the current timezone is: "+appointmentStartTimeSelection.toString());
            System.out.println("The selected appointment start time at the business timezone is: "+appointmentStartTimeSelection.toString());

            invalidAppointmentTime.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Opens up the view Appointment screen on Appointment.fxml
     * @param event
     */
    public void moveToAppointmentMenu(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/Appointment.fxml"));
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
     * Saves appointment info input and moves back to the view Appointment screen on Appointment.fxml
     * @param event
     */
    @FXML public void updateAppointmentButtonAction(javafx.event.ActionEvent event) {
        if(checkForInfo()){
            try {


                Timestamp timestamp = Timestamp.valueOf(appointmentStartDateTime);
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                ZonedDateTime zonedDateTime = localDateTime.atZone(userZoneID);
                ZonedDateTime estUTC = zonedDateTime.withZoneSameInstant(businessZoneID);
                LocalDateTime localDateTime1 = estUTC.toLocalDateTime();

                Timestamp timestamp1 = Timestamp.valueOf(appointmentEndDateTime);
                LocalDateTime localDateTime2 = timestamp1.toLocalDateTime();
                ZonedDateTime zonedDateTime2 = localDateTime2.atZone(userZoneID);
                ZonedDateTime estUTC2 = zonedDateTime2.withZoneSameInstant(businessZoneID);
                LocalDateTime localDateTime3 = estUTC2.toLocalDateTime();



                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setHeaderText("Confirm appointment time");
                confirmationAlert.setContentText("The appointment start date and time selected is: "+TimeConversion.formatZonedDateTime(appointmentStartDateTime.atZone(userZoneID))+". \n\nThe end date and time selected is: "+TimeConversion.formatZonedDateTime(appointmentEndDateTime.atZone(userZoneID))+"\n\n--------------------------------------------------\n\n The start date and time in business hours is: "+ TimeConversion.formatLocalTimeFromDate(localDateTime1)+"\n\n The end date and time selected in business hours is: "+ TimeConversion.formatLocalTimeFromDate(localDateTime3));

                Optional<ButtonType> choice = confirmationAlert.showAndWait();
                if(choice.get() == ButtonType.OK){
                    appointmentDAO.updateAppointment(selectedAppointmentToUpdate.getAppointmentID(), appointmentTitle, appointmentDescription, appointmentLocation, appointmentType,
                            appointmentStartDateTime, appointmentEndDateTime, customer.getCustomerID(), Login.userID, contact.getContactID());
                System.out.println("Appointment has been saved!");
                moveScreens.moveScreen(event, "Appointment.fxml");
                }
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}