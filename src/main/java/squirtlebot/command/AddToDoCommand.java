package squirtlebot.command;

import squirtlebot.parser.Parser;
import squirtlebot.storage.Storage;
import squirtlebot.task.TaskList;
import squirtlebot.task.ToDo;
import squirtlebot.ui.Ui;

/**
 * Represents the todo command within <code>SquirtleBot</code>
 */
public class AddToDoCommand extends Command {
    private ToDo toDoToAdd;

    /**
     * Constructs a new AddToDoCommand using inputs provided by a user
     *
     * @param userInput array containing user inputs required to create a ToDo object
     */
    public AddToDoCommand(String[] userInput) {
        setAttributes(userInput);
    }

    /**
     * Adds previously created ToDo task to existing task list.
     * Updates user on current state of the task list
     *
     * @param taskList list containing tasks created previously by the user
     * @param ui interface used to display output to the user
     * @param storage storage handler used to persist changes made by the command
     * @throws RuntimeException if an issue was encountered while attempting to write to storage
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        int sizeBeforeAdding = taskList.size();

        taskList.add(toDoToAdd);

        super.updateStorage(taskList, storage);

        assert sizeBeforeAdding == taskList.size() - 1;

        ui.setSavedMessage(String.format("\tadded: %s to your list of tasks\n\t"
                + "You now have %d tasks", toDoToAdd, taskList.size()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof AddToDoCommand otherAddToDoCommand) {
            return toDoToAdd.equals(otherAddToDoCommand.toDoToAdd);
        } else {
            return false;
        }
    }

    /**
     * Reassembles user input to form task description for ToDo object
     * Creates ToDo task according to user input
     *
     * @param userInputArray array containing user inputs required to create a ToDo object
     * @throws IllegalArgumentException if taskDescription is empty
     */
    private void setAttributes(String[] userInputArray) {
        Parser parser = new Parser();

        String taskDescription = parser.parseDescription(userInputArray);

        if (taskDescription.isEmpty()) {
            throw new IllegalArgumentException("Please provide the correct arguments for ToDo!");
        }

        this.toDoToAdd = new ToDo(taskDescription);
    }
}
