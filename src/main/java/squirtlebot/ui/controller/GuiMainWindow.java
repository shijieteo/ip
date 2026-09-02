package squirtlebot.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import squirtlebot.CommandResult;
import squirtlebot.SquirtleBot;
import squirtlebot.ui.view.DialogBox;

/**
 * Controls user interactions within SquirtleBot's GUI
 *
 * <p>
 *     Accepts user input, sending them to SquirtleBot for processing.<br>
 *     Displays both user input and SquirtleBot's response
 * </p>
 */
public class GuiMainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private SquirtleBot squirtleBot;

    private Image peinImage = new Image(this.getClass().getResourceAsStream("/images/squirtle.jpg"));
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setSquirtleBot(SquirtleBot squirtleBot) {
        this.squirtleBot = squirtleBot;
    }

    @FXML
    private void handleUserInput() {
        String userInputText = userInput.getText();
        CommandResult commandResult = squirtleBot.getResponse(userInputText);
        if (commandResult.shouldExit()) {
            System.exit(0);
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userInputText, userImage),
                DialogBox.getSquirtleBotDialog(commandResult.message(), peinImage)
        );
        userInput.clear();
    }

    /**
     * Retrieves SquirtleBot's welcome message and displays message in a {@link DialogBox}
     */
    public void printWelcomeMessage() {
        dialogContainer.getChildren().addAll(
                DialogBox.getSquirtleBotDialog(squirtleBot.getWelcomeMessage(), peinImage)
        );
    }

}
