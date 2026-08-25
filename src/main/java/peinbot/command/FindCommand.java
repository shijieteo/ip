package peinbot.command;

import java.util.stream.IntStream;

public class FindCommand extends Command {
    private String searchPattern;

    public FindCommand(String[] userInput) {
        parseParams(userInput);
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        TaskList filteredList = new TaskList(taskList.stream().filter(x -> x.toString()
                .contains(searchPattern)).toList());
        ui.listTasks(filteredList);
    }

    private void parseParams(String[] userInputArray) {
        this.searchPattern = IntStream.range(1, userInputArray.length).boxed()
                .map(x -> userInputArray[x]).reduce("", (x,y) -> x + y + " ").trim();
    }
}
