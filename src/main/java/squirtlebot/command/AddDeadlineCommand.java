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
 * Contains the values required to create a Deadline object
 */
public class AddDeadlineCommand extends Command {
    private Temporal dueDate;
    private String taskDescription;


    /**
     * Constructs a new AddDeadlineCommand using inputs provided by a user
     *
     * @param userInput array containing user inputs required to create a Deadline object
     */
    public AddDeadlineCommand(String[] userInput) {
        setAttributes(userInput);
    }

    /**
     * Creates a Deadline object based off user-provided
     * values and adds to an existing task list
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

        Deadline deadlineTask = new Deadline(taskDescription, dueDate);
        taskList.add(deadlineTask);

        super.updateStorage(taskList, storage);

        assert sizeBeforeAdding == taskList.size() - 1;

        ui.setSavedMessage(String.format("\tadded: %s to your list of tasks\n\t"
                + "You now have %d tasks", deadlineTask, taskList.size()));
    }

    /**
     * Extracts <code>taskDescription</code> and <code>dueDate</code> from the array of user inputs
     *
     * @param userInputArray array containing user inputs required to create a Deadline object
     * @throws IllegalArgumentException if dueDate or taskDescription is empty,
     *              or if dueDate is not in a valid format
     */
    private void setAttributes(String[] userInputArray) {
        Parser parser = new Parser();

        String taskDescription = parser.parseDescription(userInputArray);
        String dueDate = parser.parseTokens(userInputArray, "/by");

        if (dueDate.isEmpty() || taskDescription.isEmpty()) {
            throw new IllegalArgumentException("Please provide the correct arguments for Deadline!");
        }

        DateParser dateParser = new DateParser();

        this.taskDescription = taskDescription;
        this.dueDate = dateParser.parseTemporal(dueDate)
                .orElseThrow(() -> new IllegalArgumentException("Please enter a valid due date/datetime!"));

    }
}
