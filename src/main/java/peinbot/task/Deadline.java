package peinbot.task;

import java.time.temporal.Temporal;

/**
 * Represents the deadline task that users can add to their list of tasks
 * Contains a task description and a due date for the task
 */
public class Deadline extends Task {
    private Temporal dueDate;

    /**
     * Constructs a new deadline task based on user-provided parameters
     * @param taskDescription a description of the task to complete
     * @param dueDate a date/datetime indicating the due date for the task
     */
    public Deadline(String taskDescription, Temporal dueDate) {
        super(taskDescription);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), dueDate);
    }
}
