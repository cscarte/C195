package Utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class moveScreens {
    private static Stage stage;
    private static Parent scene;
    public static String baseName = "../View/";

    public static String loginScreen = "Login";

    /**
     * "Constructor" method to allow easier user for specifying which screen to switch to.
     * Typically, to swap to the appointment main screen, you would have to type:
     *             FXMLLoader fxmlLoader = new FXMLLoader();
     *             fxmlLoader.setLocation(getClass().getResource("../View/Appointment.fxml"));
     *             Parent root = fxmlLoader.load();
     *             Stage stage = (Stage) cancelButton.getScene().getWindow();
     *             Scene scene = new Scene(root);
     *             stage.setScene(scene);
     *             stage.show();
     *
     * With this method though, only the following is required to switch to the appointment main screen.
     * SwapScreen.swapScreen(event, "Appointment.fxml");
     * @param event
     * @param sceneName
     * @throws IOException
     */
    public static void moveScreen(javafx.event.ActionEvent event, String sceneName) throws IOException{
        String fullName = baseName + sceneName;
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(moveScreens.class.getResource(fullName));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    static moveScreensInterface swapScreenLoginScreen = ((event, loginScreen) -> {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(moveScreens.class.getResource(baseName+loginScreen));

        String fullName = baseName + loginScreen;
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(moveScreens.class.getResource(fullName));
        stage.setScene(new Scene(scene));
        stage.show();
    });
}
