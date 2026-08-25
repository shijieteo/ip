import java.util.ArrayList;

/**
 * Example of a stateless command: it carries no parsed data,
 * so a single shared instance could even be reused (see CommandFactory).
 */
public class ListCommand extends Command {
    @Override
    public void execute(ArrayList<Task> taskList, Ui ui, Storage storage) {
        ui.listTasks(taskList);
    }
}
