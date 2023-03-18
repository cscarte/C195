package Controller;

import DAO.appointmentDAO;
import DAO.contactDAO;
import DAO.customerDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import Utilities.TimeConversion;
import Utilities.moveScreens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentControllerAdd implements Initializable {

    @FXML private TextField appointmentIDTextField;
    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;

    @FXML private TextField typeTextField;
    @FXML private TextField userNameTextField;

    @FXML private ComboBox<Customer> customerNameComboBox;
    @FXML private ComboBox<Contact> contactComboBox;    @FXML private ComboBox<LocalTime> startTimeComboBox;
    @FXML private ComboBox<LocalTime> endTimeComboBox;

    @FXML private DatePicker dateDatePicker;

    @FXML private Button cancelButton;

    private LocalDate appointmentDate;
    private LocalDateTime appointmentStartDateTime;
    private LocalDateTime appointmentEndDateTime;

    private ZonedDateTime appointmentStartDateTimeZDT;
    private ZonedDateTime appointmentEndDateTimeZDT;

    private static ZoneId businessZoneID = ZoneId.of("America/New_York");
    private static ZoneOffset businessZoneOffset = businessZoneID.getRules().getOffset(Instant.now());
    private static ZoneId userZoneID = ZoneId.systemDefault();
    private static ZoneOffset userZoneOffset = userZoneID.getRules().getOffset(Instant.now());

    private static ZoneId zoneId = ZoneId.of("UTC");
    private static ZoneOffset utcZoneOffset = zoneId.getRules().getOffset(Instant.now());

    private LocalTime startTime = LocalTime.of(0,0);
    private LocalTime endTime = LocalTime.of(23, 0);

    private ZonedDateTime startTimeZoneDateTime = ZonedDateTime.now().withZoneSameInstant(businessZoneID);

    private DAO.appointmentDAO appointmentDAO = new appointmentDAO();
    private DAO.contactDAO contactDAO = new contactDAO();
    private DAO.customerDAO customerDAO = new customerDAO();

    private ObservableList<Appointment> appointmentControllerObservableList = FXCollections.observableArrayList();
    private ObservableList<User> userObservableList = FXCollections.observableArrayList();

    private User user;

    private int userID;
    private Customer customer;
    private Contact contact;
    private String appointmentType;

    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;

    private final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");

    private int timeZoneDifference= ((userZoneOffset.getTotalSeconds()/3600) - businessZoneOffset.getTotalSeconds()/3600);

    public AppointmentControllerAdd() throws SQLException {

    }

    /**
     * Populates screen with info from database
     *
     * The dateDatePicker.setDayCellFactory(datePicker -> new DateCell(){...} Lambda is used to prevent users from scheduling a date in the past.
     * Users can only select the current date or future dates.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println("test"+ " has logged in");
        contactComboBox.setItems(contactDAO.getContactObservableList());
        //System.out.println(startTimeZoneDateTime);
        userNameTextField.setText(String.valueOf(Login.userID));

        try{
            customerNameComboBox.setItems(customerDAO.customerGetAllObservableList());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dateDatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            {
                dateDatePicker.setPromptText("Select a date");
            }

            public String toString(LocalDate localDate) {
                if(localDate != null){
                    return dateTimeFormatter.format(localDate);
                }
                return " ";
            }

            @Override
            public LocalDate fromString(String s) {
                if(s != null && !s.isEmpty()){
                    return LocalDate.parse(s, dateTimeFormatter);
                } else{
                    return null;
                }
            }
        });

        //Lamba used to prevent users from scheduling an appointment at a date in the past. Lambda located in AppointmentControllerAdd
        dateDatePicker.setDayCellFactory(datePicker -> new DateCell(){
            //Lamba used to prevent users from scheduling an appointment at a date in the past.
            public void updateItem(LocalDate localDate, boolean b){
                super.updateItem(localDate, b);
                LocalDate todayDate = LocalDate.now();
                setDisable(localDate.compareTo(todayDate) < 0 || b);
            }
        });
        dateDatePicker.setValue(LocalDate.now());


        TimeConversion.populateTimeSelectionComboBox(startTimeComboBox, startTime, endTime);
        TimeConversion.populateTimeSelectionComboBox(endTimeComboBox, startTime, endTime);

        startTimeComboBox.getSelectionModel().selectFirst();
        endTimeComboBox.getSelectionModel().select(1);

    }

    /**
     * Gathers data input by the user from text fields and combo boxes.
     */
    private void gatherInfo(){
        customer = customerNameComboBox.getSelectionModel().getSelectedItem();
        contact = contactComboBox.getSelectionModel().getSelectedItem();

        appointmentTitle = titleTextField.getText().trim();
        appointmentDescription = descriptionTextField.getText().trim();
        appointmentLocation = locationTextField.getText().trim();
        appointmentType = typeTextField.getText().trim();

        appointmentDate = dateDatePicker.getValue();
        startTime = startTimeComboBox.getValue();
        endTime = endTimeComboBox.getValue();

        appointmentStartDateTimeZDT = ZonedDateTime.of(appointmentDate, startTimeComboBox.getValue(), userZoneID);
        System.out.println(appointmentStartDateTimeZDT);
        appointmentEndDateTimeZDT = ZonedDateTime.of(appointmentDate, endTimeComboBox.getValue(), userZoneID);
        System.out.println(appointmentEndDateTimeZDT);
    }

    /**
     * Checks if appointment times are valid (start and end times do not overlap, end times are not before start times, etc.)
     * @return true
     */
    public boolean checkAppointmentTimes(){
        try{
            appointmentStartDateTime = LocalDateTime.of(appointmentDate, startTime);
            appointmentEndDateTime = LocalDateTime.of(appointmentDate, endTime);

            appointmentControllerObservableList = appointmentDAO.getAllAppointments();

            for(Appointment appointment : appointmentControllerObservableList){
                if(appointment.getCustomerID() == customer.getCustomerID()) {
                    if (appointmentStartDateTime.isAfter(appointment.getAppointmentStartDateTime().minusMinutes(1)) && appointmentStartDateTime.isBefore(appointment.getAppointmentEndDateTime())) {
                        Alert invalidAppointmentAlert = new Alert(Alert.AlertType.ERROR);
                        invalidAppointmentAlert.setTitle("Appointment time selected is invalid");
                        invalidAppointmentAlert.setContentText("Appointment time selected is invalid");
                        invalidAppointmentAlert.showAndWait();
                        return false;
                    }
                } else if (appointmentEndDateTime.isAfter(appointment.getAppointmentStartDateTime().minusMinutes(1)) && appointmentEndDateTime.isBefore(appointment.getAppointmentEndDateTime())) {
                    Alert invalidAppointmentAlert = new Alert(Alert.AlertType.ERROR);
                    invalidAppointmentAlert.setTitle("Appointment time selected is invalid");
                    invalidAppointmentAlert.setContentText("Appointment time selected is invalid");
                    invalidAppointmentAlert.showAndWait();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Checks that appointment times on the user's timezone have been converted and compared with the database timezone.
     * @return TimeConversion.compareWithBusinessHours(selectedStartDateTime) && TimeConversion.compareWithBusinessHours(selectedEndDateTime);
     */
    public boolean checkAppointmentTimeAndDate(){
        LocalDateTime selectedStartDateTime = LocalDateTime.of(appointmentDate, startTime);
        LocalDateTime selectedEndDateTime = LocalDateTime.of(appointmentDate, endTime);
        return TimeConversion.compareLocalToBusinessHours(selectedStartDateTime) && TimeConversion.compareLocalToBusinessHours(selectedEndDateTime);
    }



    /**
     * First if statement takes all info from gatherInputInfo and checks for any text fields / combo boxes not filled out
     * !checkAppointmentTimes checks appointment times to make sure that are valid (start date / time is not after end date / time).
     * !checkAppointmentTimeAndDate checks that appointment times on the user's timezone have been converted and compared with the database timezone.
     * @return true
     */
    private boolean checkAppointmentInfo(){
        gatherInfo();
        if(appointmentTitle.isBlank() || appointmentDescription.isBlank() || appointmentLocation.isBlank() || appointmentType.isBlank() || appointmentDate == null || contact == null || customer == null) {
            Alert incompleteAppointmentInfo = new Alert(Alert.AlertType.ERROR);
            incompleteAppointmentInfo.setTitle("Fill out all appointment info");
            incompleteAppointmentInfo.setContentText("Fill out all appointment info");
            incompleteAppointmentInfo.showAndWait();
            return false;
        } else if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            Alert invalidAppointmentTime = new Alert(Alert.AlertType.ERROR);
            invalidAppointmentTime.setTitle("Appointment start time is equal to or after end time");
            invalidAppointmentTime.setContentText("Appointment start time is equal to or after end time");
            invalidAppointmentTime.showAndWait();
            return false;
        } else if (dateDatePicker.getValue() == null) {
            Alert noDateSelected = new Alert(Alert.AlertType.ERROR);
            noDateSelected.setTitle("Appointment date not selected");
            noDateSelected.setContentText("Appointment date not selected");
            noDateSelected.showAndWait();
        } else if (!checkAppointmentTimeAndDate()) {
            Alert invalidAppointmentTime = new Alert(Alert.AlertType.ERROR);
            invalidAppointmentTime.setTitle("Invalid time selected: Business Hours are between 8am to 10pm EST");
            invalidAppointmentTime.setContentText("Invalid time selected: Business Hours are between 8am to 10pm EST");
            invalidAppointmentTime.showAndWait();
            return false;
        } else if (!checkAppointmentTimes()) {
            return false;
        } else {
            appointmentStartDateTime = LocalDateTime.of(appointmentDate, startTime);
            appointmentEndDateTime = LocalDateTime.of(appointmentDate, endTime);
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
    @FXML public void saveButtonAction(ActionEvent event) throws SQLException, IOException{
        if (checkAppointmentInfo()){


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
                appointmentDAO.addAppointment(appointmentTitle, appointmentDescription, appointmentLocation, appointmentType,
                        appointmentStartDateTime, appointmentEndDateTime, LocalDateTime.now(), Login.loggedInUserName, LocalDateTime.now(),
                        Login.loggedInUserName, customer.getCustomerID(), Login.userID, contact.getContactID());
                moveScreens.moveScreen(event, "Appointment.fxml");
                System.out.println("Appointment has been saved!");
            }
        }
    }
}