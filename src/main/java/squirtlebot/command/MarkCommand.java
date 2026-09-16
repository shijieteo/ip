package squirtlebot.command;

import squirtlebot.exception.CommandException;
import squirtlebot.storage.Storage;
import squirtlebot.task.Task;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Represents the mark command within {@code SquirtleBot}.
 */
public class MarkCommand extends Command {
    private int index;

    /**
     * Constructs a new MarkCommand object using user inputs.
     *
     * @param userInput array containing index value required to create a MarkCommand object.
     */
    public MarkCommand(String[] userInput) {
        parseParams(userInput);
    }

    /**
     * Marks the task in the task list at the user-provided index.
     * Displays a confirmation text to the user on command executed.
     *
     * @param tasks list containing tasks created previously by the user.
     * @param ui interface used to display output to the user.
     * @param storage storage handler used to persist changes made by the command.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        validateIndex(index, tasks);

        Task markedTask = tasks.get(index);
        markedTask.setIsDone(true);

        assert markedTask.isDone();

        super.updateStorage(tasks, storage);

        ui.setSavedMessage(String.format("\tCongrats on completing the following task:\n%s", markedTask));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof MarkCommand otherMarkCommand) {
            return index == otherMarkCommand.index;
        } else {
            return false;
        }
    }

    /**
     * Extracts the index within task list to mark.
     *
     * @param userInputArray array containing index in task list to mark.
     * @throws CommandException if index value provided is not a number.
     */
    private void parseParams(String[] userInputArray) {
        try {
            index = Integer.parseInt(userInputArray[1]) - 1;
        } catch (NumberFormatException e) {
            throw new CommandException("Please insert a valid index :(", e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CommandException("Please enter an index to mark :(", e);
        }
    }
}
