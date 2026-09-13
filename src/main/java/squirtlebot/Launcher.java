package squirtlebot;

import javafx.application.Application;
import squirtlebot.ui.GuiMain;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {

    /**
     * Launches SquirtleBot application
     *
     * @param args command-line arguments to be passed to application
     */
    public static void main(String[] args) {
        Application.launch(GuiMain.class, args);
    }
}
