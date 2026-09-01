package peinbot.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import peinbot.PeinBot;
import peinbot.ui.view.DialogBox;

public class GuiMainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private PeinBot peinBot;

    private Image peinImage = new Image(this.getClass().getResourceAsStream("/images/peinbot.jpg"));
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setPeinBot(PeinBot peinBot) {
        this.peinBot = peinBot;
    }

    @FXML
    private void handleUserInput() {
        String userInputText = userInput.getText();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userInputText, userImage),
                DialogBox.getPeinBotDialog(peinBot.getResponse(userInputText), peinImage);
        )
        userInput.clear();
    }


}
