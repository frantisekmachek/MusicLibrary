package MusicClasses;

import UtilityClasses.FileLoader;
import java.io.File;
import java.time.Duration;

public class Song {
    private String title;
    private String artist;
    private String filePath;
    private Duration duration;

    /**
     * A private constructor which prevents normal instance creation (must be created via 'create' method).
     * @param title the song title
     * @param artist the artist name
     * @param filePath the file path
     */
    private Song(String title, String artist, String filePath) {
        this.title = title;
        this.artist = artist;
        this.filePath = filePath;
    }

    /**
     * Tries to create and return a new Song instance. It is created if the file with the given file path is valid (exists and is a sound file).
     * @param title the song title
     * @param artist the artist name
     * @param filePath the file path
     * @return the new Song instance
     */
    public static Song create(String title, String artist, String filePath) {
        boolean isValid = isFileValid(filePath);
        if(!isValid) {
            String print = String.format("Song with title %s wasn't created, because the file isn't valid.");
            System.out.println(print);
            return null;
        }
        return new Song(title, artist, filePath);
    }

    /**
     * Checks if the song file is valid (exists and is a sound file).
     * @param filePath the file path
     * @return true if the file is valid, false if the file isn't valid
     */
    private static boolean isFileValid(String filePath) {
        File file = new File(filePath);
        if(!file.exists()) {
            String print = String.format("File with file path %s doesn't exist.", filePath);
            System.out.println(print);
            return false;
        }

        return FileLoader.isSoundFile(filePath);
    }

}
