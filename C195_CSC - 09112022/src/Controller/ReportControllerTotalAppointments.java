package Controller;

import DAO.appointmentDAO;
import Model.Appointment;
import Model.ReportMonthType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportControllerTotalAppointments implements Initializable {
    private appointmentDAO appointmentDAO = new appointmentDAO();

    @FXML private Button cancelButton;
    @FXML private ObservableList typeMonthReport = FXCollections.observableArrayList();

    private ObservableList<Model.Appointment> filterByMonth = FXCollections.observableArrayList();

    @FXML private ComboBox<Appointment> monthComboBox;

    @FXML private TableView<ReportMonthType> appointmentTypeMonthReportTable;
    @FXML private TableColumn<ReportMonthType, String> appointmentTypeColumn;
    @FXML private TableColumn<ReportMonthType, String> appointmentTypeAndMonthTypeColumn;
    @FXML private TableColumn<ReportMonthType, Integer> appointmentTypeAndMonthAmountTotalColumn;

    public ReportControllerTotalAppointments() throws SQLException {
    }

    /**
     * Populates data on the screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeTypeMonthReport();
            appointmentTypeMonthReportTable.setItems(typeMonthReport);
            appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            appointmentTypeAndMonthTypeColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
            appointmentTypeAndMonthAmountTotalColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
            //filterMonthComboBox(monthComboBox, 1, 12).getSelectionModel().isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the report main menu screen.
     * @param event
     */
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

    /**
     *
     * @throws SQLException
     */
    private void initializeTypeMonthReport() throws SQLException {
        //appointmentDAO appointmentDAO = new appointmentDAO();
        System.out.println("initializeTypeMonthReport loading...");
        typeMonthReport.addAll(appointmentDAO.generateTypeMonthReport());
        System.out.println("initializeTypeMonthReport ran correctly!");
    }

    private static ComboBox filterMonthComboBox (ComboBox monthComboBox, int firstMonth, int endMonth){
        firstMonth = 1;
        endMonth = 12;

        while(firstMonth <= endMonth){
            int monthIntervals = 1;
            monthComboBox.getItems().add(firstMonth);
            firstMonth = firstMonth + monthIntervals;
        }
        return monthComboBox;
    }

    private void setFilterByMonthJanuary(javafx.event.ActionEvent event) throws SQLException{
        try{

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
