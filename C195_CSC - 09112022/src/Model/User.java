package Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {
    private int userID;
    private String userName;
    private String userPassword;
    private LocalDateTime createDateTime;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    /**
     * Constructor from user model.
     *
     * @param userID          User_ID in database
     * @param userName        User_Name in database
     * @param userPassword    Password in database
     * @param createdDateTime
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     */
    public User(int userID, String userName, String userPassword, LocalDateTime createdDateTime, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.createDateTime = createdDateTime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets user ID from database.
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets username from database.
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets user's password from database.
     * @return userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    /**
     * Gets user created the user in the database
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Gets the last time the user was updated in the database
     * @return lastUpdate
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Gets the user that last updated a user in the database
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }


}