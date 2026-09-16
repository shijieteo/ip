package squirtlebot.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import squirtlebot.TemporalPair;
import squirtlebot.exception.CommandException;
import squirtlebot.parser.DateParser;
import squirtlebot.parser.Parser;
import squirtlebot.storage.Storage;
import squirtlebot.task.Event;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Represents the event command within {@code SquirtleBot}.
 */
public class AddEventCommand extends Command {
    private static final String INVALID_DATETIME_FORMAT_MESSAGE = "Invalid date/datetime detected!";
    private static final String START_DATE_TOKEN = "/from";
    private static final String END_DATE_TOKEN = "/to";

    private Event eventToAdd;

    /**
     * Constructs a new AddEventCommand using inputs provided by a user.
     *
     * @param inputTokens array containing user inputs required to create an Event object.
     */
    public AddEventCommand(String[] inputTokens) {
        setAttributes(inputTokens);
    }

    /**
     * Adds the previously created Event object to the list of tasks.
     * Updates storage to reflect the newly added Event.
     * Uses Ui to store a message reflecting the newly added task.
     *
     * @param tasks list containing tasks created previously by the user.
     * @param ui interface used to display output to the user.
     * @param storage storage handler used to persist changes made by the command.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        int sizeBeforeAdding = tasks.size();

        tasks.add(eventToAdd);

        super.updateStorage(tasks, storage);

        assert sizeBeforeAdding == tasks.size() - 1;

        ui.setSavedMessage(String.format("\tadded: %s to your list of tasks\n"
                + "You now have %d tasks", eventToAdd, tasks.size()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof AddEventCommand otherAddEventCommand) {
            return eventToAdd.equals(otherAddEventCommand.eventToAdd);
        } else {
            return false;
        }
    }

    /**
     * Extracts {@code taskDescription} and start and end date pairs from the user input.
     * Queries dateParser repeatedly to parse possible start and end dates.
     * Creates the event object to be added when executed.
     *
     * @param inputTokens array containing user inputs required to create an Event object.
     * @throws CommandException if any of taskDescription, startDate or endDate is empty
     *                  or if any of startDate or endDate is not in a valid format.
     */
    private void setAttributes(String[] inputTokens) {
        Parser parser = new Parser();
        DateParser dateParser = new DateParser();

        String taskDescription = parser.parseDescription(inputTokens);
        ArrayList<TemporalPair> possibleSchedules = new ArrayList<TemporalPair>();

        Stream.iterate(1, x -> x < inputTokens.length, x -> x + 1)
                .filter(index -> {
                    String currentToken = inputTokens[index];
                    return currentToken.equals(START_DATE_TOKEN);
                }).map(index -> {
                    String startDate = parser.parseTokens(Arrays
                            .copyOfRange(inputTokens, index, inputTokens.length), START_DATE_TOKEN);
                    String endDate = parser.parseTokens(Arrays
                            .copyOfRange(inputTokens, index, inputTokens.length), END_DATE_TOKEN);

                    Temporal startTemporal = dateParser.parseTemporal(startDate)
                            .orElseThrow(() -> new CommandException(INVALID_DATETIME_FORMAT_MESSAGE));

                    Temporal endTemporal = dateParser.parseTemporal(endDate)
                            .orElseThrow(() -> new CommandException(INVALID_DATETIME_FORMAT_MESSAGE));

                    validateTemporal(startTemporal, endTemporal);

                    return new TemporalPair(startTemporal, endTemporal);
                }).forEach(x -> possibleSchedules.add(x));

        if (taskDescription.isEmpty() || possibleSchedules.isEmpty()) {
            throw new CommandException("Please provide the correct arguments for Event!");
        }

        eventToAdd = new Event(taskDescription, possibleSchedules);
    }

    private void validateTemporal(Temporal startTemporal, Temporal endTemporal) {
        LocalDateTime startDateTime = startTemporal instanceof LocalDate startDate
                ? startDate.atStartOfDay()
                : (LocalDateTime) startTemporal;

        LocalDateTime endDateTime = endTemporal instanceof LocalDate endDate
                ? endDate.atStartOfDay()
                : (LocalDateTime) endTemporal;

        if (startDateTime.isAfter(endDateTime)) {
            throw new CommandException("Event start date has to be earlier than end date!");
        }
    }
}
