package peinbot.command;

import java.time.temporal.Temporal;
import java.util.Optional;

import peinbot.parser.DateParser;
import peinbot.storage.Storage;
import peinbot.task.Event;
import peinbot.task.TaskList;
import peinbot.ui.Ui;

/**
 * Represents the event command within <code>PeinBot</code>
 * Contains the values required to create an Event object
 */
public class AddEventCommand extends Command {
    private Temporal startDate;
    private String taskDescription;
    private Temporal endDate;

    /**
     * Constructs a new AddEventCommand using inputs provided by a user
     *
     * @param userInput array containing user inputs required to create an Event object
     */
    public AddEventCommand(String[] userInput) {
        parseParams(userInput);
    }

    /**
     * Creates an Event object based off user-provided
     * values and adds to an existing task list
     * Updates user on current state of the task list
     *
     * @param taskList list containing tasks created previously by the user
     * @param ui interface used to display output to the user
     * @param storage storage handler used to persist changes made by the command
     * @throws RuntimeException if an issue was encountered while attempting to write to storage
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Event eventTask = new Event(taskDescription, startDate, endDate);
        taskList.add(eventTask);
        try {
            storage.writeData(taskList);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        ui.printMessage(String.format("\tadded: %s to your list of tasks\n\t"
                + "You now have %d tasks", eventTask, taskList.size()));
    }

    /**
     * Extracts <code>taskDescription</code>, <code>startDate</code>
     * and <code>endDate</code> from the array of user inputs
     *
     * @param userInputArray array containing user inputs required to create an Event object
     * @throws IllegalArgumentException if any of taskDescription, startDate or endDate is empty
     *                  or if any of startDate or endDate is not in a valid format
     */
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
