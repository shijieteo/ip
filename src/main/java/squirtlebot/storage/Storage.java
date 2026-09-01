package squirtlebot.storage;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import squirtlebot.task.TaskList;

/**
 * Handles all read-write operations for persistency in changes made
 * Uses <code>data/Tasks.ser</code> as system file for read-write operations
 */
public class Storage {
    private static final String FILE_LOCATION = "data/Tasks.ser";
    private static final String DIRECTORY_NAME = "data";

    /**
     * Constructs a storage object
     */
    public Storage() {}

    /**
     * Creates FileInputStream and ObjectInputStream objects required to read from data file
     *
     * @return List of task objects from data file on the system
     * @throws ClassNotFoundException if {@link squirtlebot.task.Task} subclasses or {@link squirtlebot.task.TaskList}
     *                  could not be found on the classpath
     * @throws IOException if an I/O error was encountered while opening the data file
     */
    public TaskList loadData() throws ClassNotFoundException, IOException {
        TaskList loadedTasks = new TaskList();
        try (FileInputStream fileInputStream = new FileInputStream(FILE_LOCATION);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            loadedTasks = (TaskList) objectInputStream.readObject();
        } catch (FileNotFoundException fileNotFoundException) {
            createDataFile();
        } catch (EOFException exception) {
            return loadedTasks;
        }
        return loadedTasks;
    }

    /**
     * Deletes the data file and creates a new data file
     */
    public void resetData() {
        File dataFile = new File(FILE_LOCATION);
        dataFile.delete();
        createDataFile();
    }

    /**
     * Creates FileOutputStream and ObjectOutputStream objects required to write to data file
     *
     * @param taskList TaskList object to be written to the data file
     * @throws IOException if an I/O error was encountered while writing to the data file
     */
    public void writeData(TaskList taskList) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_LOCATION);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(taskList);

        } catch (FileNotFoundException fileNotFoundException) {
            createDataFile();
            writeData(taskList);
        }
    }

    /**
     * Creates data file at {@link #FILE_LOCATION}
     * Creates data directory if it does not already exist
     */
    private void createDataFile() {
        File dataFile = new File(FILE_LOCATION);
        File directory = new File(DIRECTORY_NAME);
        if (!directory.exists()) {
            boolean isDirectoryCreated = directory.mkdirs();
        }

        try {
            boolean isFileCreated = dataFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
