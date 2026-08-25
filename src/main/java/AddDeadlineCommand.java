import java.time.temporal.Temporal;
import java.util.Optional;

public class AddDeadlineCommand extends Command {
    private Temporal dueDate;
    private String taskDescription;


    AddDeadlineCommand(String[] userInput) {
        parseParams(userInput);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Deadline deadlineTask = new Deadline(this.taskDescription, this.dueDate);
        taskList.add(deadlineTask);
        try {
            storage.writeData(taskList);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        ui.printMessage(String.format("\tadded: %s to your list of tasks\n\t" +
                "You now have %d tasks", deadlineTask, taskList.size()));
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

        dueDate = dueDate.trim();

        DateParser dateParser = new DateParser();
        Optional<Temporal> startDateOptional = dateParser.parseDate(dueDate);
        Optional<Temporal> startDateTimeOptional = dateParser.parseDateTime(dueDate);
        Temporal startTemporal = startDateOptional.or(() -> startDateTimeOptional)
                .orElseThrow(() -> new IllegalArgumentException("Please enter a due date/datetime!"));

        this.dueDate = startTemporal;
        this.taskDescription = taskDescription;


    }
}
