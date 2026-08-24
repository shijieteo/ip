import java.util.ArrayList;

public class Ui {
    private static final String HORIZONTAL_LINE = "\t_____________________________________________________________";

    public void listTasks(ArrayList<Task> taskList) {
        String accumulatedTaskString = "";
        int endLoopIndex = taskList.size();
        for (int index = 0; index < endLoopIndex; index++) {
            accumulatedTaskString += String.format("\t%d. %s", index + 1, taskList.get(index));
            if(index != endLoopIndex - 1) {
                accumulatedTaskString += "\n";
            }
        }
        printOutput(accumulatedTaskString);
    }

    public void printBanner() {
        String banner = "__________       .__      ___.           __   \n"
                + "\\______   \\ ____ |__| ____\\_ |__   _____/  |_ \n"
                + " |     ___// __ \\|  |/    \\| __ \\ /  _ \\   __\\\n"
                + " |    |   \\  ___/|  |   |  \\ \\_\\ (  <_> )  |  \n"
                + " |____|    \\___  >__|___|  /___  /\\____/|__|  \n"
                + "               \\/        \\/    \\/             \n";

        System.out.println(Ui.HORIZONTAL_LINE);
        System.out.println(banner);
        System.out.println("Hello! I'm PeinBot :)");
        System.out.println(Ui.HORIZONTAL_LINE);
        System.out.println("\tWhat can I do for you? ");
    }

    public void printOutput(String output) {
        System.out.println(output);
        System.out.println(Ui.HORIZONTAL_LINE);
    }
}
