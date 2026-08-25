import java.util.ArrayList;

public class AddEventCommand extends Command {
    private String startDate;
    private String taskDescription;
    private String endDate;

    AddEventCommand(String[] userInput){
        parseParams(userInput);
    }

    public void execute(ArrayList<Task> taskList, Ui ui, Storage storage) {

    }


    private void parseParams(String[] userInputArray) {
        boolean isEndDate = false;
        boolean isStartDate = false;
        String startDate = "";
        String endDate = "";
        int index = 1;
        String taskDescription = "";

        while(index < userInputArray.length) {
            if(userInputArray[index].equals("/from")) {
                isStartDate = true;
                isEndDate = false;
                index++;
                continue;
            } else if (userInputArray[index].equals("/to")) {
                isEndDate = true;
                isStartDate = false;
                index++;
                continue;
            }
            if(isEndDate) {
                endDate += userInputArray[index];
                endDate += " ";
            } else if (isStartDate) {
                startDate += userInputArray[index];
                startDate += " ";
            } else {
                taskDescription += userInputArray[index];
                taskDescription += " ";
            }
            index++;
        }
    }
}
