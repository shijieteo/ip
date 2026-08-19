public class Event extends Task {
    private String startDate;
    private String endDate;

    Event(String taskDescription, String startDate, String endDate) {
        super(taskDescription);
        this.startDate = startDate.trim();
        this.endDate = endDate.trim();
    }

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), this.startDate, this.endDate);
    }
}
