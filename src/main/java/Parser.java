import java.util.HashMap;
import java.util.stream.IntStream;

public class Parser {
    Parser(){}

    public HashMap<String, String> processInput(String userInput) {
        HashMap<String, String> inputParams = new HashMap<String, String>();
        String[] userInputArray = userInput.split(" ");

        String operation = userInputArray[0];
        inputParams.put("command", operation);

        switch(operation) {
            case "todo":
                parseToDo(userInputArray, inputParams);
                break;

            case "deadline":
                parseDeadline(userInputArray, inputParams);
                break;

            case "event":
                parseEvent(userInputArray, inputParams);
                break;

            case "mark":
            case "unmark":
            case "delete":
                parseIndex(userInputArray, inputParams);
                break;

            default:
                break;
        }


        return inputParams;
    }

    private void parseToDo(String[] userInputArray, HashMap<String, String> inputParams){
        String taskDescription = IntStream.range(1, userInputArray.length).boxed()
                .map(x -> userInputArray[x]).reduce("", (x,y) -> x + " " + y);

        inputParams.put("description", taskDescription);
    }

    private void parseDeadline(String[] userInputArray, HashMap<String, String> inputParams){
        String dueDate = "";
        String taskDescription = "";
        boolean isDueDate = false;
        int index = 1;
        while(index < userInputArray.length) {
            if(userInputArray[index].equals("/by")) {
                isDueDate = true;
                index++;
                continue;
            }

            if(isDueDate) {
                dueDate += userInputArray[index];
                dueDate += " ";
            }
            else {
                taskDescription += userInputArray[index];
                taskDescription += " ";
            }
            index++;
        }
        inputParams.put("description", taskDescription);
        inputParams.put("due", dueDate);
    }

    private void parseEvent(String[] userInputArray, HashMap<String, String> inputParams){
        boolean isEndDate = false;
        boolean isStartDate = false;
        String startDate = "";
        String endDate = "";
        int index = 1;
        String taskDescription = "";

        while(index < userInputArray.length) {
            if(userInputArray[index].equals("/from")) {
                isStartDate = true;
                isEndDate = false;
                index++;
                continue;
            } else if (userInputArray[index].equals("/to")) {
                isEndDate = true;
                isStartDate = false;
                index++;
                continue;
            }
            if(isEndDate) {
                endDate += userInputArray[index];
                endDate += " ";
            } else if (isStartDate) {
                startDate += userInputArray[index];
                startDate += " ";
            } else {
                taskDescription += userInputArray[index];
                taskDescription += " ";
            }
            index++;
        }
        inputParams.put("description", taskDescription);
        inputParams.put("start", startDate);
        inputParams.put("end", endDate);
    }

    private void parseIndex(String[] userInputArray, HashMap<String, String> inputParams){
        inputParams.put("index", userInputArray[1]);
    }
}
