package squirtlebot.command;

import java.util.stream.IntStream;

import squirtlebot.storage.Storage;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Represents the find command within <code>SquirtleBot</code>
 */
public class FindCommand extends Command {
    private String searchPattern;

    /**
     * Constructs a new FindCommand object using inputs provided by a user
     *
     * @param userInput array containing user inputs required to create a FindCommand object
     */
    public FindCommand(String[] userInput) {
        parseParams(userInput);
    }

    /**
     * Filters the task list for tasks containing the user-supplied string
     * Displays the filtered tasks to the user
     *
     * @param taskList list containing tasks created previously by the user
     * @param ui interface used to display output to the user
     * @param storage storage handler used to persist changes made by the command
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        TaskList filteredList = new TaskList(taskList.stream().filter(x -> x.toString()
                .contains(searchPattern)).toList());
        ui.listTasks(filteredList);
    }

    /**
     * Extracts string to be searched for within the task list
     *
     * @param userInputArray array containing user-supplied search string
     */
    private void parseParams(String[] userInputArray) {
        this.searchPattern = IntStream.range(1, userInputArray.length).boxed()
                .map(x -> userInputArray[x]).reduce("", (x, y) -> x + y + " ").trim();
    }
}
