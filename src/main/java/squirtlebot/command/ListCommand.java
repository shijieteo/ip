package squirtlebot.command;

import squirtlebot.storage.Storage;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Represents the list command within <code>SquirtleBot</code>
 */
public class ListCommand extends Command {

    /**
     * Displays the tasks within the task list to the user
     *
     * @param taskList list containing tasks created previously by the user
     * @param ui interface used to display output to the user
     * @param storage storage handler used to persist changes made by the command
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.listTasks(taskList);
    }
}
