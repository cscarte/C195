package DAO.Interfaces;

import Model.Customer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * All methods in an interface are by default public.
 * Public qualifier not needed
 */
public interface customerDAOInterface {

    @FXML
    ObservableList<Customer> customerGetAllObservableList() throws SQLException;

    /**
     * Interface to return customer from database based on the customer ID in the database in the customerDAO DAO.
     *
     * @param customerID
     * @return
     * @throws SQLException
     */
    Customer getCustomer(int customerID) throws SQLException;

    /**
     * Interface to add a customer object using the Customer model to the database in the customerDAO DAO.
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhone
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     * @throws SQLException
     */
    void addCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID) throws SQLException;

    /**
     * Interface to update existing customer and saves update to database in the customerDAO DAO.
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhone
     * @param divisionID
     * @param lastUpdatedBy
     * @throws SQLException
     */
    void updatedCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID, String lastUpdatedBy) throws SQLException;

    /**
     * Interface to delete selected customer ID (program will check for associated appointments with customer in the customerDAO DAO
     * to delete as well).
     * @param customerID
     * @throws SQLException
     */
    void deleteCustomer(int customerID) throws SQLException;

    ObservableList<Customer> getAllCustomers() throws SQLException;
}
