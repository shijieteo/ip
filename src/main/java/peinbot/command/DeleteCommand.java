package peinbot.command;

import peinbot.storage.Storage;
import peinbot.task.TaskList;
import peinbot.task.Task;
import peinbot.ui.Ui;

public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(String[] userInput) {
        parseParams(userInput);
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            Task removedTask = taskList.remove(index);
            ui.printMessage(String.format("\tThe following task was removed:\n\t %s", removedTask));

        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
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
            throw new NumberFormatException("Please enter a valid index :( ");
        }
    }
}
