package squirtlebot.command;

import squirtlebot.storage.Storage;
import squirtlebot.task.Event;
import squirtlebot.task.Task;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

public class ConfirmEventDateCommand extends Command {
    private int tasksIndex;
    private int confirmedDateIndex;

    public ConfirmEventDateCommand(String[] userInputArray) {
        setAttributes(userInputArray);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task task = taskList.get(tasksIndex);
        if (!(task instanceof Event eventToConfirm)) {
            throw new IllegalArgumentException("Selected event was not an Event!");
        }
        eventToConfirm.confirmEventDate(confirmedDateIndex);
        ui.setSavedMessage("Event updated:\n" + eventToConfirm);
        updateStorage(taskList, storage);
    }

    private void setAttributes(String[] userInputArray) {
        try {
            tasksIndex = Integer.parseInt(userInputArray[1]) - 1;
            confirmedDateIndex = Integer.parseInt(userInputArray[2]) - 1;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please enter a valid index :( ");
        }
    }
}
