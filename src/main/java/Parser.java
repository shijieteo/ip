import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Parser {
    private final HashMap<String, Function<String[], Command>> commandMap = new HashMap<>();

    Parser(){
        this.commandMap.put("todo", x -> new AddToDoCommand(x));
        this.commandMap.put("deadline", x -> new AddDeadlineCommand(x));
        this.commandMap.put("event", x -> new AddEventCommand(x));
        this.commandMap.put("mark", x -> new MarkCommand(x));
        this.commandMap.put("unmark", x -> new UnmarkCommand(x));
        this.commandMap.put("bye", x -> new ExitCommand());
        this.commandMap.put("list", x -> new ListCommand());
    }

    public Command processInput(String userInput) {
        String[] userInputArray = userInput.split(" ");
        String commandString = userInputArray[0];
        Function<String[], Command> commandfunction = Optional.ofNullable(this.commandMap.get(commandString))
                .orElseThrow(() -> new IllegalArgumentException("Invalid command"));
        return commandfunction.apply(userInputArray);
    }
}
