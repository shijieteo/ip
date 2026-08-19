public class Event extends Task {
    private String startDate;
    private String dueDate;

    Event(String taskDescription, String startDate, String dueDate) {
        super(taskDescription);
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), this.startDate, this.dueDate);
    }
}
