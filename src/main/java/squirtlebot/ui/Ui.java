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
    private String savedMessage;

    private Ui(boolean isGuiInstance) {
        this.isGuiInstance = isGuiInstance;
        savedMessage = "";
    }

    public static Ui getGuiInstance() {
        return new Ui(true);
    }

    public static Ui getCliInstance() {
        return new Ui(false);
    }

    public void listTasks(TaskList taskList) {
        setSavedMessage(taskList.toString());
    }

    /**
     * Prints welcome banner for SquirtleBot
     * <p>
     *     Intended for use when operating in CLI-mode
     * </p>
     */
    public void printBanner() {
        String banner = """
                                           16600000661                    \s
                                    4009000000000000000000001             \s
                                7001   60000800000000000001               \s
                              90  0000000  0000000800000 1000000006       \s
                            60 700000000000 00800000006 0000000000000     \s
                           00 00  000000000 1080000000 000000000   000    \s
                          80 00   0000000004 080000880 0000000000  0000   \s
                         869 00000000000000 7080000000 0000000000000000   \s
                        6660 00000000000000 00800000000 000000000000000   \s
                        06661 00000000000  0000000000000 0000000000000    \s
                       6666668   0000    00000000000000005 0000000000     \s
                       666666666660800000000   780005    000         06   \s
                       66666666656008880000               0800000000002   \s
                       19666666628000000800               0808000000801   \s
                        06466666560800000000 3333333337  0000000000000    \s
                         066666664000000000007  73337   00000000000806    \s
                          0666666560080000000000096600000000000000000     \s
                           69666664400000000008000000088000000000006      \s
                             0866664300000000000888880000000080000        \s
                       16886    08966660000000000000000000000006          \s
                    90966666606     769860000000000000000003              \s
                   0666666666660  6006                      00            \s
                  79666669666660 90006  900 888888883888888  001          \s
                  16656  1  168  0000 1 00 88888888838888888 000          \s
                   066666660 79 0008   80  08888888338888888 4000         \s
                   186666660  0     73 00 333333333333333388  0           \s
                     080800  00000  33 00 000000000858000000              \s
                
                
                """;

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
    public void setSavedMessage(String output) {
        savedMessage = output;
    }

    public void printSavedMessage() {
        System.out.println(Ui.HORIZONTAL_LINE);
        System.out.println(savedMessage);
        System.out.println(Ui.HORIZONTAL_LINE);
    }

    public String getSavedMessage() {
        return savedMessage.trim();
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
        return ("Hello! I'm SquirtleBot :) \nWhat can I do for you?");
    }
}
