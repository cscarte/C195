package DAO.Interfaces;

import Model.Contact;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.SQLException;

/**
 * All methods in an interface are by default public.
 * Public qualifier not needed
 */
public interface contactDAOInterface {
    /**
     * Interface to return observable list of all contacts from database in the contactDAO DAO.
     * @return ObservableList<Contact>
     */
    @FXML
    ObservableList<Contact> getContacts();

    /**
     * Interface to return specific contact based on the contact's ID in the database in the contactDAO DAO.
     * @param contactID
     * @return Contact
     * @throws SQLException
     */
    @FXML
    Contact getContactByID(int contactID) throws SQLException;
}
