package squirtlebot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InvalidClassException;
import java.util.ArrayList;

import squirtlebot.storage.Storage;
import squirtlebot.task.TaskList;
import squirtlebot.ui.Ui;

/**
 * Tests command handling through SquirtleBot's public GUI-facing API.
 */
public class SquirtleBotTest {
    private static enum ExceptionType {
        IO_EXCEPTION, CLASS_NOT_FOUND, INVALID_CLASS
    }

    private static class FakeUi extends Ui {
        private ArrayList<String> predeterminedInputs;

        FakeUi(ArrayList<String> predeterminedInputs) {
            super();
            this.predeterminedInputs = predeterminedInputs;
        }

        @Override
        public String readInput() {
            if (predeterminedInputs.isEmpty()) {
                return "";
            }
            return predeterminedInputs.removeFirst();
        }
    }

    private static class FakeStorage extends Storage {
        private final TaskList tasks;
        private final ExceptionType exceptionType;

        FakeStorage(TaskList tasks, ExceptionType exceptionType) {
            this.tasks = tasks;
            this.exceptionType = exceptionType;
        }

        @Override
        public TaskList loadData() throws IOException, ClassNotFoundException {
            switch (exceptionType) {
                case IO_EXCEPTION -> throw new IOException("");
                case CLASS_NOT_FOUND -> throw new ClassNotFoundException("");
                case INVALID_CLASS -> throw new InvalidClassException("");
            }

            return tasks;
        }

        @Override
        public void writeData(TaskList tasks) throws IOException {
            switch (exceptionType) {
                case IO_EXCEPTION -> throw new IOException("");
                case INVALID_CLASS -> throw new InvalidClassException("");
            }
        }
    }

    private SquirtleBot bot;

    @BeforeEach
    public void setUp() {
        bot = new SquirtleBot();
        bot.disableStorage();
    }

    @Test
    public void getResponse_addThenMarkTask_returnsUpdatedTask() {
        CommandResult addResult = bot.getResponse("todo read chapter 1");
        CommandResult markResult = bot.getResponse("mark 1");
        CommandResult listResult = bot.getResponse("list");

        assertFalse(addResult.shouldExit());
        assertEquals("added: [T] [ ] read chapter 1 to your list of tasks\n\tYou now have 1 tasks",
                addResult.message());
        assertFalse(markResult.shouldExit());
        assertEquals("Congrats on completing the following task:\n\t [T] [X] read chapter 1",
                markResult.message());
        assertEquals("1. [T] [X] read chapter 1", listResult.message());
    }

    @Test
    public void getResponse_invalidCommand_returnsErrorMessage() {
        CommandResult result = bot.getResponse("unknown command");

        assertFalse(result.shouldExit());
        assertEquals("Invalid command", result.message());
    }

    @Test
    public void getResponse_bye_returnsExitResult() {
        CommandResult result = bot.getResponse("bye");

        assertTrue(result.shouldExit());
        assertEquals("Bye. Hope to see you soon :(", result.message());
    }
}
