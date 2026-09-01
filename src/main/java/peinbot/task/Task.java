package peinbot.task;

import java.io.Serializable;

/**
 * Provides a base implementation for tasks used in <code>PeinBot</code>
 * Contains common methods used by other subclasses of task
 */
public abstract class Task implements Serializable {
    private String taskDescription;
    private Boolean isDone;

    /**
     * Constructs a new task with a task description
     * This constructor is intended to be used by subclasses of task to set task description
     * @param taskDescription a description of the task
     */
    public Task(String taskDescription) {
        this.taskDescription = taskDescription.trim();
        isDone = false;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : " ", taskDescription);
    }
}
