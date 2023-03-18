package Controller;

import DAO.countryDAO;
import DAO.customerDAO;
import DAO.firstLevelDivisionDAO;
import Model.Country;
import Model.FirstLevelDivision;
import Utilities.FilterFirstLevelDivisionsByCountry;
import Utilities.moveScreens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class CustomerControllerAdd implements Initializable {
    @FXML private TextField customerIDTextField;
    @FXML private TextField customerNameTextField;
    @FXML private TextField customerAddressTextField;
    @FXML private TextField customerPostalCodeTextField;
    @FXML private TextField customerPhoneTextField;

    @FXML private ComboBox<Country> countryComboBox;
    public ComboBox<Country> countryComboBoxUpdate;
    @FXML private ComboBox<FirstLevelDivision> firstLevelDivisionComboBox;
    public ComboBox<FirstLevelDivision> firstLevelDivisionComboBoxUpdate;
    //@FXML private ComboBox<FirstLevelDivision> filteredFirstLevelDivisionComboBox = (ComboBox<FirstLevelDivision>) FXCollections.observableArrayList();

    @FXML private Button cancelButton;
    @FXML private Button addCustomerButton;

    private countryDAO countryDAO = new countryDAO();
    private customerDAO customerDAO = new customerDAO();
    private firstLevelDivisionDAO firstLevelDivisionDAO = new firstLevelDivisionDAO();
    private FilterFirstLevelDivisionsByCountry filterFirstLevelDivisionsByCountry = new FilterFirstLevelDivisionsByCountry();

    private int customerID;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String customerPostalCode;
    private Country selectedCountry;
    private FirstLevelDivision selectedFirstLevelDivision;


    @FXML private ObservableList<FirstLevelDivision> firstLevelDivisionObservableList = firstLevelDivisionDAO.getAllDivisionsObservableList();

    @FXML private ObservableList<FirstLevelDivision> filteredDivisions = FXCollections.observableArrayList();

    @FXML private ObservableList<Country> countryObservableList = countryDAO.getAllCountriesObservableList();


    /**
     * Constructor for add customer controller.
     * @throws SQLException
     */
    public CustomerControllerAdd() throws SQLException{
    }

    /**
     * Populates info on screen
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryComboBox.setPromptText("Select Country");
        firstLevelDivisionComboBox.setPromptText("Select Province");
        countryComboBox.setItems(countryObservableList);
        firstLevelDivisionComboBox.setItems(null);
        System.out.println(Login.loggedInUserName);
    }

    /**
     * Pulls info input into the add customer form.
     */
    private void inputCustomerInfo(){
        customerName = customerNameTextField.getText().trim();
        customerPhone = customerPhoneTextField.getText().trim();
        customerAddress = customerAddressTextField.getText().trim();
        customerPostalCode = customerPostalCodeTextField.getText().trim();
        selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        selectedFirstLevelDivision = firstLevelDivisionComboBox.getSelectionModel().getSelectedItem();
    }

    /**
     * Checks if any part of the form was not filled out.
     * @param event
     * @return boolean
     */
    private boolean validateInputCustomerInfo(ActionEvent event){
        inputCustomerInfo();

        if (customerName.isBlank() || customerPhone.isBlank() || customerAddress.isBlank() || customerPostalCode.isBlank() || selectedCountry==null || selectedFirstLevelDivision==null){
            Alert infoMissing = new Alert(Alert.AlertType.WARNING);

            infoMissing.setTitle("Customer Information is Missing");
            infoMissing.setContentText("Fill out all information for customer");
            infoMissing.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Filters through the selectable first level divisions by the country selected.
     * @param event
     * @throws SQLException
     */
    @FXML
    public void selectCountry (ActionEvent event) throws SQLException{
        Country country = countryComboBox.getSelectionModel().getSelectedItem();
        filteredDivisions = filterFirstLevelDivisionsByCountry.FilterDivisionsByCountry(country.getCountryID());
        firstLevelDivisionComboBox.setItems(filteredDivisions);
        firstLevelDivisionComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Cancels adding a customer and opens up the Customer.fxml screen.
     * @param event
     */
    public void cancelInputCustomerInfo(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/Customer.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a customer to the database and opens up the Customer.fxml screen
     * @param event
     */
    @FXML
    public void addCustomer(ActionEvent event){
        if(validateInputCustomerInfo(event)){
            try{
                Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());
                Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
                customerDAO.addCustomer(customerName, customerAddress, customerPostalCode, customerPhone, createDate, Login.loggedInUserName, lastUpdate, Login.loggedInUserName, selectedFirstLevelDivision.getDivisionID());
                countryComboBoxUpdate = countryComboBox;
                //firstLevelDivisionComboBoxUpdate = firstLevelDivisionComboBox;
                moveScreens.moveScreen(event,"Customer.fxml");
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Placeholder");
    }
}