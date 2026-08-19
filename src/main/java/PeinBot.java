import java.util.Scanner;
import java.util.ArrayList;

public class PeinBot {
    private static final String HORIZONTAL_LINE = "\t_____________________________________________________________";
    private static ArrayList<String> taskList = new ArrayList<String>();

    public static void main(String[] args) {

        String banner = "__________       .__      ___.           __   \n"
                + "\\______   \\ ____ |__| ____\\_ |__   _____/  |_ \n"
                + " |     ___// __ \\|  |/    \\| __ \\ /  _ \\   __\\\n"
                + " |    |   \\  ___/|  |   |  \\ \\_\\ (  <_> )  |  \n"
                + " |____|    \\___  >__|___|  /___  /\\____/|__|  \n"
                + "               \\/        \\/    \\/             \n";

        System.out.println(PeinBot.HORIZONTAL_LINE);
        System.out.println(banner);
        System.out.println("Hello! I'm PeinBot :)");
        System.out.println(PeinBot.HORIZONTAL_LINE);


        System.out.println("\tWhat can I do for you? ");

        boolean isExit;
        do {
            String userInput = readInput();
            isExit = processInput(userInput);
        } while(!isExit);

    }

    public static boolean processInput(String userInput) {
        System.out.println(PeinBot.HORIZONTAL_LINE);
        switch (userInput) {
            case "bye":
                printOutput("\tBye. Hope to see you soon :(");
                return true;

            case "list":
                String accumulatedTaskString = "";
                int endLoopIndex = PeinBot.taskList.size();
                for (int index = 0; index < endLoopIndex; index++) {
                    accumulatedTaskString += String.format("\t%d. %s", index + 1, PeinBot.taskList.get(index));
                    if(index != endLoopIndex - 1) {
                        accumulatedTaskString += "\n";
                    }
                }
                printOutput(accumulatedTaskString);
                return false;

            default:
                PeinBot.taskList.add(userInput);
                printOutput("\t" + String.format("added: %s to your list of tasks",userInput));
                return false;
        }
    }

    public static String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void printOutput(String output) {
        System.out.println(output);
        System.out.println(PeinBot.HORIZONTAL_LINE);
    }
}
