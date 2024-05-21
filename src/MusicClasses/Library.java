package MusicClasses;

import UserInterface.UserInterface;
import UtilityClasses.FileLoader;
import UtilityClasses.Serializer;

import java.util.HashSet;

/**
 * Contains all songs, albums and song lists and allows for simple manipulation
 * with those objects.
 */
public class Library {
    private static Library instance;
    private SongList allSongs = new SongList("All Songs");
    private HashSet<Album> albums = new HashSet<>();
    private HashSet<SongList> songLists = new HashSet<>();
    private Serializer<Song> songSer = new Serializer<>();

    private Library() {
        loadAllSongs();
    }

    /**
     * Returns the singleton instance of Library. It is initialized if it doesn't exist yet.
     * @return instance of Library
     */
    public static Library getInstance() {
        if(instance == null) {
            instance = new Library();
        }
        return instance;
    }

    /**
     * Loads all songs from the resources.
     */
    private void loadAllSongs() {
        HashSet<Song> songs = FileLoader.loadSongsFromResources();
        for(Song song : songs) {
            allSongs.addSong(song);
        }
    }

    /**
     * Getter for the list of all songs.
     * @return list of all songs
     */
    public SongList getAllSongs() {
        return allSongs;
    }

    /**
     * Adds a new song to the library as well as the files.
     * @param song song to be added
     */
    public void addNewSong(Song song) {
        allSongs.addSong(song);
        UserInterface.getInstance().createSongElement(song); // create UI component for song
        FileLoader.saveSong(song);
    }

    /**
     * Removes a song from the library as well as the files.
      * @param song song to be removed
     */
    public void removeSong(Song song) {
        allSongs.removeSong(song);
        UserInterface.getInstance().removeSongElement(song);
    }

    /**
     * Saves the song via the FileLoader class.
     * @param song song to be saved
     */
    public void updateSong(Song song) {
        FileLoader.saveSong(song);
    }
}
