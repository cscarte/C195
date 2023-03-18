package DAO;

import DAO.Interfaces.customerDAOInterface;
import Model.Customer;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.*;
import java.time.LocalDateTime;

public class customerDAO implements customerDAOInterface {
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Connection connection = DBConnection.getConnection();

    @FXML
    ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
    ObservableList<Customer> customerObservableList1 = FXCollections.observableArrayList();

    private int customerIntCount;

    public void customerDAOInterface() throws SQLException{};

    private String sqlInsertIntoCustomerStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private String sqlGetCustomerStatement = "SELECT * from customers where Customer_ID=";
    private String sqlDeleteStatement = "DELETE FROM customers WHERE Customer_ID =";
    private final String sqlSelectAllCustomerStatement = "SELECT * FROM customers";

    /**
     * Creates a customer in the database
     * @return customer
     * @throws SQLException
     */
    private Customer createCustomer() throws SQLException {
        int customerID = resultSet.getInt("Customer_ID");
        String customerName = resultSet.getString("Customer_Name");
        String customerAddress = resultSet.getString("Address");
        String customerPostalCode = resultSet.getString("Postal_Code");
        String customerPhone = resultSet.getString("Phone");

        Timestamp createDateTimeTS = resultSet.getTimestamp("Create_Date");
        System.out.println(createDateTimeTS);
        LocalDateTime createDateTime = createDateTimeTS.toLocalDateTime();

        LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();
        String createdBy = resultSet.getString("Created_By");
        String lastUpdatedBy = resultSet.getString("Last_Updated_By");
        int divisionID = resultSet.getInt("Division_ID");

        Customer customer = new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, createDateTime, createdBy, lastUpdate, lastUpdatedBy, divisionID);

        return customer;
    }

    /**
     * Returns an observable list for all customers in the database
     * @return customerObservableList
     * @throws SQLException
     */
    @Override
    public ObservableList<Customer> customerGetAllObservableList() throws SQLException {
        preparedStatement = connection.prepareStatement((sqlSelectAllCustomerStatement));
        resultSet = preparedStatement.executeQuery();
        try {
            while(resultSet.next()){
                Customer customer = createCustomer();
                customerObservableList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerObservableList;
    }

    /**
     * Gets customer info from database based on the customer's ID.
     * @param customerID
     * @return customer
     * @return null
     * @throws SQLException
     */
    @Override
    public Customer getCustomer(int customerID) throws SQLException {
        String stringValueCustomerID = String.valueOf(customerID);
        String completeSQLGetCustomer = sqlGetCustomerStatement + stringValueCustomerID;
        PreparedStatement preparedStatement = connection.prepareStatement(completeSQLGetCustomer);
        resultSet = preparedStatement.executeQuery();

        try{
            while(resultSet.next()){
                Customer customer = createCustomer();
                customerObservableList.add(customer);
                return customer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Adds customer to database.
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhone
     * @param createdDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     * @throws SQLException
     */
    @Override
    public void addCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, Timestamp createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID) throws SQLException {
        preparedStatement = connection.prepareStatement((sqlInsertIntoCustomerStatement));
        //resultSet = preparedStatement.executeQuery();
        preparedStatement.setString(1, customerName);
        preparedStatement.setString(2, customerAddress);
        preparedStatement.setString(3, customerPostalCode);
        preparedStatement.setString(4, customerPhone);
        preparedStatement.setTimestamp(5,createdDate);
        preparedStatement.setString(6, createdBy);
        preparedStatement.setTimestamp(7, lastUpdate);
        preparedStatement.setString(8, lastUpdatedBy);
        preparedStatement.setInt(9, divisionID);

        preparedStatement.execute();
    }

    /**
     * Updates selected customer in database.
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhone
     * @param divisionID
     * @param lastUpdatedBy
     * @throws SQLException
     */
    @Override
    public void updatedCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionID, String lastUpdatedBy) throws SQLException {
        Customer customerToUpdate = getCustomer(customerID);
        LocalDateTime customerCreateDate = customerToUpdate.getCreateDateTime();
        String sqlUpdateStatement = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=?, Last_Update=?, Last_Updated_By=? WHERE Customer_ID=?";
        preparedStatement = connection.prepareStatement((sqlUpdateStatement));
        try {
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, customerAddress);
            preparedStatement.setString(3, customerPostalCode);
            preparedStatement.setString(4, customerPhone);
            preparedStatement.setInt(5, divisionID);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, lastUpdatedBy);
            preparedStatement.setInt(8, customerID);

            preparedStatement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Deletes customer from database.
     * This method will check for any linked appointments to the customer.
     * If any appointments exist for customer, appointments will be deleted as well.
     * @param customerID
     * @throws SQLException
     */
    @Override
    public void deleteCustomer(int customerID) throws SQLException {
        String deleteCustomerFromDatabase = sqlDeleteStatement + customerID;
        System.out.println(deleteCustomerFromDatabase);
        PreparedStatement preparedStatement = connection.prepareStatement(deleteCustomerFromDatabase);

        appointmentDAO appointmentDAO = new appointmentDAO();
        appointmentDAO.deleteAssignedAppointmentsByCustomerID(customerID);

        preparedStatement.execute();
    }

    @Override
    public ObservableList<Customer> getAllCustomers() throws SQLException {
        preparedStatement = connection.prepareStatement(sqlSelectAllCustomerStatement);
        resultSet = preparedStatement.executeQuery();

        try {
            while(resultSet.next()){
                Customer tempCustomer = createCustomer();
                customerObservableList.add(tempCustomer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerObservableList;
    }

    public int customerCount() throws SQLException {
        preparedStatement = connection.prepareStatement(sqlSelectAllCustomerStatement);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Customer customer = createCustomer();
            customerObservableList1.add(customer);
        }
        return customerIntCount = customerObservableList1.size();
    }
}
