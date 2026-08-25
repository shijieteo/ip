package peinbot.task;

import java.io.Serializable;

public class Task implements Serializable {
    private String taskDescription;
    private Boolean isDone;

    public Task(String taskDescription) {
        this.taskDescription = taskDescription.trim();
        isDone = false;
    }

    public void setIsDone(Boolean isDone) {
        isDone = isDone;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : " ", taskDescription);
    }
}
