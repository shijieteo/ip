package squirtlebot.command;

import java.time.temporal.Temporal;

import squirtlebot.parser.DateParser;
import squirtlebot.parser.Parser;
import squirtlebot.storage.Storage;
import squirtlebot.task.Event;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Represents the event command within <code>SquirtleBot</code>
 * Contains the values required to create an Event object
 */
public class AddEventCommand extends Command {
    private static final String START_DATE_TOKEN = "/from";
    private static final String END_DATE_TOKEN = "/to";

    private Temporal startDate;
    private String taskDescription;
    private Temporal endDate;

    /**
     * Constructs a new AddEventCommand using inputs provided by a user
     *
     * @param userInput array containing user inputs required to create an Event object
     */
    public AddEventCommand(String[] userInput) {
        setAttributes(userInput);
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

        super.updateStorage(taskList, storage);

        ui.setSavedMessage(String.format("\tadded: %s to your list of tasks\n\t"
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
    private void setAttributes(String[] userInputArray) {
        Parser parser = new Parser();

        String taskDescription = parser.parseDescription(userInputArray);
        String startDate = parser.parseTokens(userInputArray, START_DATE_TOKEN);
        String endDate = parser.parseTokens(userInputArray, END_DATE_TOKEN);

        if(taskDescription.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            throw new IllegalArgumentException("Please provide the correct arguments for Event!");
        }

        DateParser dateParser = new DateParser();
        Temporal startTemporal = dateParser.parseTemporal(startDate)
                .orElseThrow(() -> new IllegalArgumentException("Please enter a valid start date/datetime!"));

        Temporal endTemporal = dateParser.parseTemporal(endDate)
                .orElseThrow(() -> new IllegalArgumentException("Please enter a valid end date/datetime!"));

        this.startDate = startTemporal;
        this.endDate = endTemporal;
        this.taskDescription = taskDescription;
    }
}
