public class Deadline extends Task {
    private String dueDate;

    Deadline(String taskDescription, String dueDate) {
        super(taskDescription);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), this.dueDate);
    }
}
