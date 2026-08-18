import java.util.Scanner;


public class PeinBot {
    static String HORIZONTAL_LINE = "_____________________________________________________________";

    public static void main(String[] args) {

        String banner = "__________       .__      ___.           __   \n"
                + "\\______   \\ ____ |__| ____\\_ |__   _____/  |_ \n"
                + " |     ___// __ \\|  |/    \\| __ \\ /  _ \\   __\\\n"
                + " |    |   \\  ___/|  |   |  \\ \\_\\ (  <_> )  |  \n"
                + " |____|    \\___  >__|___|  /___  /\\____/|__|  \n"
                + "               \\/        \\/    \\/             \n";

        System.out.println(HORIZONTAL_LINE);
        System.out.println(banner);
        System.out.println("Hello! I'm PeinBot :)");

        String userInput = readInput();
        processInput(userInput);

        System.out.println("Bye. See you soon :(");
        System.out.println(HORIZONTAL_LINE);
    }

    public static void processInput(String message) {

    }

    public static String readInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What can I do for you? ");
        System.out.println(HORIZONTAL_LINE);
        return scanner.nextLine();
    }
}
