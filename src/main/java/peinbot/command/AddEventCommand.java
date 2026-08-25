package peinbot.command;

import java.time.temporal.Temporal;
import java.util.Optional;

public class AddEventCommand extends Command {
    private Temporal startDate;
    private String taskDescription;
    private Temporal endDate;

    public AddEventCommand(String[] userInput) {
        parseParams(userInput);
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Event eventTask = new Event(taskDescription, startDate, endDate);
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

        while (index < userInputArray.length) {
            if (userInputArray[index].equals("/from")) {
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
            if (isEndDate) {
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
        if (startDate.isEmpty() || taskDescription.isEmpty() || endDate.isEmpty()) {
            throw new IllegalArgumentException("Please provide the correct arguments for Event!");
        }

        startDate = startDate.trim();
        endDate = endDate.trim();

        DateParser dateParser = new DateParser();
        Optional<Temporal> startDateOptional = dateParser.parseDate(startDate);
        Optional<Temporal> startDateTimeOptional = dateParser.parseDateTime(startDate);
        Temporal startTemporal = startDateOptional.or(() -> startDateTimeOptional)
                .orElseThrow(() -> new IllegalArgumentException("Please enter a start valid date/datetime!"));

        Optional<Temporal> endDateOptional = dateParser.parseDate(endDate);
        Optional<Temporal> endDateTimeOptional = dateParser.parseDateTime(endDate);
        Temporal endTemporal = endDateOptional.or(() -> endDateTimeOptional)
                .orElseThrow(() -> new IllegalArgumentException("Please enter a end valid date/datetime!"));

        this.startDate = startTemporal;
        this.endDate = endTemporal;
        this.taskDescription = taskDescription;
    }
}
