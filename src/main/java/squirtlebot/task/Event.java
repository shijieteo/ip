package squirtlebot.task;

import java.time.temporal.Temporal;

/**
 * Represents the event task that users can add to their list of tasks
 * Contains a task description, start date and an end date for the event
 */
public class Event extends Task {
    private Temporal startDate;
    private Temporal endDate;

    /**
     * Constructs a new event task based on user-provided parameters
     * @param taskDescription a description of the event
     * @param startDate a date/datetime representing the start of the event
     * @param endDate a date/datetime representing the end of the event
     */
    public Event(String taskDescription, Temporal startDate, Temporal endDate) {
        super(taskDescription);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), startDate, endDate);
    }
}
