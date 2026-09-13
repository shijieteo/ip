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
            loadCount += 1;
            if (remainingLoadFailures > 0) {
                remainingLoadFailures -= 1;
                switch (exceptionType) {
                    case IO_EXCEPTION -> throw new IOException("");
                    case CLASS_NOT_FOUND -> throw new ClassNotFoundException("");
                    case INVALID_CLASS -> throw new InvalidClassException("");
                    case NONE -> { }
                    default -> throw new AssertionError("Unexpected exception type");
                }
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

    @Test
    public void initializeTasks_loadSucceeds_usesStoredTasks() {
        TaskList storedTasks = new TaskList();
        storedTasks.add(new ToDo("stored task"));
        FakeStorage storage = new FakeStorage(storedTasks, ExceptionType.NONE);
        SquirtleBot testBot = createBot(storage, new FakeUi(new ArrayList<>()));

        assertTrue(testBot.initializeTasks());
        assertEquals("1. [T] [ ] stored task", testBot.getResponse("list").message());
        assertEquals(1, storage.loadCount);
    }

    @Test
    public void initializeTasks_ioException_returnsFalse() {
        FakeStorage storage = new FakeStorage(new TaskList(), ExceptionType.IO_EXCEPTION);
        SquirtleBot testBot = createBot(storage, new FakeUi(new ArrayList<>()));

        assertFalse(testBot.initializeTasks());
        assertEquals(1, storage.loadCount);
        assertEquals(0, storage.resetCount);
    }

    @Test
    public void initializeTasks_classNotFound_returnsFalse() {
        FakeStorage storage = new FakeStorage(new TaskList(), ExceptionType.CLASS_NOT_FOUND);
        SquirtleBot testBot = createBot(storage, new FakeUi(new ArrayList<>()));

        assertFalse(testBot.initializeTasks());
        assertEquals(1, storage.loadCount);
        assertEquals(0, storage.resetCount);
    }

    @Test
    public void initializeTasks_invalidClassThenSuccess_resetsAndRetries() {
        FakeStorage storage = new FakeStorage(new TaskList(), ExceptionType.INVALID_CLASS, 2);
        SquirtleBot testBot = createBot(storage, new FakeUi(new ArrayList<>()));

        assertTrue(testBot.initializeTasks());
        assertEquals(3, storage.loadCount);
        assertEquals(2, storage.resetCount);
    }

    @Test
    public void run_storageLoads_processesCommandsUntilBye() {
        TaskList storedTasks = new TaskList();
        storedTasks.add(new ToDo("stored task"));
        FakeStorage storage = new FakeStorage(storedTasks, ExceptionType.NONE);
        FakeUi ui = new FakeUi(new ArrayList<>(List.of("list", "bye")));
        SquirtleBot testBot = createBot(storage, ui);

        testBot.run();

        assertEquals(1, ui.bannerCount);
        assertEquals(List.of("1. [T] [ ] stored task", "Bye. Hope to see you soon :("),
                ui.printedMessages);
    }

    @Test
    public void run_storageFailsAndUserDeclines_stopsInteraction() {
        FakeStorage storage = new FakeStorage(new TaskList(), ExceptionType.IO_EXCEPTION);
        FakeUi ui = new FakeUi(new ArrayList<>(List.of("N")));
        SquirtleBot testBot = createBot(storage, ui);

        testBot.run();

        assertEquals(1, ui.bannerCount);
        assertFalse(storage.isDisabled);
        assertEquals(List.of(SquirtleBot.STORAGE_ISSUE_PROMPT), ui.printedMessages);
    }

    @Test
    public void run_storageFailsAndUserContinues_disablesStorageAndRuns() {
        FakeStorage storage = new FakeStorage(new TaskList(), ExceptionType.IO_EXCEPTION);
        FakeUi ui = new FakeUi(new ArrayList<>(List.of("Y", "todo new task", "bye")));
        SquirtleBot testBot = createBot(storage, ui);

        testBot.run();

        assertTrue(storage.isDisabled);
        assertEquals(1, storage.writeCount);
        assertEquals(SquirtleBot.STORAGE_ISSUE_PROMPT, ui.printedMessages.get(0));
        assertEquals("added: [T] [ ] new task to your list of tasks\n\tYou now have 1 tasks",
                ui.printedMessages.get(1));
        assertEquals("Bye. Hope to see you soon :(", ui.printedMessages.get(2));
    }

    private SquirtleBot createBot(FakeStorage storage, FakeUi ui) {
        return new SquirtleBot(storage, new TaskList(), new Parser(), ui);
    }
}
