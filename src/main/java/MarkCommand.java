import java.util.ArrayList;

public class MarkCommand extends Command {
    private int index;

    MarkCommand(String[] userInput) {
        parseParams(userInput);
    }

    public void execute(ArrayList<Task> taskList, Ui ui, Storage storage) {
        try{
            taskList.get(this.index).setIsDone(true);
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
