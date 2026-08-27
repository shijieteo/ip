package peinbot.command;

import java.time.temporal.Temporal;
import java.util.Optional;

import peinbot.parser.DateParser;
import peinbot.storage.Storage;
import peinbot.task.Deadline;
import peinbot.task.TaskList;
import peinbot.ui.Ui;



/**
 * Represents the deadline command within <code>PeinBot</code>.
 * Contains the values required to create a <code>Deadline</code> task
 */
public class AddDeadlineCommand extends Command {
    private Temporal dueDate;
    private String taskDescription;


    /**
     * Constructs a new AddDeadlineCommand using inputs provided by a user
      * @param userInput array containing user inputs required to create a <code>Deadline</code> object
     */
    public AddDeadlineCommand(String[] userInput) {
        parseParams(userInput);
    }

    /**
     * Creates a <code>Deadline</code> object based off user-provided
     * values and adds to an existing <code>TaskList</code>
     * Updates user on current state of tasklist
     * @param taskList tasklist containing currently active tasks created by user
     * @param ui interface used to inform user of current state of <code>TaskList</code>
     * @param storage interface to write newly-created <code>Deadline</code> object to storage
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Deadline deadlineTask = new Deadline(taskDescription, dueDate);
        taskList.add(deadlineTask);
        try {
            storage.writeData(taskList);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        ui.printMessage(String.format("\tadded: %s to your list of tasks\n\t"
                + "You now have %d tasks", deadlineTask, taskList.size()));
    }

    /**
     * Extracts <code>taskDescription</code> and <code>dueDate</code> from the array of user inputs
     * @param userInputArray array containing user inputs required to create a <code>Deadline</code> object
     */
    private void parseParams(String[] userInputArray) {
        String dueDate = "";
        String taskDescription = "";
        boolean isDueDate = false;
        int index = 1;
        while (index < userInputArray.length) {
            if (userInputArray[index].equals("/by")) {
                isDueDate = true;
                index++;
                continue;
            }

            if (isDueDate) {
                dueDate += userInputArray[index];
                dueDate += " ";
            } else {
                taskDescription += userInputArray[index];
                taskDescription += " ";
            }
            index++;
        }
        if (dueDate.isEmpty() || taskDescription.isEmpty()) {
            throw new IllegalArgumentException("Please provide the correct arguments for Deadline!");
        }

        dueDate = dueDate.trim();

        DateParser dateParser = new DateParser();
        Optional<Temporal> startDateOptional = dateParser.parseDate(dueDate);
        Optional<Temporal> startDateTimeOptional = dateParser.parseDateTime(dueDate);
        Temporal startTemporal = startDateOptional.or(() -> startDateTimeOptional)
                .orElseThrow(() -> new IllegalArgumentException("Please enter a due date/datetime!"));

        this.dueDate = startTemporal;
        this.taskDescription = taskDescription;
    }
}
