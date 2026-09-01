package squirtlebot.ui;

import java.util.Scanner;

import squirtlebot.task.TaskList;

/**
 * Handles user interaction such as reading input and printing messages when operating in {@code CLI} mode
 * <p>
 *     Helps to store messages to output when operating in {@code GUI} mode
 * </p>
 */
public class Ui {
    private static final String HORIZONTAL_LINE = "\t_____________________________________________________________";
    private boolean isGuiInstance;
    private String savedOutput;

    private Ui(boolean isGuiInstance) {
        this.isGuiInstance = isGuiInstance;
        savedOutput = "";
    }

    public static Ui getGuiInstance() {
        return new Ui(true);
    }

    public static Ui getCliInstance() {
        return new Ui(false);
    }

    public void listTasks(TaskList taskList) {
        printMessage(taskList.toString());
    }

    /**
     * Prints welcome banner for SquirtleBot
     * <p>
     *     Intended for use when operating in CLI-mode
     * </p>
     */
    public void printBanner() {
        String banner = "__________       .__      ___.           __   \n"
                + "\\______   \\ ____ |__| ____\\_ |__   _____/  |_ \n"
                + " |     ___// __ \\|  |/    \\| __ \\ /  _ \\   __\\\n"
                + " |    |   \\  ___/|  |   |  \\ \\_\\ (  <_> )  |  \n"
                + " |____|    \\___  >__|___|  /___  /\\____/|__|  \n"
                + "               \\/        \\/    \\/             \n";

        System.out.println(Ui.HORIZONTAL_LINE);
        System.out.println(banner);
        System.out.println("Hello! I'm SquirtleBot :)");
        System.out.println(Ui.HORIZONTAL_LINE);
        System.out.println("\tWhat can I do for you? ");
    }

    /**
     * Prints output message between horizontal lines for formatting
     * @param output message to display to user
     */
    public void printMessage(String output) {
        if (!isGuiInstance) {
            System.out.println(Ui.HORIZONTAL_LINE);
            System.out.println(output);
            System.out.println(Ui.HORIZONTAL_LINE);
        } else {
            savedOutput = output;
        }
    }

    public String getSavedOutput() {
        return savedOutput;
    }

    /**
     * Creates a scanner and reads the next line of user input
     * @return string containing user's input
     */
    public String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public String getGuiWelcomeMessage() {
        return ("Hello! I'm SquirtleBot :) \n What can I do for you?");
    }
}
