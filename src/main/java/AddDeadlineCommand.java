import java.util.ArrayList;

public class AddDeadlineCommand extends Command {
    private String dueDate;
    private String taskDescription;


    AddDeadlineCommand(String[] userInput) {
        parseParams(userInput);
    }

    @Override
    public void execute(ArrayList<Task> taskList, Ui ui, Storage storage) {

    }


    private void parseParams(String[] userInputArray) {
        String dueDate = "";
        String taskDescription = "";
        boolean isDueDate = false;
        int index = 1;
        while(index < userInputArray.length) {
            if(userInputArray[index].equals("/by")) {
                isDueDate = true;
                index++;
                continue;
            }

            if(isDueDate) {
                dueDate += userInputArray[index];
                dueDate += " ";
            }
            else {
                taskDescription += userInputArray[index];
                taskDescription += " ";
            }
            index++;
        }
        if(dueDate.isEmpty() || taskDescription.isEmpty()){
            throw new IllegalArgumentException("Please provide the correct arguments for Deadline!");
        }
        this.dueDate = dueDate;
        this.taskDescription = taskDescription;
    }
}
