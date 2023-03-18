package Model;

import Utilities.TimeConversion;

import java.time.LocalDateTime;

public class Customer {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;

    private LocalDateTime createDateTime;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    public int divisionID;
    public String divisionName;
    public int countryID;
    public String countryName;

    public Customer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, LocalDateTime createDateTime, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.customerPostalCode = customerPostalCode;
        this.createDateTime = createDateTime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    /**
     * Gets customer's ID from database
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Gets customer's name from database
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets customer's address from database
     * @return customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Gets customer's postal code from database
     * @return customerPostalCode
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * Gets customer's phone from database
     * @return customerPhone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Gets customer's first level division ID based on the country from database
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Gets customer's first level division name based on the country from database
     * @return divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Gets the creation date and time of the customer in the database
     * @return createDateTime
     */
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    /**
     * Gets the creation date and time in the user's local timezone
     * @return TimeConversion.formatLocalDate(createDateTime)
     */
    public String getFormattedCreateDate(){
        return TimeConversion.formatLocalDate(createDateTime);
    }

    /**
     * Gets who created the customer in the database
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Gets the last time and date the customer was updated in the database
     * @return lastUpdate
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Gets the last time and date a customer was formatted in the user's local timezone.
     * @return TimeConversion.formatLocalDate(lastUpdate)
     */
    public String getFormattedLastUpdate(){
        return TimeConversion.formatLocalDate(lastUpdate);
    }

    /**
     *
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the customers ID in the database
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Sets the customer's name in the database
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Sets the customer's address in the database
     * @param customerAddress
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Sets the customer's postal code in the database
     * @param customerPostalCode
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * Sets the customer's phone number in the database
     * @param customerPhone
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * Sets the customer's creation date and time in the database.
     * @param createDateTime
     */
    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    /**
     * Sets the user who created the customer in the database
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Sets the last time and date a customer was updated.
     * @param lastUpdate
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Sets the user who last updated a customer
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Sets the first level division for a customer
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Displays the customer's name as a string instead of a reference to the customer object.
     * @return
     */
    @Override
    public String toString(){
        return this.getCustomerName();
    }
}
