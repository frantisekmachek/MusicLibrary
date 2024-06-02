package MusicClasses;

import UtilityClasses.FileLoader;
import java.io.File;
import java.io.Serializable;
import java.util.Objects;

public class Song implements Serializable {
    private String title;
    private String artist;
    private Album album;
    private String filePath;

    //region Getters and setters
    public Album getAlbum() {
        return album;
    }

    /**
     * Sets the song's album and automatically removes or adds itself to it.
     * @param album the new album
     */
    public void setAlbum(Album album) {
        Album oldAlbum = this.album;
        this.album = album;
        if(oldAlbum != null) {
            oldAlbum.removeSong(this);
        }
        if(album != null) {
            album.addSong(this);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    //endregion

    /**
     * A private constructor which prevents normal instance creation (must be created via the 'create' method).
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
     * @throws Exception wrong file provided
     */
    public static Song create(String title, String artist, String filePath) throws Exception {
        boolean isValid = isFileValid(filePath);
        if(!isValid) {
            String print = String.format("Song with title %s wasn't created, because the file isn't valid.");
            System.out.println(print);
            throw new Exception("The file provided is not a sound file.");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(filePath, song.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath);
    }
}
