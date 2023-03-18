package Model;

import DAO.contactDAO;
import DAO.userDAO;
import Utilities.TimeConversion;

import java.sql.Date;
import java.sql.SQLException;
import java.time.*;

public class Appointment {
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime appointmentStart;
    private ZonedDateTime appointmentStartZDT;

    private LocalDateTime appointmentEnd;
    private ZonedDateTime appointmentEndZDT;
    private LocalDateTime appointmentCreateDate;
    private String appointmentCreatedBy;
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;

    private static final ZoneId userZoneID = ZoneId.systemDefault();
    private static final ZoneOffset userZoneOffset = userZoneID.getRules().getOffset(Instant.now());

    private static final ZoneId businessZoneID = ZoneId.of("UTC");
    private static final ZoneOffset utcZoneOffset = businessZoneID.getRules().getOffset(Instant.now());

    private final int userToUTC = ((userZoneOffset.getTotalSeconds()/3600) - (utcZoneOffset.getTotalSeconds()/3600));

    public int customerID;
    public int userID;
    public int contactID;

    private Contact contact;
    private User user;

    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, LocalDateTime appointmentCreateDate, String appointmentCreatedBy, LocalDateTime lastUpdated, String lastUpdatedBy, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.appointmentCreateDate = appointmentCreateDate;
        this.appointmentCreatedBy = appointmentCreatedBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    public Appointment(int contactID, int appointmentID, String appointmentTitle, String appointmentType, Date appointmentStart, Date appointmentEnd, int customerID) {
    }

    public ZonedDateTime getAppointmentStartZDT() {
        return appointmentStartZDT;
    }

    public void setAppointmentStartZDT(ZonedDateTime appointmentStartZDT) {
        this.appointmentStartZDT = appointmentStartZDT;
    }

    public ZonedDateTime getAppointmentEndZDT() {
        return appointmentEndZDT;
    }

    public void setAppointmentEndZDT(ZonedDateTime appointmentEndZDT) {
        this.appointmentEndZDT = appointmentEndZDT;
    }

    /**
     * Gets appointment's ID from database.
     * @return appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets appointment ID from getAppointmentID
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets appointment's title from database.
     * @return appointmentTitle
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     * Sets appointment title to getAppointmentTitle.
     * @param appointmentTitle
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    /**
     * Gets appointment's description from database.
     * @return appointmentDescription
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * Sets appointment description to getAppointmentDescription.
     * @param appointmentDescription
     */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    /**
     * Gets appointment's location from database.
     * @return appointmentLocation
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    /**
     * Sets appointment's location to getAppointmentLocation.
     * @param appointmentLocation
     */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    /**
     * Gets appointment's type from database.
     * @return appointmentType
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * Sets appointment's type to getAppointmentType.
     * @param appointmentType
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * Gets appointment's start date and start time from database.
     * @return appointmentStart
     */
    public LocalDateTime getAppointmentStartDateTime() {
        return appointmentStart;
    }

    public ZonedDateTime getAppointmentStartDateTimeZDT(){
        return appointmentStartZDT.withZoneSameInstant(userZoneID);
    }

    public ZonedDateTime getAppointmentEndDateTimeZDT(){
        return appointmentEndZDT.withZoneSameInstant(userZoneID);
    }

    /**
     * Sets appointment's start time and start date to getAppointmentStart.
     * @param appointmentStart
     */
    public void setAppointmentStartDateTime(LocalDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    /**
     * Returns the appointment start date in a formatted string
     * @return appointmentStart
     */
    public String getFormattedStartDateTime(){
        return TimeConversion.formatLocalDate(LocalDateTime.from(appointmentStart));
    }

    /**
     * Returns the appointment end date in a formatted string
     * @return
     */
    public String getFormattedEndDate(){
        return TimeConversion.formatLocalDate(LocalDateTime.from(appointmentEnd));
    }

    /**
     * Returns the appointment's starting time in the local time zone.
     * @return appointmentStart
     */
    public LocalTime getStartTimeLocally(){
        return appointmentStart.toLocalTime();
    }

    public LocalTime getEndTimeLocally(){
        return appointmentEnd.toLocalTime();
    }

    /**
     * Returns the appointment's starting time in the local time zone as a string.
     * @return
     */
    public String getStartTimeLocallyString(){
        return TimeConversion.formatLocalTime(LocalTime.from(appointmentStart));
    }

    /**
     * Gets appointment's end date and end time from database.
     * @return appointmentEnd
     */
    public LocalDateTime getAppointmentEndDateTime() {
        return appointmentEnd;
    }

    /**
     * Returns an appointment's end date locally.
     * @return appointmentEnd
     */
    public LocalDate getAppointmentEndDate(){
        return appointmentEnd.toLocalDate();
    }

    /**
     * Returns an appointment's end time locally.
     * @return appointmentEnd
     */
    public LocalTime getAppointmentEndTime(){
        return appointmentEndZDT.toLocalTime();
    }

    public LocalTime getFormattedEndTimeLocally(){
        return appointmentEndZDT.withZoneSameInstant(userZoneID).toLocalTime();
    }

    /**
     * Returns an appointment's end date and time as a string.
     * @return appointmentEnd
     */
    public String getAppointmentEndDateTimeString(){
        return TimeConversion.formatLocalTime(LocalTime.from(appointmentEnd));
    }

    /**
     * Sets appointment's end date and end time from getAppointmentEnd.
     * @param appointmentEnd
     */
    public void setAppointmentEndDateTime(LocalDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    /**
     * Gets customer's ID from database.
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets customer's ID to getCustomerID.
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets user's ID from database.
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets user's ID to getUserID.
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets contact's ID from database.
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets contact's ID to getContactID.
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets appointment's create date and create time from database.
     * @return
     */
    public LocalDateTime getAppointmentCreateDate() {
        return appointmentCreateDate;
    }

    /**
     * Sets appointment's create date and create time from getAppointmentCreateDate.
     * @param appointmentCreateDate
     */
    public void setAppointmentCreateDate(LocalDateTime appointmentCreateDate) {
        this.appointmentCreateDate = appointmentCreateDate;
    }

    /**
     * Gets who created a specific appointment from the database.
     * @return
     */
    public String getAppointmentCreatedBy() {
        return appointmentCreatedBy;
    }

    /**
     * Sets who created a specific appointment from getCreatedBy.
     * @param appointmentCreatedBy
     */
    public void setAppointmentCreatedBy(String appointmentCreatedBy) {
        this.appointmentCreatedBy = appointmentCreatedBy;
    }

    /**
     * Gets the last time an appointment was updated from the database.
     * @return
     */
    public LocalDateTime getLastUpdated() {

        return lastUpdated.plusHours(userToUTC);
    }

    /**
     * Sets the last time an appointment was updated from getLastUpdated.
     * @param lastUpdated
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Gets who last updated the appointment from the database.
     * @return
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets who last updated an appointment from getLastUpdated.
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets contact assigned to an appointment from the database.
     * @return
     */
    public Contact getContactByIDFromAppointments() throws SQLException {
        contactDAO contactDAO = new contactDAO();
        this.contact = contactDAO.getContactByID(getContactID());
        return this.contact;
    }

    /**
     * Returns the contact's name by the contact's ID from the database.
     * @return contactName
     * @throws SQLException
     */
    public String getContactByNameFromAppointments() throws SQLException{
        Contact contact = getContactByIDFromAppointments();
        String contactName = contact.getContactName();
        return contactName;
    }


    /**
     * Sets the contact assigned to an appointment from getContact.
     * @param contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Gets the user assigned to an appointment from the database.
     * @return this.user
     */
    public User getUserName() throws SQLException{
        userDAO userDAO = new userDAO();
        this.user = userDAO.getUserName(String.valueOf(getUserID()));
        return this.user;
    }

    /**
     * Sets the user assigned to an appointment from getUser.
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the contact associated with an appointment
     * @return contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Gets the user associated with creating / updating an appointment
     * @return
     */
    public User getUser() {
        return user;
    }

    public LocalDate getAppointmentStartDateString(){
        LocalDate appointmentStartDate = appointmentStart.toLocalDate();
        return appointmentStartDate;
    }

    /**
     * Displays the appointment info as a string instead of a reference to the appointment object.
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(this.appointmentID);
    }
}
