package peinbot.task;

public class ToDo extends Task {
    public ToDo(String taskDescription) {
        super(taskDescription);
    }

    @Override
    public String toString() {
        return String.format("[T] %s", super.toString());
    }
}
