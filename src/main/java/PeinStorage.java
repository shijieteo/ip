import java.io.*;

import java.util.ArrayList;

public class PeinStorage {
    private static final String FILE_LOCATION = "data/Tasks.ser";
    private static final String DIRECTORY_NAME = "data";

    PeinStorage() {}

    public ArrayList<Task> loadData() throws ClassNotFoundException, IOException {
        ArrayList<Task> loadedTasks = new ArrayList<Task>();
        try (FileInputStream fileInputStream = new FileInputStream(FILE_LOCATION);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            loadedTasks = readFile(objectInputStream);

        } catch(FileNotFoundException fileNotFoundException){
            createDataFile();
        } catch(EOFException exception) {
            return loadedTasks;
        }
        return loadedTasks;
    }

    public void writeData(ArrayList<Task> taskList) throws IOException {
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
        if(!directory.exists()) {
            boolean isDirectoryCreated = directory.mkdirs();
        }

        try {
            boolean isFileCreated = dataFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<Task> readFile (ObjectInputStream objectInputStream)
            throws ClassNotFoundException, IOException {
        ArrayList<Task> taskList = (ArrayList<Task>) objectInputStream.readObject();
        return taskList;
    }

}
