public class UnmarkCommand extends Command {
    private int index;

    UnmarkCommand(String[] userInput) {
        parseParams(userInput);
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try{
            Task task = taskList.get(this.index);
            task.setIsDone(false);
            ui.printMessage(String.format("\tThe following task was marked as not done:\n\t %s", task));

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
            throw new NumberFormatException("Please enter a valid index :(");
        }
    }
}
