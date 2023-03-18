package DAO;

import DAO.Interfaces.appointmentDAOInterface;
import Model.Appointment;
import Model.ReportMonthType;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;

public class appointmentDAO implements appointmentDAOInterface {

    private String sqlStatementSelectAllAppointments = "SELECT * FROM appointments";

    private String sqlStatementSelectAppointmentsByContact = "SELECT * FROM appointments WHERE Contact_ID=? ORDER BY START ASC";

    private String sqlStatementSelectAppointmentsByUser = "SELECT * FROM appointments WHERE User_ID=? ORDER BY START ASC";

    private String sqlStatementSelectAppointmentsByType = "SELECT * FROM appointments WHERE type=?";
    private String sqlStatementInsertAppointment = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private String sqlStatementUpdateAppointments = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
    private String sqlStatementSelectAppointmentByID = "SELECT * FROM appointments WHERE Appointment_ID= ";

    private String sqlStatementDeleteAppointmentByID = "DELETE FROM appointments WHERE Appointment_ID=";

    private String sqlStatementDeleteAppointmentsAssociatedWithCustomer = "DELETE FROM appointments WHERE Customer_ID=";

    private String sqlStatementGenerateTypeMonthReport = "Select month(start) as month, type, count(*) as total from appointments GROUP BY month, type;";

    private String sqlStatementGenerateContactScheduleReport = "Select year(start) as year, contacts.Contact_Name as Contact, count(*) as total from appointments JOIN contacts on contacts.Contact_ID=appointments.Contact_ID ORDER BY contact, year;";

    private static ZoneId businessZoneID = ZoneId.of("America/New_York");
    private static ZoneOffset businessZoneOffset = businessZoneID.getRules().getOffset(Instant.now());
    private static ZoneId userZoneID = ZoneId.systemDefault();
    private static ZoneOffset userZoneOffset = userZoneID.getRules().getOffset(Instant.now());

    private static ZoneId zoneId = ZoneId.of("UTC");
    private static ZoneOffset utcZoneOffset = zoneId.getRules().getOffset(Instant.now());
    private Connection connection = DBConnection.getConnection();

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @FXML
    public ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();

    @FXML
    public ObservableList<Appointment> appointmentWithinAMonthObservableList = FXCollections.observableArrayList();

    /**
     * AppointmentDAO constructor.
     * @throws SQLException
     */
    public appointmentDAO() throws SQLException{}

    /**
     * Gathers user input data for the appointment
     * @return
     * @throws SQLException
     */
    private Appointment getAppointmentInfo() throws SQLException{
        int appointmentID = resultSet.getInt("Appointment_ID");
        String appointmentTitle = resultSet.getString("Title");
        String appointmentDescription = resultSet.getString("Description");
        String appointmentLocation = resultSet.getString("Location");
        String appointmentType = resultSet.getString("Type");

        Timestamp timeStart = resultSet.getTimestamp("Start");
        ZonedDateTime zonedDateTimeUTC = timeStart.toLocalDateTime().atZone(zoneId);
        ZonedDateTime zonedDateTimeStart = zonedDateTimeUTC.withZoneSameInstant(userZoneID);
        LocalDateTime appointmentStart = zonedDateTimeStart.toLocalDateTime();

        Timestamp timeEnd = resultSet.getTimestamp("End");
        ZonedDateTime zonedDateTimeUTCEnd = timeEnd.toLocalDateTime().atZone(zoneId);
        ZonedDateTime zonedDateTimeEnd = zonedDateTimeUTCEnd.withZoneSameInstant(userZoneID);
        LocalDateTime appointmentEnd = zonedDateTimeEnd.toLocalDateTime();


        LocalDateTime appointmentCreateDate = resultSet.getTimestamp("Create_Date").toLocalDateTime();
        String createdBy = resultSet.getString("Created_By");
        LocalDateTime lastUpdated = resultSet.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = resultSet.getString("Last_Updated_By");
        int customerID = resultSet.getInt("Customer_ID");
        int userID = resultSet.getInt("User_ID");
        int contactID = resultSet.getInt("Contact_ID");

        Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentStart, appointmentEnd, appointmentCreateDate, createdBy, lastUpdated, lastUpdatedBy, customerID,userID, contactID);


        return appointment;
    }

    /**
     * Returns an observable list for all appointments in database.
     * @return appointmentObservableList
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointment> getAllAppointments() throws SQLException {
        preparedStatement = connection.prepareStatement(sqlStatementSelectAllAppointments);
        resultSet = preparedStatement.executeQuery();
        try {
            while(resultSet.next()){
                Appointment appointment = getAppointmentInfo();
                appointmentObservableList.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println("The issue is in getAllAppointments");
            e.printStackTrace();
        }
        return appointmentObservableList;
    }

    /**
     * Returns an appointment by the appointment ID from the database.
     * @param appointmentID
     * @return getAppointmentInformation
     * @return null
     * @throws SQLException
     */
    @Override
    public Appointment getAppointmentByAppointmentID(int appointmentID) throws SQLException {
        String sqlStatementComplete = sqlStatementSelectAppointmentByID + appointmentID;
        preparedStatement = connection.prepareStatement(sqlStatementComplete);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            return getAppointmentInfo();
        }
        return null;
    }

    /**
     * Returns all appointments of an appointment type from a String value
     * @param appointmentType
     * @return appointmentObservableListByType
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointment> getAppointmentByTypeFromString(String appointmentType) throws SQLException {
        ObservableList<Appointment> appointmentObservableListByType = FXCollections.observableArrayList();
        preparedStatement = connection.prepareStatement(sqlStatementSelectAppointmentsByType);
        preparedStatement.setString(1, appointmentType);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Appointment appointment = getAppointmentInfo();
            appointmentObservableListByType.add(appointment);
        }
        return appointmentObservableListByType;
    }

    /**
     * Returns all appointments within a month of the user's current login date.
     * @return appointmentWithinAMonthObservableList
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointment> getAppointmentsByCurrentMonth() throws SQLException {
        String sqlStatementGetAppointmentsByCurrentMonth = "SELECT * FROM appointments WHERE MONTH(start)=MONTH(current_date()) AND YEAR(start)=year(current_date())";
        preparedStatement = connection.prepareStatement(sqlStatementGetAppointmentsByCurrentMonth);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Appointment appointment = getAppointmentInfo();
            appointmentWithinAMonthObservableList.add(appointment);
        }
        return appointmentWithinAMonthObservableList;
    }

    /**
     * Returns all appointments assigned to a specific contact by the contact's ID in the database.
     * @param contactID
     * @return contactAppointmentObservableList
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointment> getAppointmentsBySpecificContact (int contactID) throws SQLException {
        ObservableList<Appointment> contactAppointmentObservableList = FXCollections.observableArrayList();
        preparedStatement = connection.prepareStatement(sqlStatementSelectAppointmentsByContact);
        preparedStatement.setInt(1, contactID);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            Appointment appointment = getAppointmentInfo();
            contactAppointmentObservableList.add(appointment);
        }
        return contactAppointmentObservableList;
    }

    /**
     * Returns all appointments assigned to a user by the user's ID in the database.
     * @param userID
     * @return userAppointmentObservableList
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointment> getAppointmentsBySpecificUser(int userID) throws SQLException {
        ObservableList<Appointment> userAppointmentObservableList = FXCollections.observableArrayList();
        preparedStatement = connection.prepareStatement(sqlStatementSelectAppointmentsByUser);
        preparedStatement.setInt(1, userID);

        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Appointment appointment = getAppointmentInfo();
            userAppointmentObservableList.add(appointment);
        }
        return userAppointmentObservableList;
    }

    /**
     * Adds appointment to the database utilizing appointmentDAOInterface
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentType
     * @param appointmentStart
     * @param appointmentEnd
     * @param appointmentCreateDate
     * @param createdBy
     * @param lastUpdated
     * @param lastUpdatedBy
     * @param customerID
     * @param userID
     * @param contactID
     * @throws SQLException
     */
    @Override
    public void addAppointment(String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, LocalDateTime appointmentCreateDate, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {

        preparedStatement = connection.prepareStatement(sqlStatementInsertAppointment);
        preparedStatement.setString(1, appointmentTitle);
        preparedStatement.setString(2, appointmentDescription);
        preparedStatement.setString(3, appointmentLocation);
        preparedStatement.setString(4, appointmentType);

        Timestamp timeStart = Timestamp.valueOf(appointmentStart);
        ZonedDateTime zonedDateTimeStart = timeStart.toLocalDateTime().atZone(userZoneID);
        ZonedDateTime utcZonedDateTimeStart = zonedDateTimeStart.withZoneSameInstant(zoneId);
        LocalDateTime appointmentStartLDT = utcZonedDateTimeStart.toLocalDateTime();
        preparedStatement.setTimestamp(5, Timestamp.valueOf(appointmentStartLDT));

        Timestamp timeEnd = Timestamp.valueOf(appointmentEnd);
        ZonedDateTime zonedDateTimeEnd = timeEnd.toLocalDateTime().atZone(userZoneID);
        ZonedDateTime utcZonedDateTimeEnd = zonedDateTimeEnd.withZoneSameInstant(zoneId);
        LocalDateTime appointmentEndLDT = utcZonedDateTimeEnd.toLocalDateTime();
        preparedStatement.setTimestamp(6, Timestamp.valueOf(appointmentEndLDT));

        preparedStatement.setTimestamp(7, Timestamp.valueOf(appointmentCreateDate));
        preparedStatement.setString(8, createdBy);
        preparedStatement.setTimestamp(9, Timestamp.valueOf(lastUpdated));
        preparedStatement.setString(10, lastUpdatedBy);
        preparedStatement.setInt(11, customerID);
        preparedStatement.setInt(12, userID);
        preparedStatement.setInt(13, contactID);
        preparedStatement.execute();

    }

    /**
     * Deletes an appointment based on its appointment ID in the database.
     * @param appointmentID
     * @throws SQLException
     */
        @Override
    public void deleteAppointment(int appointmentID) throws SQLException {
        String completeSqlStatementDeleteByAppointmentID = sqlStatementDeleteAppointmentByID + appointmentID;
        preparedStatement = connection.prepareStatement(completeSqlStatementDeleteByAppointmentID);
        preparedStatement.execute();
    }

    /**
     * Deletes all appointments associated with a customer by the customer's ID in the database.
     * @param customerID
     * @throws SQLException
     */
    @Override
    public void deleteAssignedAppointmentsByCustomerID(int customerID) throws SQLException {
        String completeSqlStatementDeleteAppointmentsByCustomer = sqlStatementDeleteAppointmentsAssociatedWithCustomer + customerID;

        preparedStatement = connection.prepareStatement(completeSqlStatementDeleteAppointmentsByCustomer);

        preparedStatement.execute();
    }

    /**
     * Updates an appointment in database based on the appointment's ID.
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentType
     * @param appointmentStart
     * @param appointmentEnd
     * @param customerID
     * @param userID
     * @param contactID
     * @throws SQLException
     */
    @Override
    public void updateAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customerID, int userID, int contactID) throws SQLException {
        preparedStatement = connection.prepareStatement(sqlStatementUpdateAppointments);
        String lastUpdatedBy = "test";
        LocalDateTime lastUpdate = LocalDateTime.now();
        try{
            preparedStatement.setString(1, appointmentTitle);
            preparedStatement.setString(2, appointmentDescription);
            preparedStatement.setString(3, appointmentLocation);
            preparedStatement.setString(4, appointmentType);

            Timestamp timeStart = Timestamp.valueOf(appointmentStart);
            ZonedDateTime zonedDateTime = timeStart.toLocalDateTime().atZone(userZoneID);
            ZonedDateTime utcZonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
            LocalDateTime localDateTimeToUTC = utcZonedDateTime.toLocalDateTime();
            preparedStatement.setTimestamp(5, Timestamp.valueOf(localDateTimeToUTC));

            Timestamp timeEnd = Timestamp.valueOf(appointmentEnd);
            ZonedDateTime zonedDateTimeEnd = timeEnd.toLocalDateTime().atZone(userZoneID);
            ZonedDateTime utcZonedDateTimeEnd = zonedDateTimeEnd.withZoneSameInstant(zoneId);
            LocalDateTime localDateTimeToUTCEnd = utcZonedDateTimeEnd.toLocalDateTime();
            preparedStatement.setTimestamp(6, Timestamp.valueOf(localDateTimeToUTCEnd));

            preparedStatement.setTimestamp(7, Timestamp.valueOf(lastUpdate));

            preparedStatement.setString(8, lastUpdatedBy);
            preparedStatement.setInt(9, customerID);
            preparedStatement.setInt(10, userID);
            preparedStatement.setInt(11, contactID);
            preparedStatement.setInt(12, appointmentID);

            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList generateTypeMonthReport(){
        try{
            preparedStatement = connection.prepareStatement(sqlStatementGenerateTypeMonthReport);
            resultSet = preparedStatement.executeQuery();

            ArrayList<ReportMonthType> typeMonthReport = new ArrayList<>();

            while(resultSet.next()){
                ReportMonthType tableRow = new ReportMonthType(resultSet.getString("month"), resultSet.getString("type"), resultSet.getInt("total"));
                typeMonthReport.add(tableRow);
            }

            return typeMonthReport;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}