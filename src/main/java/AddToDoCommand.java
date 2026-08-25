import java.util.stream.IntStream;

public class AddToDoCommand extends Command {
    private String taskDescription;

    public AddToDoCommand(String[] userInput) {
        parseDescription(userInput);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ToDo toDoTask = new ToDo(this.taskDescription);
        taskList.add(toDoTask);
        try {
            storage.writeData(taskList);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        ui.printMessage(String.format("\tadded: %s to your list of tasks\n\t" +
                "You now have %d tasks", toDoTask, taskList.size()));
    }


    private void parseDescription(String[] userInputArray){
        this.taskDescription = IntStream.range(1, userInputArray.length).boxed()
                .map(x -> userInputArray[x]).reduce("", (x,y) -> x + " " + y);

        if(taskDescription.isEmpty()){
            throw new IllegalArgumentException("Please provide the correct arguments for ToDo!");
        }

    }
}
