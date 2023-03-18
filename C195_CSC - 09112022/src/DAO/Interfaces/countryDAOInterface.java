package DAO.Interfaces;

import Model.Country;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public interface countryDAOInterface {
    /**
     * Interface to return an observable list for all countries in the database in the countryDAO DAO.
     *
     * @return ObservableList<Country>
     */
    @FXML
    ObservableList<Country> getAllCountriesObservableList();
}
