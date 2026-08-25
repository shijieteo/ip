import java.io.InvalidClassException;
import java.io.IOException;
import java.time.format.DateTimeParseException;

import peinbot.command.Command;

public class PeinBot {
    private static final String HORIZONTAL_LINE = "\t_____________________________________________________________";
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private Parser parser;

    PeinBot() {
        storage = new Storage();
        taskList = new TaskList();
        ui = new Ui();
        parser = new Parser();
    }

    public void run() {
        ui.printBanner();
        boolean isExit = false;
        while (true) {
            try {
                taskList = storage.loadData();
                break;
            } catch (InvalidClassException invalidClassException) {
                storage.resetData();
                continue;
            } catch (ClassNotFoundException | IOException e) {
                isExit = storageIssueHandler();
                break;
            }
        }

        while (!isExit) {
            String userInput = ui.readInput();
            try {
                Command userCommand = parser.processInput(userInput);
                isExit = userCommand.isExit();
                userCommand.execute(taskList, ui, storage);
            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                ui.printMessage("\tPlease enter a valid index....");
            } catch (DateTimeParseException dateTimeParseException) {
                ui.printMessage("\tPlease enter a valid date....");
            } catch (IllegalArgumentException illegalArgumentException) {
                ui.printMessage("\t" + illegalArgumentException.getMessage());
            } catch (RuntimeException runtimeException) {
                isExit = storageIssueHandler();
            }
        }
    }

    public static void main(String[] args) {
        PeinBot peinBot = new PeinBot();
        peinBot.run();
    }

    private boolean storageIssueHandler() {
        String userAnswer = "";
        do {
            ui.printMessage("\tThere was an issue with data storage... continue? [Y/N]: ");
            userAnswer = ui.readInput();
            if (userAnswer.equals("N")) {
                return true;
            }
        } while (!userAnswer.equals("Y") && !userAnswer.equals("N"));
        return false;
    }

    private void loadData() throws ClassNotFoundException, IOException {
        taskList = storage.loadData();
    }
}


