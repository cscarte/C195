package DAO.Interfaces;

import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Interfaces are by default publicly accessible.
 */
public interface appointmentDAOInterface {

    /**
     * Interface to returns an observable list containing all appointments in the database in the appointmentDAO DAO.
     * @return ObservableList<Appointment>
     * @throws SQLException
     */
    @FXML
    ObservableList<Appointment> getAllAppointments() throws SQLException;

    /**
     * Interface to returns appointment by a specific by the appointment ID in the appointmentDAO DAO.
     * @param appointmentID
     * @return
     * @throws SQLException
     */
    @FXML
    Appointment getAppointmentByAppointmentID (int appointmentID) throws SQLException;

    /**
     * Interface to returns an observable list by a specific appointment type in the appointmentDAO DAO
     * @param appointmentType
     * @return ObservableList<Appointment>
     * @throws SQLException
     */
    @FXML
    ObservableList<Appointment> getAppointmentByTypeFromString (String appointmentType) throws SQLException;

    /**
     * Interface to return an observable list that shows in the appointmentDAO DAO
     * appointments that are upcoming within a month of the current date.
     * @return ObservableList<Appointment>
     * @throws SQLException
     */
    @FXML
    ObservableList<Appointment> getAppointmentsByCurrentMonth() throws SQLException;

    /**
     * Interface to returns an observable list that shows appointments assigned to a specific contact in the appointmentDAO DAO.
     * @param contactID
     * @return ObservableList<Appointment>
     * @throws SQLException
     */
    @FXML
    ObservableList<Appointment> getAppointmentsBySpecificContact(int contactID) throws SQLException;

    /**
     * Interface to return an observable list that shows appointments assigned to a specific user in the appointmentDAO DAO.
     * @param userID
     * @return ObservableList<Appointment>
     * @throws SQLException
     */
    @FXML
    ObservableList<Appointment> getAppointmentsBySpecificUser(int userID) throws SQLException;

    /**
     * Interface to add appointment to the database in the appointmentDAO DAO.
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


    @FXML void addAppointment(String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, LocalDateTime appointmentCreateDate, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException;

    /**
     * Interface to delete a specific appointment by the appointment ID in the appointmentDAO DAO.
     * @param appointmentID
     * @throws SQLException
     */
    @FXML
    void deleteAppointment(int appointmentID) throws SQLException;

    /**
     * Interface to check for and delete any appointments that are assigned to a customer in the appointmentDAO DAO.
     * @param customerID
     * @throws SQLException
     */
    @FXML
    void deleteAssignedAppointmentsByCustomerID(int customerID) throws SQLException;

    /**
     * Interface to update appointments in the appointmentDAO DAO
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


    @FXML void updateAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customerID, int userID, int contactID) throws SQLException;

    @FXML
    ArrayList generateTypeMonthReport();


}
