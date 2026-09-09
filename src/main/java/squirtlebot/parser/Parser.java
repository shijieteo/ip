package squirtlebot.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import squirtlebot.command.AddDeadlineCommand;
import squirtlebot.command.AddEventCommand;
import squirtlebot.command.AddToDoCommand;
import squirtlebot.command.Command;
import squirtlebot.command.ConfirmEventDateCommand;
import squirtlebot.command.DeleteCommand;
import squirtlebot.command.ExitCommand;
import squirtlebot.command.FindCommand;
import squirtlebot.command.ListCommand;
import squirtlebot.command.MarkCommand;
import squirtlebot.command.UnmarkCommand;

/**
 * Parses user input strings to identify the command the user would like to execute
 */
public class Parser {
    private static final int NUMBER_OF_COMMANDS = 10;

    private final HashMap<String, Function<String[], Command>> commandMap = new HashMap<>();

    /**
     * Constructs a Parser object
     * Initializes <code>commandMap</code> to contain the various mappings of user input to command
     */
    public Parser() {
        commandMap.put("todo", x -> new AddToDoCommand(x));
        commandMap.put("deadline", x -> new AddDeadlineCommand(x));
        commandMap.put("event", x -> new AddEventCommand(x));
        commandMap.put("mark", x -> new MarkCommand(x));
        commandMap.put("unmark", x -> new UnmarkCommand(x));
        commandMap.put("bye", x -> new ExitCommand());
        commandMap.put("list", x -> new ListCommand());
        commandMap.put("find", x -> new FindCommand(x));
        commandMap.put("delete", x -> new DeleteCommand(x));
        commandMap.put("confirm", x -> new ConfirmEventDateCommand(x));

        assert commandMap.size() == NUMBER_OF_COMMANDS;
        assert commandMap.values().stream().allMatch(function -> function != null);
    }

    /**
     * Returns {@link Command} representing the user input
     *
     * @param userInput string representing command to execute and parameters if any
     * @throws IllegalArgumentException if user specifies an unsupported command
     */
    public Command parseCommand(String userInput) {
        String[] userInputArray = userInput.split(" ");
        String commandString = userInputArray[0];
        Function<String[], Command> commandFunction = Optional.ofNullable(commandMap.get(commandString))
                .orElseThrow(() -> new IllegalArgumentException("Invalid command"));

        assert commandFunction != null;
        
        return commandFunction.apply(userInputArray);
    }

    public String parseTokens(String[] userInputArray, String expectedToken) {
        boolean isExpectedTokenIdentified = false;
        String assembledToken = "";

        for (String currentToken : userInputArray) {
            boolean isDoneReadingExpectedToken = currentToken.startsWith("/") && isExpectedTokenIdentified;

            if (currentToken.equals(expectedToken)) {
                isExpectedTokenIdentified = true;
                continue;
            } else if (isDoneReadingExpectedToken) {
                break;
            } else if (currentToken.startsWith("/")) {
                isExpectedTokenIdentified = false;
            }

            if (!isExpectedTokenIdentified) {
                continue;
            }
            assembledToken += (currentToken + " ");
        }

        return assembledToken.trim();
    }

    public String parseDescription(String[] userInputArray) {
        String assembledDescription = "";

        for (int i = 1; i < userInputArray.length; i++) {
            String currentText = userInputArray[i];
            if(currentText.startsWith("/")) {
                break;
            }
            assembledDescription += (currentText + " ");
        }

        return assembledDescription.trim();
    }
}
