package peinbot.command;

import peinbot.storage.Storage;
import peinbot.task.Task;
import peinbot.task.TaskList;
import peinbot.ui.Ui;

public class UnmarkCommand extends Command {
    private int index;

    public UnmarkCommand(String[] userInput) {
        parseParams(userInput);
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task unmarkedTask = taskList.get(index);
        unmarkedTask.setIsDone(false);
        ui.printMessage(String.format("\tThe following task was marked as not done:\n\t %s", unmarkedTask));

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
            throw new NumberFormatException("Please enter a valid index :(");
        }
    }
}
