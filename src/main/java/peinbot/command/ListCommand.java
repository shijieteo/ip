package peinbot.command;

import peinbot.storage.Storage;
import peinbot.task.TaskList;
import peinbot.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.listTasks(taskList);
    }
}
