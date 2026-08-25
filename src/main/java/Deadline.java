import java.time.temporal.Temporal;

public class Deadline extends Task {
    private Temporal dueDate;

    Deadline(String taskDescription, Temporal dueDate) {
        super(taskDescription);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), this.dueDate);
    }
}
