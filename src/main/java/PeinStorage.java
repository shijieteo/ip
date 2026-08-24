import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

import java.util.ArrayList;

public class PeinStorage {
    private static final String FILE_LOCATION = "./data/Task.ser";

    PeinStorage() {}

    public ArrayList<Task> readFromFile() {
        ArrayList<Task> loadedTask = new ArrayList<Task>();

        return loadedTask;
    }
}
