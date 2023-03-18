 import Utilities.DBConnection;
import Utilities.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * Opens the login screen.
     * @param stage
     * @throws Exception
     */
    public void start (Stage stage) throws Exception{
        Logger.initializeLogManager();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Client Scheduler");
        stage.setScene(scene);
        stage.setResizable(false);
                stage.show();
    }

    /**
     * Main method, initializes connection to the database as well as launches the start method to open
     * the login screen. Closes program and connection to the database when program closes.
     * @param args
     * @throws Exception
     */
    public static void main (String[] args) throws Exception {
        DBConnection.makeConnection();

        //LocalTime localTime = LocalTime.now();
        //System.out.println(TimeConversion.convertLocalToEST(localTime));
        launch(args);
        System.out.println("Closing program and connection to database...");
        DBConnection.closeConnection();
    }
}