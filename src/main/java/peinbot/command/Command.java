package peinbot.command;

import peinbot.storage.Storage;
import peinbot.task.TaskList;
import peinbot.ui.Ui;

public abstract class Command {

    public abstract void execute(TaskList taskList, Ui ui, Storage storage);

    public boolean isExit() {
        return false;
    }
}
