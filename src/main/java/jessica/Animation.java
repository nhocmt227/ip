package jessica;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Animation {
    public static StringBinding animateEmptyInput(TextField userInput) {
        return Bindings.when(userInput.textProperty().isEmpty())
                .then("Type something or try help...") // Show placeholder when empty
                .otherwise(""); // Remove placeholder when typing
    }



    public static void applyFadeAndSlide(DialogBox dialog) {
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
}
