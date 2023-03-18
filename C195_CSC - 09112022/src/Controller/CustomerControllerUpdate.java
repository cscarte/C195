package Controller;

import DAO.countryDAO;
import DAO.customerDAO;
import DAO.firstLevelDivisionDAO;
import Model.Country;
import Model.Customer;
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
import java.util.ResourceBundle;

public class CustomerControllerUpdate implements Initializable {
    private Customer customerToUpdate = CustomerController.customerToUpdate;
    private firstLevelDivisionDAO firstLevelDivisionDAO = new firstLevelDivisionDAO();
    private countryDAO countryDAO = new countryDAO();
    private customerDAO customerDAO = new customerDAO();
    private FilterFirstLevelDivisionsByCountry filterFirstLevelDivisionsByCountry = new FilterFirstLevelDivisionsByCountry();

    @FXML
    private ObservableList<FirstLevelDivision> filteredDivisions = FXCollections.observableArrayList();

    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String customerPostalCode;
    private Country selectedCountry;
    private FirstLevelDivision selectedFirstLevelDivision;

    @FXML
    private TextField customerIDTextField;
    @FXML
    private TextField customerNameTextField;
    @FXML
    private TextField customerAddressTextField;
    @FXML
    private TextField customerPostalCodeTextField;
    @FXML
    private TextField customerPhoneTextField;

    @FXML
    private ComboBox<Country> countryComboBox;
    public ComboBox<Country> countryComboBoxUpdate;
    @FXML
    private ComboBox<FirstLevelDivision> firstLevelDivisionComboBox;
    public ComboBox<FirstLevelDivision> firstLevelDivisionComboBoxUpdate;

    @FXML
    private Button cancelButton;
    @FXML
    private Button updateCustomerButton;

    public CustomerControllerUpdate() throws SQLException{
    }

    /**
     * Populates data on the screen
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("From CustomerControllerUpdate: " + customerToUpdate);
        customerIDTextField.setText(String.valueOf(customerToUpdate.getCustomerID()));
        customerNameTextField.setText(customerToUpdate.getCustomerName());
        customerPhoneTextField.setText(customerToUpdate.getCustomerPhone());
        customerPostalCodeTextField.setText(customerToUpdate.getCustomerPostalCode());
        customerAddressTextField.setText(customerToUpdate.getCustomerAddress());
        customerToUpdate.getDivisionID();

        countryComboBox.setItems(countryDAO.getAllCountriesObservableList());
        if(customerToUpdate.getDivisionID() <= 54) {
            countryComboBox.getSelectionModel().select(0);
        } else if (customerToUpdate.getDivisionID() >= 60 && customerToUpdate.getDivisionID() <= 72){
            countryComboBox.getSelectionModel().selectLast();
        } else {
            countryComboBox.getSelectionModel().select(1);
        }

        try {
            filteredDivisions = filterFirstLevelDivisionsByCountry.FilterDivisionsByCountry(customerToUpdate.countryID);
            firstLevelDivisionComboBox.setItems(filteredDivisions);
            FirstLevelDivision customerFirstLevelDivision = getSelectedFirstLevelDivision(customerToUpdate.getDivisionID());
            firstLevelDivisionComboBox.getSelectionModel().select(customerFirstLevelDivision);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns first level division info from the first level division database access object
     * @param divisionID
     * @return
     * @throws SQLException
     */
    public FirstLevelDivision getSelectedFirstLevelDivision(int divisionID) throws SQLException{
        return new firstLevelDivisionDAO().getFirstLevelDivision(divisionID);
    }

    /**
     * Opens the Customer.fxml screen
     * @param event
     */
    public void moveToCustomer(javafx.event.ActionEvent event) {
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
     * Gathers user input data for the customer being updated
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
     * Checks if any text fields or combo boxes have not been filled out.
     * @return
     */
    private boolean validateInputCustomerInfo(){
        inputCustomerInfo();

        if(customerName.isBlank() || customerPhone.isBlank() || customerAddress.isBlank() ||customerPostalCode.isBlank() || selectedCountry==null || selectedFirstLevelDivision==null){
            Alert incompleteInformation = new Alert(Alert.AlertType.WARNING);
            incompleteInformation.setTitle("Customer information missing");
            incompleteInformation.setContentText("Customer information is missing");
            incompleteInformation.showAndWait();
            return false;
        } return true;
    }

    /**
     * Populates first level division and country combo boxes with data from database.
     * @param event
     * @throws SQLException
     */
    @FXML
    public void selectionComboBox(javafx.event.ActionEvent event) throws SQLException{
        Country country = countryComboBox.getSelectionModel().getSelectedItem();
        filteredDivisions = filterFirstLevelDivisionsByCountry.FilterDivisionsByCountry(country.getCountryID());
        firstLevelDivisionComboBox.setItems(filteredDivisions);
        firstLevelDivisionComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Saves (updates) the customer information based on the text fields and combo boxes from the CustomerUpdate.fxml screen
     * @param event
     * @throws SQLException
     */
    public void comboBoxUpdates(ActionEvent event) throws SQLException{
        if(validateInputCustomerInfo()){
            customerDAO.updatedCustomer(customerToUpdate.getCustomerID(), customerName, customerAddress, customerPostalCode, customerPhone, selectedFirstLevelDivision.getDivisionID(), Login.loggedInUserName);
            try {
                moveScreens.moveScreen(event, "Customer.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}