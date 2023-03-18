package DAO.Interfaces;

import Model.FirstLevelDivision;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/**
 * Interfaces are publicly accessible by default.
 */
public interface firstLevelDivisionDAOInterface {
    /**
     * Interface for an observable list for all first level divisions for the firstLevelDivisionDAO DAO
     * @return
     */
    @FXML
    ObservableList<FirstLevelDivision> getAllDivisionsObservableList();
}