public class DeleteCommand extends Command {
    private int index;

    DeleteCommand(String[] userInput) {
        parseParams(userInput);
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try{
            taskList.remove(this.index);
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
            throw new NumberFormatException("Please enter a valid index :( ");
        }
    }
}
