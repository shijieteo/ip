package squirtlebot.command;

import java.util.stream.IntStream;

import squirtlebot.storage.Storage;
import squirtlebot.task.TaskList;
import squirtlebot.task.ToDo;
import squirtlebot.ui.Ui;

/**
 * Represents the todo command within <code>SquirtleBot</code>
 * Contains the values required to create a ToDo object
 */
public class AddToDoCommand extends Command {
    private String taskDescription;

    /**
     * Constructs a new AddToDoCommand using inputs provided by a user
     *
     * @param userInput array containing user inputs required to create a ToDo object
     */
    public AddToDoCommand(String[] userInput) {
        parseDescription(userInput);
    }

    /**
     * Creates a ToDo object based off user-provided
     * values and adds to an existing task list
     * Updates user on current state of the task list
     *
     * @param taskList list containing tasks created previously by the user
     * @param ui interface used to display output to the user
     * @param storage storage handler used to persist changes made by the command
     * @throws RuntimeException if an issue was encountered while attempting to write to storage
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ToDo toDoTask = new ToDo(taskDescription);
        taskList.add(toDoTask);
        try {
            storage.writeData(taskList);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        ui.printMessage(String.format("\tadded: %s to your list of tasks\n\t"
                + "You now have %d tasks", toDoTask, taskList.size()));
    }

    /**
     * Reassembles user input that was previously split to form task description for ToDo object
     *
     * @param userInputArray array containing user inputs required to create a ToDo object
     * @throws IllegalArgumentException if taskDescription is empty
     */
    private void parseDescription(String[] userInputArray) {
        taskDescription = IntStream.range(1, userInputArray.length).boxed()
                .map(x -> userInputArray[x]).reduce("", (x, y) -> x + " " + y);

        if (taskDescription.isEmpty()) {
            throw new IllegalArgumentException("Please provide the correct arguments for ToDo!");
        }
    }
}
