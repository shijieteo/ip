public class AddEventCommand extends Command {
    private String startDate;
    private String taskDescription;
    private String endDate;

    AddEventCommand(String[] userInput){
        parseParams(userInput);
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Event eventTask = new Event(this.taskDescription, this.startDate, this.endDate);
        taskList.add(eventTask);
        try {
            storage.writeData(taskList);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        ui.printMessage(String.format("\tadded: %s to your list of tasks\n\t" +
                "You now have %d tasks", eventTask, taskList.size()));
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
        if(startDate.isEmpty() || taskDescription.isEmpty() || endDate.isEmpty()){
            throw new IllegalArgumentException("Please provide the correct arguments for Event!");
        }
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskDescription = taskDescription;
    }
}
