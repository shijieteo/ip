import java.util.ArrayList;

/**
 * Example of a command that signals program termination.
 * It overrides {@link #isExit()} instead of returning a magic boolean from a switch.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(ArrayList<Task> taskList, Ui ui, Storage storage) {
        ui.printMessage("\tBye. Hope to see you soon :(");
    }



    @Override
    public boolean isExit() {
        return true;
    }


}
