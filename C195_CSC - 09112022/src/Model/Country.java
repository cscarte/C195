package Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class Country {
    private int countryID;
    private String countryName;
    private LocalDate createDate;
    private LocalTime createTime;
    private Timestamp lastUpdate;
    private String lastUpdateBy;



    /**
     * Constructor for country
     *
     * @param countryID
     * @param countryName
     * @param createDate
     * @param createTime
     * @param lastUpdate
     * @param lastUpdateBy
     */
    public Country(int countryID, String countryName, LocalDate createDate, LocalTime createTime, Timestamp lastUpdate, String lastUpdateBy) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Country() {
    }

    /**
     * Gets the country's ID from the database
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets the country's ID in the database
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Gets the country's name from the database
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the country's name in the database
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Gets the country's creation date in the database
     * @return createDate
     */
    public LocalDate getCreateDate() {
        return createDate;
    }

    /**
     * Sets the country's creation date in the database
     * @param createDate
     */
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the country's creation time in the database
     * @return createTime
     */
    public LocalTime getCreateTime() {
        return createTime;
    }

    /**
     * Sets the country's creation time in the database
     * @param createTime
     */
    public void setCreateTime(LocalTime createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets the country's last updated time / date in the database
     * @return lastUpdate
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the country's last updated time / date in the database
     * @param lastUpdate
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets which user last updated the country in the database
     * @return lastUpdateBy
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * Sets which user last updated the country in the database
     * @param lastUpdateBy
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Displays the country's name as a string instead of a reference to the country object.
     * @return
     */
    @Override
    public String toString(){
        return countryName;
    }
}
