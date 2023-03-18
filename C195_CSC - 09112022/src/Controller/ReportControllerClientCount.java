package Controller;

import DAO.customerDAO;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportControllerClientCount implements Initializable {

    @FXML private TableView<Customer> customerTableView;

    @FXML private TableColumn<Customer, Integer> customerIDColumn;
    @FXML private TableColumn<Customer, String> customerNameColumn;

    @FXML private TextField customerCountTextField;

    @FXML private Button cancelButton;

    @FXML private ObservableList<Model.Customer> customerObservableList;

    private customerDAO customerDAO = new customerDAO();

    public ReportControllerClientCount() throws SQLException {
        customerObservableList = customerDAO.getAllCustomers();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTableView.setItems(customerObservableList);
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        try {
            customerCountTextField.setText(String.valueOf(customerDAO.customerCount()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void moveToReportMenu(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../View/Report.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
