package DAO;

import DAO.Interfaces.userDAOInterface;
import Model.User;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.*;
import java.time.LocalDateTime;

public class userDAO implements userDAOInterface {

    private int userID;
    private String userName;
    private String userPassword;
    private LocalDateTime createdDateTime;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

    private String sqlStatementSelectAllUsers = "SELECT * FROM users";
    private String sqlStatementGetUserName = "SELECT * FROM users WHERE User_Name=";
    private String sqlStatementGetUserID = "SELECT * FROM users WHERE User_ID=";
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    Connection connection = DBConnection.getConnection();

    @FXML
    private ObservableList<User> userObservableList = FXCollections.observableArrayList();

    /**
     * Constructor for userDAO.
     * @throws SQLException for database retrieval errors.
     */
    public userDAO() throws SQLException{
        preparedStatement = connection.prepareStatement(sqlStatementSelectAllUsers);
        resultSet = preparedStatement.executeQuery();
    }

    /**
     * Returns all users from database
     * @return ObservableList<User>
     */
    public ObservableList<User> getUserObservableList(){
        int userID;
        String userName;
        String userPassword;
        LocalDateTime createdDateTime;
        String createdBy;
        Timestamp lastUpdate;
        String lastUpdatedBy;
        try {
            while(resultSet.next()){
                userID = resultSet.getInt("User_ID");
                userName = resultSet.getString("User_Name");
                userPassword = resultSet.getString("Password");
                createdDateTime = resultSet.getTimestamp("Create_Date").toLocalDateTime();
                createdBy = resultSet.getString("Created_By");
                lastUpdate = resultSet.getTimestamp("Last_Update");
                lastUpdatedBy = resultSet.getString("Last_Updated_By");

                User user = new User(userID, userName, userPassword, createdDateTime, createdBy, lastUpdate, lastUpdatedBy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userObservableList;
    }

    /**
     * Returns user ID for username if the username and user password match
     * @param userName
     * @param userPassword
     * @return
     */
    public static int validateUserInDatabase(String userName, String userPassword) throws SQLException {
        try{
            String sqlStatement ="SELECT * FROM users WHERE User_Name = '"+userName+"' AND Password= '"+userPassword+"';";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getString("User_Name").equals(userName)){
                if (resultSet.getString("Password").equals(userPassword)){
                    return resultSet.getInt("User_ID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("User was not found in database");
        return 0;
    }



    /**
     * Gets user from database based on the user's ID
     * @param userID
     * @return
     * @throws SQLException
     */
    @Override
    public User getUserID(int userID) throws SQLException {
        preparedStatement = connection.prepareStatement(sqlStatementGetUserID);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String userName = resultSet.getString("User_Name");
            String userPassword = resultSet.getString("Password");
            LocalDateTime createdDateTime = resultSet.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdated = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");

            User gotUserID = new User(userID, userName, userPassword, createdDateTime, createdBy, lastUpdated, lastUpdatedBy);
            return gotUserID;
        }
        return null;
    }

    /**
     * Gets user from database based on the user's user name.
     * @param userName
     * @return getUserNameReturn
     * @throws SQLException
     */
    @Override
    public User getUserName(String userName) throws SQLException{
        String sqlStatementGetUserName = "SELECT * FROM users WHERE User_Name="+userName;
        preparedStatement = connection.prepareStatement(sqlStatementSelectAllUsers);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int userID = resultSet.getInt("User_ID");
            String userPassword = resultSet.getString("Password");
            LocalDateTime createDateTime = resultSet.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdated = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");

            User getUserNameReturn = new User(userID, userName, userPassword, createDateTime, createdBy, lastUpdated, lastUpdatedBy);
            return getUserNameReturn;
        }
        return null;
    }

    /**
     * Returns all users from the database
     * @return userObservableList
     * @throws SQLException
     */
    @Override
    public ObservableList<User> getAllUsers() throws SQLException {
        try {
            preparedStatement = connection.prepareStatement(sqlStatementSelectAllUsers);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                userID = resultSet.getInt("User_ID");
                userName = resultSet.getString("User_Name");
                userPassword = resultSet.getString("Password");
                createdDateTime = resultSet.getTimestamp("Create_Date").toLocalDateTime();
                createdBy = resultSet.getString("Created_By");
                lastUpdated = resultSet.getTimestamp("Last_Update");
                lastUpdatedBy = resultSet.getString("Last_Updated_By");

                User user = new User(userID, userName, userPassword, createdDateTime, createdBy, lastUpdated, lastUpdatedBy);
                userObservableList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
