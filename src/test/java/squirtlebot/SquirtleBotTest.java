package squirtlebot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import squirtlebot.parser.Parser;
import squirtlebot.storage.Storage;
import squirtlebot.task.TaskList;
import squirtlebot.task.ToDo;
import squirtlebot.ui.Ui;

/**
 * Tests command handling through SquirtleBot's public GUI-facing API.
 */
public class SquirtleBotTest {
    private enum ExceptionType {
        NONE, IO_EXCEPTION, CLASS_NOT_FOUND, INVALID_CLASS
    }

    private static class FakeUi extends Ui {
        private final ArrayList<String> predeterminedInputs;
        private final ArrayList<String> printedMessages = new ArrayList<>();
        private int bannerCount;

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

        @Override
        public void printBanner() {
            bannerCount += 1;
        }

        @Override
        public void printSavedMessage() {
            printedMessages.add(getSavedMessage());
        }
    }

    private static class FakeStorage extends Storage {
        private final TaskList tasks;
        private final ExceptionType exceptionType;
        private int remainingLoadFailures;
        private int loadCount;
        private int resetCount;
        private int writeCount;
        private boolean isDisabled;

        FakeStorage(TaskList tasks, ExceptionType exceptionType) {
            this(tasks, exceptionType, Integer.MAX_VALUE);
        }

        FakeStorage(TaskList tasks, ExceptionType exceptionType, int loadFailures) {
            this.tasks = tasks;
            this.exceptionType = exceptionType;
            this.remainingLoadFailures = loadFailures;
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
            writeCount += 1;
            if (isDisabled) {
                return;
            }
            switch (exceptionType) {
                case IO_EXCEPTION -> throw new IOException("");
                case INVALID_CLASS -> throw new InvalidClassException("");
                case NONE, CLASS_NOT_FOUND -> { }
                default -> throw new AssertionError("Unexpected exception type");
            }
        }

        @Override
        public void resetData() {
            resetCount += 1;
        }

        @Override
        public void disable() {
            isDisabled = true;
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
