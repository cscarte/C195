package DAO;

import DAO.Interfaces.contactDAOInterface;
import Model.Contact;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class contactDAO implements contactDAOInterface {
    private String sqlStatementSelectAllContacts = "SELECT * FROM contacts";
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    Connection connection = DBConnection.getConnection();
    @FXML
    public ObservableList<Contact> contactObservableList = FXCollections.observableArrayList();

    /**
     * Constructor for contactDAO
     * Includes prepared statement establishing connection and including the SQL query to select all contacts in the database.
     * @throws SQLException
     */
    public contactDAO() throws SQLException{
        preparedStatement = connection.prepareStatement(sqlStatementSelectAllContacts);
        resultSet = preparedStatement.executeQuery();
    }

    /**
     * Returns all contacts in an observable list
     * @return getContactObservableList
     */
    public  ObservableList<Contact> getContactObservableList(){
        int contactID;
        String contactName;
        String contactEmail;

        try {
            while(resultSet.next()){
                contactID = resultSet.getInt("Contact_ID");
                contactName = resultSet.getString("Contact_Name");
                contactEmail = resultSet.getString("Email");

                Contact contact = new Contact(contactID, contactName, contactEmail);
                contactObservableList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactObservableList;
    }

    /**
     * Returns an observable list showing all contacts in the database
     * @return contactObservableList
     */
    @Override
    public ObservableList<Contact> getContacts() {
        int contactID;
        String contactName;
        String contactEmail;

        try{
            while(resultSet.next()){
               contactID = resultSet.getInt("Contact_ID");
               contactName = resultSet.getString("Contact_Name");
               contactEmail =resultSet.getString("Email");

               Contact contact = new Contact(contactID, contactName, contactEmail);

               contactObservableList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactObservableList;
    }

    /**
     * Returns contact by contact ID from the database.
     * @param contactID
     * @return gotContact
     * @throws SQLException
     */
    public Contact getContactByID(int contactID) throws SQLException{
        //String completeSqlStatement = sqlStatementSelectAllContacts + String.valueOf(contactID);
        preparedStatement = connection.prepareStatement(sqlStatementSelectAllContacts);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            contactID = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");
            String contactEmail = resultSet.getString("Email");

            Contact gotContact = new Contact(contactID, contactName, contactEmail);
            return  gotContact;
        }
        return null;
    }
}