package DAO;

import DAO.Interfaces.firstLevelDivisionDAOInterface;
import Model.FirstLevelDivision;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class firstLevelDivisionDAO implements firstLevelDivisionDAOInterface {
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private Connection connection = DBConnection.getConnection();
    private String sqlStatementSelectAllDivisions = "SELECT * FROM first_level_divisions";
    private String sqlStatementSelectSpecificDivision = "SELECT * FROM first_level_divisions WHERE Division_ID=";

    @FXML
    private ObservableList<FirstLevelDivision> firstLevelDivisionObservableList = FXCollections.observableArrayList();

    /**
     * Constructor for firstLevelDivisionDAO
     * @throws SQLException
     */
    public firstLevelDivisionDAO() throws SQLException{
        preparedStatement = connection.prepareStatement(sqlStatementSelectAllDivisions);
        resultSet = preparedStatement.executeQuery();
    }

    /**
     * Returns observable list of all first level divisions from database.
     * @return firstLevelDivisionObservableList
     */
    @Override
    public ObservableList<FirstLevelDivision> getAllDivisionsObservableList() {
        try{
            while(resultSet.next()){
                int divisionID = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                LocalDate createDate = resultSet.getDate("Create_Date").toLocalDate();
                LocalTime createTime = resultSet.getTime("Create_Date").toLocalTime();
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int countryID = resultSet.getInt("Country_ID");

                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(divisionID, divisionName, createDate, createTime, createdBy, lastUpdate, lastUpdatedBy, countryID);

                firstLevelDivisionObservableList.add(firstLevelDivision);
            }
        } catch (SQLException e) {
            System.out.println("The issue is in firstLevelDivisionDAO, check result set");
            e.printStackTrace();
        }
        return firstLevelDivisionObservableList;
    }

    /**
     * Returns a specific first level division from the database
     * @param divisionID
     * @return firstLevelDivision
     * @throws SQLException
     */
    public FirstLevelDivision getFirstLevelDivision(int divisionID) throws SQLException{
        String completeSqlStatementSelectAllDivisions = sqlStatementSelectSpecificDivision + divisionID;
        preparedStatement = connection.prepareStatement(completeSqlStatementSelectAllDivisions);
        resultSet = preparedStatement.executeQuery();
        FirstLevelDivision firstLevelDivision = null;
        while(resultSet.next()){
            String division = resultSet.getString("Division");
            LocalDate createLocalDate = resultSet.getDate("Create_Date").toLocalDate();
            LocalTime createLocalTime = resultSet.getTime("Create_Date").toLocalTime();
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int countryID = resultSet.getInt("Country_ID");

            firstLevelDivision = new FirstLevelDivision(divisionID, division, createLocalDate, createLocalTime, createdBy, lastUpdate, lastUpdatedBy, countryID);
        }
        return firstLevelDivision;
    }
}