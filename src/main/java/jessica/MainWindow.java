package jessica;

import javafx.animation.*;
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
 * Controller for the main GUI of Jessica chatbot.
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Jessica jessica;
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user-anime1.png"));
    private final Image jessicaImage = new Image(this.getClass().getResourceAsStream("/images/bot-anime1.png"));

    /**
     * Initializes UI elements and event listeners.
     */
    @FXML
    public void initialize() {
        sendButton.disableProperty().bind(userInput.textProperty().isEmpty());

        // Auto-scroll when new messages appear
        dialogContainer.heightProperty().addListener((obs, oldVal, newVal) ->
                Platform.runLater(this::smoothScrollToBottom)
        );


        chatbotHello();

        // Set and bind placeholder text
        userInput.setPromptText("Type something or try help");
        userInput.promptTextProperty().bind(Animation.animateEmptyInput(userInput));

        // Toggle placeholder animation class
        userInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                userInput.getStyleClass().add("decorate-empty-text");
            } else {
                userInput.getStyleClass().remove("decorate-empty-text");
            }
        });

        // Prevent Enter key from submitting empty input
        userInput.setOnAction(event -> {
            if (!userInput.getText().isEmpty()) {
                handleUserInput();
            } else {
                event.consume();
            }
        });
    }

    /**
     * Injects the chatbot instance.
     * @param d Jessica chatbot instance
     */
    public void setJessica(Jessica d) {
        jessica = d;
    }

    /**
     * Handles user input, processes response, and updates the UI.
     */
    @FXML
    public void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) return;

        if (input.trim().equals("clear")) {
            clearContent();
            return;
        }

        if (input.equals("bye")) {
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

    /**
     * Displays user input in the chat.
     */
    public void handleInputPopUp(String input) {
        DialogBox userDialog = DialogBox.getUserDialog(input, userImage);
        Animation.applyFadeAndSlide(userDialog);
        dialogContainer.getChildren().add(userDialog);
    }

    /**
     * Displays chatbot response in the chat.
     */
    public void handleResponsePopUp(String response) {
        DialogBox dukeDialog = DialogBox.getJessicaDialog(response, jessicaImage);
        Animation.applyFadeAndSlide(dukeDialog);
        dialogContainer.getChildren().add(dukeDialog);
    }

    /**
     * Displays chatbot greeting message.
     */
    public void chatbotHello() {
        String greeting = Help.chatbotHello();
        DialogBox dukeDialog = DialogBox.getChatbotHelloDialog(greeting, jessicaImage);
        Animation.applyFadeAndSlide(dukeDialog);
        dialogContainer.getChildren().add(dukeDialog);
    }

    /**
     * Exits the program with a delay.
     */
    public void exitProgram() {
        wait(300);
        Platform.exit();
    }

    /**
     * Delays execution for a specified time.
     * @param millisecond Delay duration in milliseconds
     */
    public void wait(int millisecond) {
        try {
            TimeUnit.MILLISECONDS.sleep(millisecond);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Scrolls smoothly to the bottom of the chat.
     */
    private void smoothScrollToBottom() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(scrollPane.vvalueProperty(), 1.0, Interpolator.EASE_OUT))
        );
        timeline.play();
    }

    private void clearContent() {
        // Create fade-out animation
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), dialogContainer);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Once faded out, clear content and fade back in
        fadeOut.setOnFinished(event -> {
            dialogContainer.getChildren().clear();
            dialogContainer.setOpacity(1.0); // Reset opacity
        });

        fadeOut.play();
        userInput.clear();
    }

}
