package peinbot.command;

import peinbot.storage.Storage;
import peinbot.task.Task;
import peinbot.task.TaskList;
import peinbot.ui.Ui;

public class MarkCommand extends Command {
    private int index;

    public MarkCommand(String[] userInput) {
        parseParams(userInput);
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task markedTask = taskList.get(index);
        markedTask.setIsDone(true);
        ui.printMessage(String.format("\tCongrats on completing the following task:\n\t %s", markedTask));
        try {
            storage.writeData(taskList);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseParams(String[] userInputArray) {
        try {
            index = Integer.parseInt(userInputArray[1]) - 1;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please insert a valid index :(");
        }
    }
}
