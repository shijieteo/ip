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
    private boolean isAwaitingStorageDecision = false;

    private final Image botImage = new Image(this.getClass().getResourceAsStream("/images/squirtle.jpg"));
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setSquirtleBot(SquirtleBot squirtleBot) {
        this.squirtleBot = squirtleBot;
    }

    @FXML
    private void handleUserInput() {
        if (isAwaitingStorageDecision) {
            handleStorageDecision();
            return;
        }
        String userInputText = userInput.getText();
        CommandResult commandResult = squirtleBot.getResponse(userInputText);
        if (commandResult.shouldExit()) {
            System.exit(0);
        }

        addUserMessageToDisplay(userInputText);
        addBotMessageToDisplay(commandResult.message());
        userInput.clear();
    }

    private void handleStorageDecision() {
        String storageDecision = userInput.getText();
        if (storageDecision.equals("Y")) {
            squirtleBot.disableStorage();
            isAwaitingStorageDecision = false;

            addUserMessageToDisplay(storageDecision);
            addBotMessageToDisplay(SquirtleBot.CONTINUE_WITHOUT_STORAGE_RESPONSE);
            userInput.clear();
        } else if (storageDecision.equals("N")) {
            System.exit(0);
        } else {
            promptOnStorageIssue();
        }
    }

    /**
     * Retrieves SquirtleBot's welcome message and displays message in a {@link DialogBox}
     */
    public void printWelcomeMessage() {
        addBotMessageToDisplay(squirtleBot.getWelcomeMessage());
    }

    public void promptOnStorageIssue() {
        isAwaitingStorageDecision = true;
        addBotMessageToDisplay(SquirtleBot.STORAGE_ISSUE_PROMPT);
    }

    private void addBotMessageToDisplay(String botMessage) {
        dialogContainer.getChildren().add(DialogBox.getSquirtleBotDialog(botMessage, botImage));
    }

    private void addUserMessageToDisplay(String userInput) {
        dialogContainer.getChildren().add(DialogBox.getUserDialog(userInput, userImage));
    }

}
