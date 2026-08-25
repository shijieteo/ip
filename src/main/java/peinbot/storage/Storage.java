package peinbot.storage;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class Storage {
    private static final String FILE_LOCATION = "data/Tasks.ser";
    private static final String DIRECTORY_NAME = "data";

    Storage() {}

    public TaskList loadData() throws ClassNotFoundException, IOException {
        TaskList loadedTasks = new TaskList();
        try (FileInputStream fileInputStream = new FileInputStream(FILE_LOCATION);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            loadedTasks = readFile(objectInputStream);

        } catch (FileNotFoundException fileNotFoundException) {
            createDataFile();
        } catch (EOFException exception) {
            return loadedTasks;
        }
        return loadedTasks;
    }

    public void resetData() {
        File dataFile = new File(FILE_LOCATION);
        dataFile.delete();
        createDataFile();
    }

    public void writeData(TaskList taskList) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_LOCATION);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(taskList);

        } catch (FileNotFoundException fileNotFoundException) {
            createDataFile();
            writeData(taskList);
        }
    }

    private void createDataFile()  {
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

    private TaskList readFile (ObjectInputStream objectInputStream)
            throws ClassNotFoundException, IOException {
        TaskList taskList = (TaskList) objectInputStream.readObject();
        return taskList;
    }

}
