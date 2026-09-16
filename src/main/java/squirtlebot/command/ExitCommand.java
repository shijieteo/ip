package squirtlebot.command;

import squirtlebot.storage.Storage;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Represents the exit command within {@code SquirtleBot}.
 */
public class ExitCommand extends Command {

    /**
     * Displays an exit message to the user.
     *
     * @param tasks list containing tasks created previously by the user.
     * @param ui interface used to display exit message to the user.
     * @param storage storage handler used to persist changes made by the command.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.setSavedMessage("\tBye. Hope to see you soon :(");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldExit() {
        return true;
    }


}
