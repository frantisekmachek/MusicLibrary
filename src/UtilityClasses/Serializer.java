package UtilityClasses;

import java.io.*;

/**
 * Allows for simple serialization and deserialization of objects.
 * @param <T> generic type, must implement the Serializable interface
 */
public class Serializer<T extends Serializable> {
    /**
     * Attempts to serialize an object of the generic type in a file at a given file path.
     * @param object the object to be serialized
     * @param filePath the file path
     * @return true if serialization was successful, false otherwise
     */
    public boolean serializeObject(T object, String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(object);
            objOut.close();
            fileOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Attempts to deserialize an object of the generic type from a file at a given file path.
     * @param filePath the file path
     * @return the deserialized object
     */
    public T deserializeObject(String filePath) {
        T object = null;
        if(FileLoader.fileExists(filePath)) {
            try {
                FileInputStream fileIn = new FileInputStream(filePath);
                ObjectInputStream objIn = new ObjectInputStream(fileIn);
                object = (T)objIn.readObject();
                objIn.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return object;
            }
        }
        return object;
    }
}
