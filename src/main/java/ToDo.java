public class ToDo extends Task {
    ToDo(String taskDescription) {
        super(taskDescription);
    }

    @Override
    public String toString() {
        return String.format("[T] %s", super.toString());
    }
}
