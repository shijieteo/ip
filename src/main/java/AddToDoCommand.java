import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Example of a stateful command: it holds the data parsed from the user input
 * (the task description), which the enum approach could not do cleanly.
 */
public class AddToDoCommand extends Command {
    private String taskDescription;

    public AddToDoCommand(String[] userInput) {
        parseDescription(userInput);
    }

    @Override
    public void execute(ArrayList<Task> taskList, Ui ui, Storage storage) {
        Task task = new ToDo(this.taskDescription);
        taskList.add(task);
        try {
            storage.writeData(task);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        ui.printMessage(String.format("\tadded: %s to your list of tasks\n\t" +
                "You now have %d tasks", task, taskList.size()));
    }


    private void parseDescription(String[] userInputArray){
        this.taskDescription = IntStream.range(1, userInputArray.length).boxed()
                .map(x -> userInputArray[x]).reduce("", (x,y) -> x + " " + y);

        if(taskDescription.isEmpty()){
            throw new IllegalArgumentException("Please provide the correct arguments for ToDo!");
        }

    }
}
