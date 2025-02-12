package jessica;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput; // Input textbox
    @FXML
    private Button sendButton;

    private Jessica jessica;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setJessica(Jessica d) {
        jessica = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().equals("bye")) {
            exitProgram();
        }
        String response = jessica.getResponse(input);
        System.out.println(response);
        handleInputPopUp(input);
        PauseTransition delay = new PauseTransition(Duration.millis(300));
        delay.setOnFinished(event -> handleResponsePopUp(response));
        delay.play();
        userInput.clear();
    }

    private void handleInputPopUp(String input) {
        DialogBox userDialog = DialogBox.getUserDialog(input, userImage);
        applyFadeAndSlide(userDialog); // Apply animation
        dialogContainer.getChildren().add(userDialog);
    }

    private void handleResponsePopUp(String response) {
        DialogBox dukeDialog = DialogBox.getDukeDialog(response, dukeImage);
        applyFadeAndSlide(dukeDialog); // Apply animation
        dialogContainer.getChildren().add(dukeDialog);
    }

    private void applyFadeAndSlide(DialogBox dialog) {
        // Fade transition (makes it appear smoothly)
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), dialog);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Slide transition (makes it move up slightly)
        TranslateTransition slideUp = new TranslateTransition(Duration.millis(500), dialog);
        slideUp.setFromY(30); // Start 30 pixels lower
        slideUp.setToY(0); // Move to normal position

        // Play animations together
        fadeIn.play();
        slideUp.play();
    }

    private void exitProgram() {
        wait(300);
        // code to exit the program
        Platform.exit();
    }

    private void wait(int millisecond) {
        try {
            TimeUnit.MILLISECONDS.sleep(millisecond);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
