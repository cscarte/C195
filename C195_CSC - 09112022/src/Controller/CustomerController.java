package Controller;

import DAO.customerDAO;
import Model.Customer;
import Utilities.moveScreens;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    private customerDAO customerDAO = new customerDAO();

    @FXML private Button mainMenuButton;
    @FXML private Button addCustomerButton;
    @FXML private Button updateCustomerButton;
    @FXML private Button deleteCustomerButton;

    @FXML private TableView<Customer> customerTableView;

    @FXML private TableColumn<Customer, Integer> customerIDColumn;
    @FXML private TableColumn<Customer, String> customerNameColumn;
    @FXML private TableColumn<Customer, String> customerAddressColumn;
    @FXML private TableColumn<Customer, String> customerPostalCodeColumn;
    @FXML private TableColumn<Customer, String> customerPhoneColumn;
    @FXML private TableColumn<Customer, String> customerCreateDateColumn;
    @FXML private TableColumn<Customer, String> customerCreatedByColumn;
    @FXML private TableColumn<Customer, LocalTime> customerLastUpdateColumn;
    @FXML private TableColumn<Customer, String> customerLastUpdatedByColumn;
    @FXML private TableColumn<Customer, Integer> customerDivisionIDColumn;
    //private int getCustomerID;

    public static Customer customerToUpdate;


    public CustomerController() throws SQLException{
    }

    /**
     * Populates info on screen
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerDAO customerDAO = new customerDAO();
            customerTableView.setItems(customerDAO.customerGetAllObservableList());

            customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            customerCreateDateColumn.setCellValueFactory(new PropertyValueFactory<>("FormattedCreateDate"));
            customerCreatedByColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            customerLastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("FormattedLastUpdate"));
            customerLastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            customerDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

            customerTableView.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves to the main menu page
     * @param event
     */
    public void moveToMainMenu(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/MainMenu.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) mainMenuButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves to the customer add page
     * @param event
     */
    public void moveToAddCustomer(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/CustomerAdd.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) addCustomerButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens up the CustomerUpdate screen
     * @param event
     * @throws IOException
     */
    @FXML
    public void moveToCustomerUpdate(javafx.event.ActionEvent event) throws IOException{
        customerToUpdate = customerTableView.getSelectionModel().getSelectedItem();
        //System.out.println("From CustomerController: " + customerToUpdate);
        moveScreens.moveScreen(event, "CustomerUpdate.fxml");
    }

    /**
     * Deletes customer database as well as all associated appointments with that customer.
     * @param event
     */
    @FXML
    public void deleteCustomerFromDataBase(javafx.event.ActionEvent event) {
        Customer customerToDelete = customerTableView.getSelectionModel().getSelectedItem();
        Alert alertConfirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmDelete.setTitle("Confirm deletion");
        String stringConfirmDelete = "Delete customer and all associated appointments?";
        alertConfirmDelete.setContentText(stringConfirmDelete);
        Optional<ButtonType> alertChoice = alertConfirmDelete.showAndWait();

        if (alertChoice.isPresent() && alertChoice.get()==ButtonType.OK){
            try {
                customerDAO.deleteCustomer(customerToDelete.getCustomerID());
                moveScreens.moveScreen(event, "Customer.fxml");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}