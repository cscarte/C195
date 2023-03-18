package Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class FirstLevelDivision {
    private int divisionID;
    private String divisionName;
    private LocalDate createDate;
    private LocalTime createTime;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryID;



    public FirstLevelDivision(int divisionID, String divisionName, LocalDate createDate, LocalTime createTime, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.createDate =createDate;
        this.createTime=createTime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    /**
     * Gets the first level division's ID from the database
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets the first level division's ID from the database
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Gets the first level division's name from the database
     * @return divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets the first level division's name from the database
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Gets the first level division's creation date in the database
     * @return createDate
     */
    public LocalDate getCreateDate() {
        return createDate;
    }

    /**
     * Sets the first level division's creation date in the database
     * @param createDate
     */
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the first level divison's time created in the database
     * @return createTime
     */
    public LocalTime getCreateTime() {
        return createTime;
    }

    /**
     * Sets the first level divison's time created in the database
     * @param createTime
     */
    public void setCreateTime(LocalTime createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets the user that created the first level division in the database
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user that created the first level division in the database
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the time of the last time the first level division was updated in the database
     * @return lastUpdate
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the time of the last time the first level division was updated in the database
     * @param lastUpdate
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the user that last updated the first level division in the database
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the user that last updated the first level division in the database
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the country's ID for the first level division
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets the country's ID for the first level division
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Displays the first level division's name as a string instead of a reference to the first level division object
     * @return divisionName
     */
    @Override
    public String toString(){
        return divisionName;
    }
}
