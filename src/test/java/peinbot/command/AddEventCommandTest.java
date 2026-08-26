package peinbot.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddEventCommandTest {

    @Test
    public void parseParams_noTaskDescription_exceptionThrown() {
        String input = "/from 01-01-2026 /to 01-01-2027";
        String[] splitInput = input.split(" ");
        try {
            new AddEventCommand(splitInput);
            fail();
        } catch (Exception e) {
            assertEquals("Please provide the correct arguments for Event!", e.getMessage());
        }
    }
}
