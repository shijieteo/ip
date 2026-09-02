package squirtlebot.command;

import squirtlebot.storage.Storage;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Provides a base implementation for commands used in <code>SquirtleBot</code>
 * Subclasses must implement the logic for running commands by overriding the
 * {@link #execute(TaskList, Ui, Storage)} method
 */
public abstract class Command {

    /**
     * Performs the actions of the command, applying its actions to the given task list,
     * user interfaces, and storage
     *
     * @param taskList list containing tasks created previously by the user
     * @param ui interface used to display output to the user
     * @param storage storage handler used to persist changes made by the command
     */
    public abstract void execute(TaskList taskList, Ui ui, Storage storage);


    /**
     * Indicates whether the user-issued command was to exit the program
     *
     *  @return true if the issued command is to exit <code>SquirtleBot</code>; false otherwise
     */
    public boolean shouldExit() {
        return false;
    }
}
