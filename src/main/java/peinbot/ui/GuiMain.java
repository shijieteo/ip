package peinbot.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import peinbot.PeinBot;
import peinbot.ui.controller.GuiMainWindow;
import peinbot.ui.view.DialogBox;

public class GuiMain extends Application {
    private PeinBot peinBot = new PeinBot(true);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GuiMain.class.getResource("/view/GuiMainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setMinWidth(417);
            stage.setMinHeight(220);
            stage.setScene(scene);
            stage.setTitle("PeinBot");
            peinBot.initializeStorage();
            GuiMainWindow controller = fxmlLoader.<GuiMainWindow>getController();
            controller.setPeinBot(peinBot);  // inject the PeinBot instance
            controller.printWelcomeMessage();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
