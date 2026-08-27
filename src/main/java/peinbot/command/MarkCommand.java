package peinbot.command;

import peinbot.storage.Storage;
import peinbot.task.Task;
import peinbot.task.TaskList;
import peinbot.ui.Ui;

/**
 * Represents the mark command within <code>PeinBot</code>
 */
public class MarkCommand extends Command {
    private int index;

    /**
     * Constructs a new MarkCommand object using user inputs
     *
     * @param userInput array containing index value required to create a MarkCommand object
     */
    public MarkCommand(String[] userInput) {
        parseParams(userInput);
    }

    /**
     * Marks the task in the task list at the user-provided index
     * Displays a confirmation text to the user on command executed
     *
     * @param taskList list containing tasks created previously by the user
     * @param ui interface used to display output to the user
     * @param storage storage handler used to persist changes made by the command
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            Task markedTask = taskList.get(index);
            markedTask.setIsDone(true);
            ui.printMessage(String.format("\tCongrats on completing the following task:\n\t %s", markedTask));
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
     * Extracts the index within task list to mark
     *
     * @param userInputArray array containing index in task list to mark
     * @throws NumberFormatException if index value provided is not a number
     */
    private void parseParams(String[] userInputArray) {
        try {
            index = Integer.parseInt(userInputArray[1]) - 1;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please insert a valid index :(");
        }
    }
}
