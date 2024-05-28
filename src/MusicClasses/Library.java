package MusicClasses;

import UserInterface.UserInterface;
import UtilityClasses.FileLoader;
import UtilityClasses.Serializer;

import java.util.ArrayList;
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
        loadAllAlbums();
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

    private void loadAllAlbums() {
        HashSet<Album> albums = FileLoader.loadAlbumsFromResources();
        this.albums = albums;
    }

    /**
     * Returns the list of all songs.
     * @return list of all songs
     */
    public SongList getAllSongs() {
        return allSongs;
    }

    /**
     * Returns the HashSet of all albums.
     * @return HashSet of all albums
     */
    public HashSet<Album> getAlbums() {
        return albums;
    }

    /**
     * Adds a new song to the library as well as the files.
     * @param song song to be added
     */
    public void addNewSong(Song song) {
        allSongs.addSong(song);
        UserInterface.getInstance().createSongElement(song); // create UI component for song
        FileLoader.saveSong(song);
        System.out.println("New song called " + song.getTitle() + " by " + song.getArtist() + " was imported.");
    }

    /**
     * Removes a song from the library as well as the files.
      * @param song song to be removed
     */
    public void removeSong(Song song) {
        allSongs.removeSong(song);
        UserInterface.getInstance().removeSongElement(song);
        System.out.println("Song called " + song.getTitle() + " by " + song.getArtist() + " was removed.");
    }

    /**
     * Saves the song via the FileLoader class.
     * @param song song to be saved
     */
    public void updateSong(Song song) {
        FileLoader.saveSong(song);
    }

    /**
     * Generates Album toString() Strings and returns them in an array.
     * @return array of Album Strings
     */
    public String[] getAlbumStrings() {
        ArrayList<String> albumStrings = new ArrayList<>();
        for(Album album : albums) {
            albumStrings.add(album.toString());
        }
        String[] stringArray = new String[albumStrings.size()];
        stringArray = albumStrings.toArray(stringArray);
        return stringArray;
    }

    /**
     * Finds an album which corresponds with the given String.
     * @param string the String
     * @return the album
     */
    public Album getAlbumFromString(String string) { // This method might need a rework
        String[] substrings = string.split(" - ");
        if(substrings.length != 2) {
            return null;
        }
        String artist = substrings[0];
        String title = substrings[1];
        Album tempAlbum = new Album(title,artist);
        for(Album album : albums) {
            if(album.equals(tempAlbum)) {
                return album;
            }
        }
        return null;
    }

    /**
     * Removes an album from the library as well as the files.
     * @param album the album to be removed
     */
    public void removeAlbum(Album album) {
        if(albums.contains(album)) {
            albums.remove(album);
            UserInterface.getInstance().removeAlbumElement(album);
            System.out.println("Album called " + album.getTitle() + " by " + album.getArtist() + " was removed.");
        }
    }

    /**
     * Adds an album to the library and saves it in a file.
     * @param album the new album
     */
    public void addAlbum(Album album) {
        albums.add(album);
        UserInterface.getInstance().createAlbumElement(album);
        FileLoader.saveAlbum(album);
        System.out.println("New album called " + album.getTitle() + " by " + album.getArtist() + " was created.");
    }

    /**
     * Saves the album via the FileLoader class.
     * @param album album to be saved
     */
    public void updateAlbum(Album album) {
        FileLoader.saveAlbum(album);
    }
}
