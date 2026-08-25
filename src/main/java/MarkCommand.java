public class MarkCommand extends Command {
    private int index;

    MarkCommand(String[] userInput) {
        parseParams(userInput);
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try{
            Task task = taskList.get(this.index);
            task.setIsDone(true);
            ui.printMessage(String.format("\tCongrats on completing the following task:\n\t %s", task));
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
            this.index = Integer.parseInt(userInputArray[1]) - 1;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please insert a valid index :(");
        }
    }
}
