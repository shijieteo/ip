package peinbot.ui;

import java.util.Scanner;

public class Ui {
    private static final String HORIZONTAL_LINE = "\t_____________________________________________________________";

    public void listTasks(TaskList taskList) {
        printMessage(taskList.toString());
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

    public void printMessage(String output) {
        System.out.println(Ui.HORIZONTAL_LINE);
        System.out.println(output);
        System.out.println(Ui.HORIZONTAL_LINE);
    }

    public String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
