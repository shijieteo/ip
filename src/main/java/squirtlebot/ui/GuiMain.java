package squirtlebot.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import squirtlebot.SquirtleBot;
import squirtlebot.ui.controller.GuiMainWindow;

/**
 * Starts the GUI for SquirtleBot
 * <p>Loads the GuiMainWindow layout, initializes SquirtleBot then sends instance of SquirtleBot to controller</p>
 */
public class GuiMain extends Application {
    private SquirtleBot squirtleBot = new SquirtleBot(true);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GuiMain.class.getResource("/view/GuiMainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            stage.setMinWidth(417);
            stage.setMinHeight(220);
            stage.setScene(scene);
            stage.setTitle("SquirtleBot");

            squirtleBot.initializeStorage();
            GuiMainWindow controller = fxmlLoader.<GuiMainWindow>getController();
            controller.setSquirtleBot(squirtleBot);
            controller.printWelcomeMessage();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
