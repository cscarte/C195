package DAO.Interfaces;

import Model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.SQLException;

public interface userDAOInterface {
    /**
     * Interface to return user from the database based on the user's name in the userDAO DAO.
     * @param userName
     * @return User
     * @throws SQLException
     */
    @FXML
    User getUserName(String userName) throws SQLException;

    /**
     * Interface to return user from the database based on the user's ID in the userDAO DAO.
     * @param userID
     * @return User
     * @throws SQLException
     */
    @FXML
    User getUserID(int userID) throws SQLException;


    /**
     * Interface to return all users from the database in the userDAO DAO.
     * @return ObservableList<User>
     */
    @FXML ObservableList<User> getAllUsers() throws SQLException;
}
