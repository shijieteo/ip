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
    private static final String DEFAULT_FILE_LOCATION = "data/Tasks.ser";

    private boolean isDisabled;
    private final String fileLocation;
    private final String directoryName;

    /**
     * Constructs a storage object using default file locations
     */
    public Storage() {
        this(DEFAULT_FILE_LOCATION);
    }

    /**
     * Constructs a storage object that persists data at the specified location.
     *
     * @param fileLocation path of the file used to store tasks
     */
    public Storage(String fileLocation) {
        isDisabled = false;
        this.fileLocation = fileLocation;
        File parentDirectory = new File(fileLocation).getParentFile();
        this.directoryName = parentDirectory == null ? "." : parentDirectory.getPath();
    }

    /**
     * Creates FileInputStream and ObjectInputStream objects required to read from data file
     *
     * @return List of task objects from data file on the system
     * @throws ClassNotFoundException if {@link squirtlebot.task.Task} subclasses or {@link squirtlebot.task.TaskList}
     *                  could not be found on the classpath
     * @throws IOException if an I/O error was encountered while opening the data file
     */
    public TaskList loadData() throws ClassNotFoundException, IOException {
        assert !isDisabled;
        TaskList loadedTasks = new TaskList();
        try (FileInputStream fileInputStream = new FileInputStream(fileLocation);
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
        if (isDisabled) {
            return;
        }
        File dataFile = new File(fileLocation);
        dataFile.delete();
        createDataFile();
    }

    /**
     * Sets the value of isDisabled to disable all storage-related operations
     */
    public void disable() {
        this.isDisabled = true;
    }

    /**
     * Creates FileOutputStream and ObjectOutputStream objects required to write to data file
     *
     * @param taskList TaskList object to be written to the data file
     * @throws IOException if an I/O error was encountered while writing to the data file
     */
    public void writeData(TaskList taskList) throws IOException {
        if (isDisabled) {
            return;
        }
        createDataFile();

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileLocation);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(taskList);
        }
    }

    /**
     * Creates the configured data file
     * Creates data directory if it does not already exist
     */
    private void createDataFile() {
        if (isDisabled) {
            return;
        }
        File dataFile = new File(fileLocation);
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            dataFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
