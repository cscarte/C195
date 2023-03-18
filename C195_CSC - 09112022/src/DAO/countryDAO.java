package DAO;

import DAO.Interfaces.countryDAOInterface;
import Model.Country;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class countryDAO implements countryDAOInterface {
    private PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    private Connection connection = DBConnection.getConnection();
    private String sqlStatementSelectAllCountries = "SELECT * FROM countries";
    private String sqlStatementGetCountryID = "SELECT * FROM countries WHERE Country_ID=";

    @FXML
    public ObservableList<Country> countryObservableList = FXCollections.observableArrayList();

    /**
     * Constructor for countryDAO
     * @throws SQLException
     */
    public countryDAO() throws SQLException{
        preparedStatement = connection.prepareStatement(sqlStatementSelectAllCountries);
        resultSet = preparedStatement.executeQuery();
    }

    /**
     * Returns an observable list showing all countries in the databse
     * @return countryObservableList
     */
    @Override
    public ObservableList<Country> getAllCountriesObservableList(){
        try {
            preparedStatement = connection.prepareStatement(sqlStatementSelectAllCountries);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int countryID = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");
                LocalDate createDate = resultSet.getDate("Create_Date").toLocalDate();
                LocalTime createTime = resultSet.getTime("Create_Date").toLocalTime();
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");

                Country country = new Country(countryID, countryName, createDate, createTime, lastUpdate, createdBy);
                countryObservableList.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryObservableList;
    }
}
