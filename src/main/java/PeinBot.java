import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class PeinBot {
    private static final String HORIZONTAL_LINE = "\t_____________________________________________________________";

    private static ArrayList<Task> taskList = new ArrayList<Task>();


    public static void main(String[] args) {
        boolean isExit = false;
        String banner = "__________       .__      ___.           __   \n"
                + "\\______   \\ ____ |__| ____\\_ |__   _____/  |_ \n"
                + " |     ___// __ \\|  |/    \\| __ \\ /  _ \\   __\\\n"
                + " |    |   \\  ___/|  |   |  \\ \\_\\ (  <_> )  |  \n"
                + " |____|    \\___  >__|___|  /___  /\\____/|__|  \n"
                + "               \\/        \\/    \\/             \n";

        System.out.println(PeinBot.HORIZONTAL_LINE);
        System.out.println(banner);
        System.out.println("Hello! I'm PeinBot :)");
        System.out.println(PeinBot.HORIZONTAL_LINE);


        System.out.println("\tWhat can I do for you? ");

        PeinStorage storage = new PeinStorage();

        try {
            PeinBot.taskList = storage.loadData();
        } catch (ClassNotFoundException | IOException e) {
            String userAnswer = "";
            do {
                System.out.print("Could not load tasks from data file... continue? [Y/N]: ");
                userAnswer = readInput();
                if (userAnswer == "N") {
                    isExit = true;
                }
            } while(!userAnswer.equals("Y") && !userAnswer.equals("N"));
        }

        while(!isExit){
            String userInput = readInput();
            isExit = processInput(userInput);
        }

    }

    public static boolean processInput(String userInput) {
        int taskListIndex;
        System.out.println(PeinBot.HORIZONTAL_LINE);
        String[] userInputArray = userInput.split(" ");
        switch (userInputArray[0]) {
            case "bye":
                printOutput("\tBye. Hope to see you soon :(");
                return true;

            case "list":
                listTasks();
                return false;

            case "mark":
                try {
                    taskListIndex = Integer.parseInt(userInputArray[1]) - 1;
                    markTasks(taskListIndex);
                    printOutput(String.format("\tCongrats on completing the following task:\n\t %s",
                            PeinBot.taskList.get(taskListIndex)));
                }
                catch (NumberFormatException numberFormatException) {
                    printOutput("\tPlease provide a number next time... :(");
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    printOutput("\tPlease provide a number within the list :(");
                }

                return false;

            case "unmark":
                try {
                    taskListIndex = Integer.parseInt(userInputArray[1]) - 1;
                    unmarkTasks(taskListIndex);
                    printOutput(String.format("\tThe following task was marked as not done yet:\n\t %s",
                            PeinBot.taskList.get(taskListIndex)));
                }
                catch (NumberFormatException numberFormatException) {
                    printOutput("\tPlease provide a number next time... :(");
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    printOutput("\tPlease provide a number within the list :(");
                }
                return false;
            case "delete":
                try {
                    taskListIndex = Integer.parseInt(userInputArray[1]) - 1;
                    Task deletedTask = deleteTask(taskListIndex);
                    printOutput("\t" + String.format("The following task was deleted: %s \n\t" +
                            "You now have %d tasks", deletedTask, PeinBot.taskList.size()));
                }
                catch (NumberFormatException numberFormatException) {
                    printOutput("\tPlease provide a number next time... :(");
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    printOutput("\tPlease provide a number within the list :(");
                }
                return false;

            default:
                addTasks(userInput);
                return false;
        }
    }

    public static String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void printOutput(String output) {
        System.out.println(output);
        System.out.println(PeinBot.HORIZONTAL_LINE);
    }

    private static void listTasks() {
        String accumulatedTaskString = "";
        int endLoopIndex = PeinBot.taskList.size();
        for (int index = 0; index < endLoopIndex; index++) {
            accumulatedTaskString += String.format("\t%d. %s", index + 1, PeinBot.taskList.get(index));
            if(index != endLoopIndex - 1) {
                accumulatedTaskString += "\n";
            }
        }
        printOutput(accumulatedTaskString);
    }

    private static void markTasks(int index) {
        PeinBot.taskList.get(index).setIsDone(true);
    }

    private static void unmarkTasks(int index) {
        PeinBot.taskList.get(index).setIsDone(false);
    }

    private static void addTasks(String userInput) {
        PeinStorage storage = new PeinStorage();
        String[] taskSplit = userInput.split(" ");
        String taskType = taskSplit[0];
        String taskDescription = "";
        int index;
        String taskString = "";
        try {
            switch (taskType) {
                case "todo":
                    Task toDoTask = createToDoTask(taskSplit);
                    try {
                        storage.writeData(toDoTask);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    PeinBot.taskList.add(toDoTask);
                    taskString = toDoTask.toString();
                    break;

                case "deadline":
                    Task deadlineTask = createDeadlineTask(taskSplit);
                    try {
                        storage.writeData(deadlineTask);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    PeinBot.taskList.add(deadlineTask);
                    taskString = deadlineTask.toString();
                    break;

                case "event":
                    Task eventTask = createEventTask(taskSplit);
                    try {
                        storage.writeData(eventTask);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    PeinBot.taskList.add(eventTask);
                    taskString = eventTask.toString();
                    break;
                default:
                    printOutput("\tInvalid task type....\n\tPlease try again");
                    return;
            }
            printOutput("\t" + String.format("added: %s to your list of tasks\n\t" +
                    "You now have %d tasks", taskString, PeinBot.taskList.size()));
        }


        catch (IllegalArgumentException illegalArgumentException) {
            printOutput("\t" + illegalArgumentException.getMessage());
        }
    }

    private static Task createToDoTask(String[] taskSplit) throws IllegalArgumentException {
        String taskDescription = IntStream.range(1, taskSplit.length).boxed()
                .map(x -> taskSplit[x]).reduce("", (x,y) -> x + " " + y);
        if (taskDescription.isEmpty()){
            throw new IllegalArgumentException("Task Description for task is empty :(");
        }
        return new ToDo(taskDescription);
    }

    private static Task createEventTask(String[] taskSplit) throws IllegalArgumentException {
        boolean isEndDate = false;
        boolean isStartDate = false;
        String startDate = "";
        String endDate = "";
        int index = 1;
        String taskDescription = "";

        while(index < taskSplit.length) {
            if(taskSplit[index].equals("/from")) {
                isStartDate = true;
                isEndDate = false;
                index++;
                continue;
            } else if (taskSplit[index].equals("/to")) {
                isEndDate = true;
                isStartDate = false;
                index++;
                continue;
            }

            if(isEndDate) {
                endDate += taskSplit[index];
                endDate += " ";
            } else if (isStartDate) {
                startDate += taskSplit[index];
                startDate += " ";
            } else {
                taskDescription += taskSplit[index];
                taskDescription += " ";
            }
            index++;
        }
        if (taskDescription.isEmpty()){
            throw new IllegalArgumentException("Task Description for task is empty :(");
        } else if (startDate.isEmpty()) {
            throw new IllegalArgumentException("Start Date for task is empty :(");
        } else if (endDate.isEmpty()) {
            throw new IllegalArgumentException("End Date for task is empty :(");
        }
        return (new Event(taskDescription, startDate, endDate));
    }

    private static Task deleteTask(int index) {
        return PeinBot.taskList.remove(index);
    }

    private static Task createDeadlineTask(String[] taskSplit) throws IllegalArgumentException {
        String dueDate = "";
        String taskDescription = "";
        boolean isDueDate = false;
        int index = 1;
        while(index < taskSplit.length) {
            if(taskSplit[index].equals("/by")) {
                isDueDate = true;
                index++;
                continue;
            }

            if(isDueDate) {
                dueDate += taskSplit[index];
                dueDate += " ";
            }
            else {
                taskDescription += taskSplit[index];
                taskDescription += " ";
            }
            index++;
        }
        if (taskDescription.isEmpty()){
            throw new IllegalArgumentException("Task Description for task is empty :(");
        } else if (dueDate.isEmpty()) {
            throw new IllegalArgumentException("Due Date for task is empty :(");
        }
        return new Deadline(taskDescription, dueDate);
    }
}

