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
                System.out.println("\tBye. Hope to see you soon :(");
                System.out.println(PeinBot.HORIZONTAL_LINE);
                return true;

            default:
                System.out.println("\t" + String.format("added: %s to your list of tasks",userInput));
                PeinBot.taskList.add(userInput);
                System.out.println(PeinBot.HORIZONTAL_LINE);
                return false;
        }
    }

    public static String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
