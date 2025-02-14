package jessica;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.w3c.dom.Text;

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
    private final Image jessicaImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        sendButton.disableProperty().bind(userInput.textProperty().isEmpty());

        chatbotHello();

        // Set placeholder text initially and bind it dynamically
        userInput.setPromptText("Type something or try help");  // Ensure it appears at startup
        userInput.promptTextProperty().bind(Animation.animateEmptyInput(userInput));

        // Ensure CSS animation applies when empty
        if (userInput.getText().isEmpty()) {
            userInput.getStyleClass().add("decorate-empty-text");
        }

        // Prevent Enter key from submitting when input is empty
        userInput.setOnAction(event -> {
            if (userInput.getText().isEmpty()) {
                event.consume(); // Prevent action from triggering
            } else {
                handleUserInput(); // Submit normally
            }
        });

        // Add listener to toggle the placeholder animation class
        userInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                if (!userInput.getStyleClass().contains("decorate-empty-text")) {
                    userInput.getStyleClass().add("decorate-empty-text");
                }
            } else {
                userInput.getStyleClass().remove("decorate-empty-text");
            }
        });
    }

    /** Injects the Jessica instance */
    public void setJessica(Jessica d) {
        jessica = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    public void handleUserInput() {
        String input = userInput.getText();
        if (input.isEmpty()) {
            return; // Do nothing if input is empty
        }
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

    public void handleInputPopUp(String input) {
        DialogBox userDialog = DialogBox.getUserDialog(input, userImage);
        Animation.applyFadeAndSlide(userDialog); // Apply animation
        dialogContainer.getChildren().add(userDialog);
    }

    public void handleResponsePopUp(String response) {
        DialogBox dukeDialog = DialogBox.getJessicaDialog(response, jessicaImage);
        Animation.applyFadeAndSlide(dukeDialog); // Apply animation
        dialogContainer.getChildren().add(dukeDialog);
    }

    public void chatbotHello() {
        String input = Help.chatbotHello();
        DialogBox dukeDialog = DialogBox.getChatbotHelloDialog(input, jessicaImage);
        Animation.applyFadeAndSlide(dukeDialog); // Apply animation
        dialogContainer.getChildren().add(dukeDialog);
    }

    public void exitProgram() {
        wait(300);
        // code to exit the program
        Platform.exit();
    }

    public void wait(int millisecond) {
        try {
            TimeUnit.MILLISECONDS.sleep(millisecond);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
