package squirtlebot.command;

import squirtlebot.storage.Storage;
import squirtlebot.task.Task;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Represents the delete command within <code>SquirtleBot</code>
 * Contains the values required to create a <code>DeleteCommand</code> object
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * Constructs a DeleteCommand object using user inputs
     *
     * @param userInput array containing index value required for creating a DeleteCommand object
     */
    public DeleteCommand(String[] userInput) {
        parseParams(userInput);
    }

    /**
     * Deletes a user-specified task from task list
     *
     * @param taskList list containing tasks created previously by the user
     * @param ui interface used to display output to the user
     * @param storage storage handler used to persist changes made by the command
     * @throws RuntimeException if an issue was encountered while attempting to write to storage
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task removedTask = taskList.remove(index);
        ui.setSavedMessage(String.format("\tThe following task was removed:\n\t %s", removedTask));
        try {
            storage.writeData(taskList);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extracts the index to delete from an array of user inputs
     *
     * @param userInputArray array containing the task list index to delete from
     * @throws NumberFormatException if index value provided is not a number
     */
    private void parseParams(String[] userInputArray) {
        try {
            index = Integer.parseInt(userInputArray[1]) - 1;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please enter a valid index :( ");
        }
    }
}
