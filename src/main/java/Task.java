import java.io.Serializable;

public class Task implements Serializable {
    private String taskDescription;
    private Boolean isDone;

    Task(String taskDescription) {
        this.taskDescription = taskDescription.trim();
        this.isDone = false;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.isDone ? "X" : " ", this.taskDescription);
    }
}
