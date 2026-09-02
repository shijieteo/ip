package squirtlebot;

import java.io.IOException;
import java.io.InvalidClassException;
import java.time.format.DateTimeParseException;

import squirtlebot.command.Command;
import squirtlebot.parser.Parser;
import squirtlebot.storage.Storage;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Coordinates command parsing, task management, storage, and user interaction
 * for SquirtleBot.
 *
 * <p>Supports commands such as {@code todo}, {@code deadline}, {@code event},
 * {@code list}, {@code find}, {@code mark}, {@code unmark}, and {@code delete}.
 */
public class SquirtleBot {
    private static final String HORIZONTAL_LINE = "\t_____________________________________________________________";
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private Parser parser;

    /**
     * Constructs a new instance of SquirtleBot
     * @param isGuiSquirtleBot {@code true} if creating a GUI-based SquirtleBot;
     *                     {@code false} otherwise.
     */
    public SquirtleBot(boolean isGuiSquirtleBot) {
        storage = new Storage();
        taskList = new TaskList();
        ui = isGuiSquirtleBot ? Ui.getGuiInstance() : Ui.getCliInstance();
        parser = new Parser();
    }

    /**
     * Starts an instance of {@code SquirtleBot}.<br>
     * Intended for use with SquirtleBot running in CLI-mode.<br>
     * SquirtleBot will attempt to load previously stored tasks, then start reading user commands. <br>
     * Will continue running till user issues a <code>bye</code> command
     */
    public void run() {
        ui.printBanner();
        boolean shouldExit = false;
        while (true) {
            try {
                taskList = storage.loadData();
                break;
            } catch (InvalidClassException invalidClassException) {
                storage.resetData();
                continue;
            } catch (ClassNotFoundException | IOException e) {
                shouldExit = storageIssueHandler();
                break;
            }
        }

        while (!shouldExit) {
            String userInput = ui.readInput();
            shouldExit = runCommand(userInput);
            ui.printSavedMessage();
        }
    }

    public static void main(String[] args) {
        SquirtleBot squirtleBot = new SquirtleBot(false);
        squirtleBot.run();
    }

    private boolean storageIssueHandler() {
        String userAnswer = "";
        do {
            ui.setSavedMessage("\tThere was an issue with data storage... continue? [Y/N]: ");
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

    public String getWelcomeMessage() {
        return this.ui.getGuiWelcomeMessage();
    }

    /**
     * Loads tasks previously created by user into tasklist.
     * Intended for use with SquirtleBot running in GUI mode
     */
    public void initializeStorage() {
        while (true) {
            try {
                taskList = storage.loadData();
                break;
            } catch (InvalidClassException invalidClassException) {
                storage.resetData();
                continue;
            } catch (ClassNotFoundException | IOException e) {
                break;
            }
        }
    }

    /**
     * Runs the command entered by user, retrieves corresponding output
     * Intended for use with SquirtleBot running in GUI mode
     * @param userInput user input containing command to run and relevant parameters
     * @return output corresponding to user's command
     */
    public CommandResult getResponse(String userInput) {
        boolean shouldExit = runCommand(userInput);
        return new CommandResult(shouldExit, ui.getSavedMessage());
    }

    private boolean runCommand(String userInput) {
        boolean shouldExit = false;
        try {
            Command userCommand = parser.processInput(userInput);
            shouldExit = userCommand.shouldExit();
            userCommand.execute(taskList, ui, storage);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            ui.setSavedMessage("\tPlease enter a valid index....");
        } catch (DateTimeParseException dateTimeParseException) {
            ui.setSavedMessage("\tPlease enter a valid date....");
        } catch (IllegalArgumentException illegalArgumentException) {
            ui.setSavedMessage("\t" + illegalArgumentException.getMessage());
        } catch (RuntimeException runtimeException) {
            ui.setSavedMessage("There was an issue with storage..... :(");
        }
        return shouldExit;
    }
}


