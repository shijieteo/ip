package peinbot.command;

import peinbot.storage.Storage;
import peinbot.task.TaskList;
import peinbot.ui.Ui;

public class ExitCommand extends Command {

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.printMessage("\tBye. Hope to see you soon :(");
    }

    @Override
    public boolean isExit() {
        return true;
    }


}
