package jessica;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main entry point for the Jessica application.
 * <p>
 * This class initializes and launches the JavaFX GUI using FXML.
 * It loads the main window layout from an FXML file and sets up
 * the primary stage.
 * </p>
 */
public class Main extends Application {

    private Jessica jessica = new Jessica();

    /**
     * Starts the JavaFX application.
     * <p>
     * This method loads the FXML layout for the main window, sets up
     * the scene, and displays the application window.
     * </p>
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Jessica AI Assistant");
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/logo.png")));

            fxmlLoader.<MainWindow>getController().setJessica(jessica);  // inject the Jessica instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
