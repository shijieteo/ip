package peinbot.task;

/**
 * Represents the ToDo task that users can add to their list of tasks
 * Contains a task description for the task
 */
public class ToDo extends Task {
    public ToDo(String taskDescription) {
        super(taskDescription);
    }

    @Override
    public String toString() {
        return String.format("[T] %s", super.toString());
    }
}
