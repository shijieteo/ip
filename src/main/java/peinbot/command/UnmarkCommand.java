package peinbot.command;

import peinbot.storage.Storage;
import peinbot.task.Task;
import peinbot.task.TaskList;
import peinbot.ui.Ui;

/**
 * Represents the unmark command within <code>PeinBot</code>
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
     * Unmarks the task in the task list at the user-supplied index
     * Displays a confirmation text to the user on command executed
     *
     * @param taskList list containing tasks created previously by the user
     * @param ui interface used to display output to the user
     * @param storage storage handler used to persist changes made by the command
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            Task unmarkedTask = taskList.get(index);
            unmarkedTask.setIsDone(false);

            ui.printMessage(String.format("\tThe following task was marked as not done:\n\t %s", unmarkedTask));

        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
        try {
            storage.writeData(taskList);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extracts the index in the task list to unmark
     *
     * @param userInputArray array containing user-supplied list index to unmark
     * @throws NumberFormatException if index value provided is not a number
     */
    private void parseParams(String[] userInputArray) {
        try {
            index = Integer.parseInt(userInputArray[1]) - 1;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please enter a valid index :(");
        }
    }
}
