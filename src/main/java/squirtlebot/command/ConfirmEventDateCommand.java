package squirtlebot.command;

import squirtlebot.exception.CommandException;
import squirtlebot.storage.Storage;
import squirtlebot.task.Event;
import squirtlebot.task.Task;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;


/**
 * Represents the confirm command in {@code SquirtleBot}, allowing users to
 * confirm a single start/end date for an event
 */
public class ConfirmEventDateCommand extends Command {
    private int tasksIndex;
    private int confirmedDateIndex;


    /**
     * Constructs a new ConfirmEventDateCommand using inputs provided by a user
     *
     * @param userInputArray array containing user inputs required to confirm the date of an event
     */
    public ConfirmEventDateCommand(String[] userInputArray) {
        setAttributes(userInputArray);
    }

    /**
     * Retrieves the event to confirm from the task list<br>
     * Verifies if the selected task is an Event<br>
     * Sets the date of the event according to user's input
     *
     * @param tasks list containing tasks created previously by the user
     * @param ui interface used to display output to the user
     * @param storage storage handler used to persist changes made by the command
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {

        validateIndex(tasksIndex, tasks);

        Task task = tasks.get(tasksIndex);
        if (!(task instanceof Event eventToConfirm)) {
            throw new CommandException("Selected event was not an Event!");
        }

        eventToConfirm.confirmEventDate(confirmedDateIndex);

        ui.setSavedMessage("Event updated:\n" + eventToConfirm);

        updateStorage(tasks, storage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof ConfirmEventDateCommand otherConfirmEventDateCommand) {
            boolean areTasksIndicesEqual = tasksIndex == otherConfirmEventDateCommand.tasksIndex;
            boolean areConfirmedDateIndicesEqual = confirmedDateIndex
                    == otherConfirmEventDateCommand.confirmedDateIndex;

            return areTasksIndicesEqual && areConfirmedDateIndicesEqual;
        } else {
            return false;
        }
    }

    /**
     * Extracts two indices required to confirm the date for an event.
     *
     * @param userInputArray array containing two indices required to confirm an event's date
     * @throws CommandException if provided indices are not numbers
     */
    private void setAttributes(String[] userInputArray) {
        try {
            tasksIndex = Integer.parseInt(userInputArray[1]) - 1;
            confirmedDateIndex = Integer.parseInt(userInputArray[2]) - 1;
        } catch (NumberFormatException e) {
            throw new CommandException("Please enter a valid index :(", e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CommandException("Please enter an index to confirm dates for :(", e);
        }
    }
}
