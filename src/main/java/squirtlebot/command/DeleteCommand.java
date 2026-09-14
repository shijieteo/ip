package squirtlebot.command;

import squirtlebot.exception.CommandException;
import squirtlebot.storage.Storage;
import squirtlebot.task.Task;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Represents the delete command within <code>SquirtleBot</code>
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
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        int sizeBeforeRemoval = taskList.size();

        try {
            Task removedTask = taskList.remove(index);
            ui.setSavedMessage(String.format("\tThe following task was removed:\n\t %s", removedTask));
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("Please enter a valid index :(", e);
        }

        assert sizeBeforeRemoval == taskList.size() + 1;

        super.updateStorage(taskList, storage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof DeleteCommand otherDeleteCommand) {
            return index == otherDeleteCommand.index;
        } else {
            return false;
        }
    }

    /**
     * Extracts the index to delete from an array of user inputs
     *
     * @param userInputArray array containing the task list index to delete from
     * @throws CommandException if index value provided is not a number
     */
    private void parseParams(String[] userInputArray) {
        try {
            index = Integer.parseInt(userInputArray[1]) - 1;
        } catch (NumberFormatException e) {
            throw new CommandException("Please enter a valid index :(", e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CommandException("Please enter an index to delete :(", e);
        }
    }
}
