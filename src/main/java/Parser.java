import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

public class Parser {
    private final HashMap<String, Function<String[], Command>> commandMap = new HashMap<>();

    Parser() {
        commandMap.put("todo", x -> new AddToDoCommand(x));
        commandMap.put("deadline", x -> new AddDeadlineCommand(x));
        commandMap.put("event", x -> new AddEventCommand(x));
        commandMap.put("mark", x -> new MarkCommand(x));
        commandMap.put("unmark", x -> new UnmarkCommand(x));
        commandMap.put("bye", x -> new ExitCommand());
        commandMap.put("list", x -> new ListCommand());
        commandMap.put("find", x -> new FindCommand(x));
    }

    public Command processInput(String userInput) {
        String[] userInputArray = userInput.split(" ");
        String commandString = userInputArray[0];
        Function<String[], Command> commandFunction = Optional.ofNullable(commandMap.get(commandString))
                .orElseThrow(() -> new IllegalArgumentException("Invalid command"));
        return commandFunction.apply(userInputArray);
    }
}
