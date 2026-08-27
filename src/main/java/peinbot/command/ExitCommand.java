package peinbot.command;

import peinbot.storage.Storage;
import peinbot.task.TaskList;
import peinbot.ui.Ui;

/**
 * Represents the exit command within <code>PeinBot</code>.
 */
public class ExitCommand extends Command {

    /**
     * Displays an exit message to the user
     *
     * @param taskList list containing tasks created previously by the user
     * @param ui interface used to display exit message to the user
     * @param storage storage handler used to persist changes made by the command
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.printMessage("\tBye. Hope to see you soon :(");
    }

    /**
     * Indicates whether the user-issued command was to exit the program
     *
     * @return true as this is the exit command
     */
    @Override
    public boolean isExit() {
        return true;
    }


}
