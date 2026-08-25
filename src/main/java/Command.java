import java.util.ArrayList;

public abstract class Command {

    public abstract void execute(ArrayList<Task> taskList, Ui ui, Storage storage);

    public boolean isExit() {
        return false;
    }
}
