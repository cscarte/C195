package Utilities;

import DAO.countryDAO;
import DAO.firstLevelDivisionDAO;

import Model.Country;
import Model.FirstLevelDivision;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class FilterFirstLevelDivisionsByCountry {
    private firstLevelDivisionDAO firstLevelDivisionDAO = new firstLevelDivisionDAO();
    private countryDAO countryDAO = new countryDAO();

    @FXML
    private ObservableList<Country> countryObservableList = countryDAO.getAllCountriesObservableList();

    @FXML
    private ObservableList<FirstLevelDivision> firstLevelDivisionObservableList = firstLevelDivisionDAO.getAllDivisionsObservableList();

    /**
     * Constructor for FilterFirstLevelDivisionsByCountry for the Customer Add and Customer Update screens.
     * @throws SQLException
     */
    public FilterFirstLevelDivisionsByCountry() throws SQLException {
    }

    /**
     * Filters first level divisions by the country they belong to.
     * @param id
     * @return filteredDivisions
     * @throws SQLException
     */
    public ObservableList<FirstLevelDivision> FilterDivisionsByCountry(int id) throws SQLException{
      ObservableList<FirstLevelDivision> filteredDivisionsByCountry = FXCollections.observableArrayList();
      for (FirstLevelDivision firstLevelDivision : firstLevelDivisionObservableList){
          if (firstLevelDivision.getCountryID()==id){
              filteredDivisionsByCountry.add(firstLevelDivision);
          }
        }
        return filteredDivisionsByCountry;
    }
}