package squirtlebot.task;

import java.time.temporal.Temporal;

import java.util.ArrayList;

import squirtlebot.TemporalPair;

/**
 * Represents the event task that users can add to their list of tasks
 * Contains a task description, start date and an end date for the event
 */
public class Event extends Task {
    private ArrayList<TemporalPair> possibleSchedules;
    private boolean isDateConfirmed;

    public Event(String taskDescription, ArrayList<TemporalPair> possibleSchedules) {
        super(taskDescription);
        this.possibleSchedules = possibleSchedules;
        if (possibleSchedules.size() > 1) {
            isDateConfirmed = false;
        } else if (possibleSchedules.size() == 1) {
            isDateConfirmed = true;
        }
    }

    public void confirmEventDate(int index) {
        if (isDateConfirmed) {
            return;
        }
        TemporalPair confirmedDate = possibleSchedules.get(index);
        possibleSchedules = new ArrayList<>();
        possibleSchedules.add(confirmedDate);

        assert possibleSchedules.size() == 1;
    }

    @Override
    public String toString() {
        String schedulesDisplays = possibleSchedules.stream()
                .map(x -> String.format("from: %s to: %s", x.startDate(), x.endDate()))
                .reduce("", (x,y) -> x + "\n\t" + y)
                .trim();
        return String.format("[E] %s \nPossible Schedules: \n\t%s", super.toString(), schedulesDisplays);
    }
}
