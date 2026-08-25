import java.util.ArrayList;

public class DeleteCommand extends Command {
    private int index;

    DeleteCommand(String[] userInput) {
        parseParams(userInput);
    }

    public void execute(ArrayList<Task> taskList, Ui ui, Storage storage) {
        try{
            taskList.remove(this.index);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseParams(String[] userInputArray) {
        try {
            this.index = Integer.parseInt(userInputArray[1]) - 1;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
