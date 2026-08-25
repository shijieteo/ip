import java.time.LocalDate;
import java.time.temporal.Temporal;

public class Event extends Task {
    private Temporal startDate;
    private Temporal endDate;

    Event(String taskDescription, Temporal startDate, Temporal endDate) {
        super(taskDescription);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), this.startDate, this.endDate);
    }
}
