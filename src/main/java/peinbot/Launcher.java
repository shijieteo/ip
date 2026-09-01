import javafx.application.Application;
import peinbot.ui.GuiMain;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(GuiMain.class, args);
    }
}
