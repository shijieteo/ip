package squirtlebot.command;

import java.time.temporal.Temporal;

import squirtlebot.parser.DateParser;
import squirtlebot.parser.Parser;
import squirtlebot.storage.Storage;
import squirtlebot.task.Deadline;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Represents the deadline command within <code>SquirtleBot</code>
 */
public class AddDeadlineCommand extends Command {
    private Deadline deadlineToAdd;


    /**
     * Constructs a new AddDeadlineCommand using inputs provided by a user
     *
     * @param userInput array containing user inputs required to create a Deadline object
     */
    public AddDeadlineCommand(String[] userInput) {
        setAttributes(userInput);
    }

    /**
     * Creates a Deadline object based off user-provided<br>
     * values and adds to an existing task list<br>
     * Updates user on current state of task list
     *
     * @param taskList list containing tasks created previously by the user
     * @param ui interface used to display output to the user
     * @param storage storage handler used to persist changes made by the command
     * @throws RuntimeException if an issue was encountered while attempting to write to storage
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        int sizeBeforeAdding = taskList.size();

        taskList.add(deadlineToAdd);

        super.updateStorage(taskList, storage);

        assert sizeBeforeAdding == taskList.size() - 1;

        ui.setSavedMessage(String.format("\tadded: %s to your list of tasks\n\t"
                + "You now have %d tasks", deadlineToAdd, taskList.size()));
    }

    /**
     * Extracts <code>taskDescription</code> and <code>dueDate</code> from the array of user inputs
     * Creates deadline task to be added later
     *
     * @param userInputArray array containing user inputs required to create a Deadline object
     * @throws IllegalArgumentException if dueDate or taskDescription is empty,
     *              or if dueDate is not in a valid format
     */
    private void setAttributes(String[] userInputArray) {
        Parser parser = new Parser();

        String taskDescription = parser.parseDescription(userInputArray);
        String dueDateString = parser.parseTokens(userInputArray, "/by");

        if (dueDateString.isEmpty() || taskDescription.isEmpty()) {
            throw new IllegalArgumentException("Please provide the correct arguments for Deadline!");
        }

        DateParser dateParser = new DateParser();
        Temporal dueDateTemporal = dateParser.parseTemporal(dueDateString)
                .orElseThrow(() -> new IllegalArgumentException("Please enter a valid due date/datetime!"));

        deadlineToAdd = new Deadline(taskDescription, dueDateTemporal);

    }
}
