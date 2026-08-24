import java.io.*;

import java.util.ArrayList;

public class PeinStorage {
    private static final String FILE_LOCATION = "./data/Task.ser";

    PeinStorage() {}

    public ArrayList<Task> loadData() throws ClassNotFoundException, IOException {
        ArrayList<Task> loadedTasks = new ArrayList<Task>();
        try (FileInputStream fileInputStream = new FileInputStream(FILE_LOCATION);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            loadedTasks = readFile(objectInputStream);

        } catch(FileNotFoundException fileNotFoundException){
            createDataFile();
        }
        return loadedTasks;
    }

    public void writeData(Task task) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_LOCATION);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(task);

        } catch (FileNotFoundException fileNotFoundException) {
            createDataFile();
            writeData(task);
        }
    }

    private void createDataFile()  {
        File dataFile = new File(FILE_LOCATION);
        try {
            boolean isCreated = dataFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<Task> readFile (ObjectInputStream objectInputStream)
            throws ClassNotFoundException, IOException {
        ArrayList<Task> taskList = new ArrayList<Task>();
        while(true) {
            try {
                Task task = (Task) objectInputStream.readObject();
                taskList.add(task);
            } catch (EOFException eofException) {
                break;
            }
        }
        return taskList;
    }

}
