package squirtlebot.command;

import squirtlebot.exception.CommandException;
import squirtlebot.storage.Storage;
import squirtlebot.task.Task;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Represents the unmark command within <code>SquirtleBot</code>
 */
public class UnmarkCommand extends Command {
    private int index;

    /**
     * Constructs an UnmarkCommand object using user inputs
     *
     * @param userInput array containing index value required to create an UnmarkCommand object
     */
    public UnmarkCommand(String[] userInput) {
        parseParams(userInput);
    }

    /**
     * Unmarks the task in the task list at the user-supplied index<br>
     * Displays a confirmation text to the user on command executed
     *
     * @param taskList list containing tasks created previously by the user
     * @param ui interface used to display output to the user
     * @param storage storage handler used to persist changes made by the command
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        validateIndex(index, taskList);

        Task unmarkedTask = taskList.get(index);
        unmarkedTask.setIsDone(false);
        super.updateStorage(taskList, storage);

        assert !unmarkedTask.isDone();

        ui.setSavedMessage(String.format("\tThe following task was marked as not done:\n\t %s", unmarkedTask));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof UnmarkCommand otherUnmarkCommand) {
            return index == otherUnmarkCommand.index;
        } else {
            return false;
        }
    }

    /**
     * Extracts the index in the task list to unmark
     *
     * @param userInputArray array containing user-supplied list index to unmark
     * @throws CommandException if index value provided is not a number
     */
    private void parseParams(String[] userInputArray) {
        try {
            index = Integer.parseInt(userInputArray[1]) - 1;
        } catch (NumberFormatException e) {
            throw new CommandException("Please enter a valid index :(");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CommandException("Please enter an index to unmark :(", e);
        }
    }
}
