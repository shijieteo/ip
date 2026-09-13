package squirtlebot.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import squirtlebot.storage.Storage;
import squirtlebot.task.TaskList;
import squirtlebot.task.ToDo;
import squirtlebot.ui.Ui;

/**
 * Tests commands that mark, unmark, and delete existing tasks.
 */
public class TaskMutationCommandTest {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        storage.disable();
        tasks = new TaskList();
        tasks.add(new ToDo("first task"));
        tasks.add(new ToDo("second task"));
        ui = new Ui();
    }

    @Test
    public void mark_execute_marksSelectedOneBasedIndex() {
        new MarkCommand(new String[]{"mark", "2"}).execute(tasks, ui, storage);

        assertFalse(tasks.get(0).isDone());
        assertTrue(tasks.get(1).isDone());
        assertEquals("Congrats on completing the following task:\n\t [T] [X] second task",
                ui.getSavedMessage());
    }

    @Test
    public void unmark_execute_unmarksSelectedTask() {
        tasks.get(0).setIsDone(true);

        new UnmarkCommand(new String[]{"unmark", "1"}).execute(tasks, ui, storage);

        assertFalse(tasks.get(0).isDone());
        assertEquals("The following task was marked as not done:\n\t [T] [ ] first task",
                ui.getSavedMessage());
    }

    @Test
    public void delete_execute_removesSelectedOneBasedIndex() {
        new DeleteCommand(new String[]{"delete", "1"}).execute(tasks, ui, storage);

        assertEquals(1, tasks.size());
        assertEquals(new ToDo("second task"), tasks.get(0));
        assertEquals("The following task was removed:\n\t [T] [ ] first task", ui.getSavedMessage());
    }

    @Test
    public void mark_nonNumericIndex_throwsNumberFormatException() {
        NumberFormatException exception = assertThrows(
                NumberFormatException.class, () -> new MarkCommand(new String[]{"mark", "first"}));

        assertEquals("Please insert a valid index :(", exception.getMessage());
    }

    @Test
    public void unmark_nonNumericIndex_throwsNumberFormatException() {
        NumberFormatException exception = assertThrows(
                NumberFormatException.class, () -> new UnmarkCommand(
                        new String[]{"unmark", "not a number"}));

        assertEquals("Please enter a valid index :(", exception.getMessage());
    }

    @Test
    public void delete_nonNumericIndex_throwsNumberFormatException() {
        NumberFormatException exception = assertThrows(
                NumberFormatException.class, () -> new DeleteCommand(
                        new String[]{"delete", "not a number"}));

        assertEquals("Please enter a valid index :(", exception.getMessage());
    }
    @Test
    public void delete_indexOutsideList_throwsIndexOutOfBoundsException() {
        DeleteCommand command = new DeleteCommand(new String[]{"delete", "3"});

        assertThrows(IndexOutOfBoundsException.class, () -> command.execute(tasks, ui, storage));
        assertEquals(2, tasks.size());
    }
}
