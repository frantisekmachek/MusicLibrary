package MusicClasses;

import UserInterface.UserInterface;
import UtilityClasses.FileLoader;
import UtilityClasses.Serializer;
import UtilityClasses.SoundPlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Contains all songs, albums and song lists and allows for simple manipulation
 * with those objects.
 */
public class Library {
    private static Library instance;
    private SongList allSongs = new SongList("All Songs");
    private HashSet<Album> albums = new HashSet<>();
    private HashSet<SongList> songLists = new HashSet<>();
    private LinkedList<Song> songQueue = new LinkedList<>();
    private LinkedList<Song> songHistory = new LinkedList<>();
    private int currentHistoryIndex;

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

    /**
     * Loads all albums from the resources.
     */
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
        removeSongFromQueue(song);
        try {
            SoundPlayer.getInstance().setCurrentSong(null, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
            removeSongsFromAlbum(album);
            System.out.println("Album called " + album.getTitle() + " by " + album.getArtist() + " was removed.");
        }
    }

    /**
     * Removes all songs from an album.
     * @param album album
     */
    private void removeSongsFromAlbum(Album album) {
        for(Song song : allSongs.getSongs()) {
            if(song.getAlbum() != null) {
                if(song.getAlbum().equals(album)) {
                    song.setAlbum(null);
                    FileLoader.saveSong(song);
                    UserInterface.getInstance().updateSongCovers(null);
                }
            }
        }
    }

    /**
     * Adds an album to the library and saves it in a file.
     * @param album the new album
     */
    public void addAlbum(Album album) throws Exception {
        if(!albums.contains(album)) {
            albums.add(album);
            UserInterface.getInstance().createAlbumElement(album);
            FileLoader.saveAlbum(album);
            System.out.println("New album called " + album.getTitle() + " by " + album.getArtist() + " was created.");
        } else {
            throw new Exception("Album couldn't be created, because it already exists.");
        }
    }

    /**
     * Saves the album via the FileLoader class.
     * @param album album to be saved
     */
    public void updateAlbum(Album album) {
        FileLoader.saveAlbum(album);
    }

    /**
     * Adds a song to the queue to be played later.
     * @param song song being added to the queue
     */
    public void addSongToQueue(Song song) {
        if(allSongs.getSongs().contains(song)) {
            songQueue.addLast(song);
        }
        try {
            SoundPlayer soundPlayer = SoundPlayer.getInstance();
            if(soundPlayer.getCurrentSong() == null) {
                Song newSong = getSongInQueue();
                soundPlayer.setCurrentSong(newSong, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a song from the queue. Primarily called when a song is removed from the program
     * to avoid playing songs that no longer exist.
     * @param song song to be removed
     */
    public void removeSongFromQueue(Song song) {
        songQueue.removeIf(element -> element.equals(song));
        songHistory.removeIf(element -> element.equals(song));
    }

    /**
     * Gets the next song in the queue, removes it from the queue and adds it to the history.
     * @return song from queue
     */
    public Song getSongInQueue() {
        if(songQueue.size() > 0) {
            Song song = songQueue.pop();
            addSongToHistory(song);
            return song;
        } else {
            return null;
        }
    }

    /**
     * Adds a song to the playback history. If that song is already in the history, the history is cleared to avoid duplicates.
     * @param song song being added to the history
     */
    public void addSongToHistory(Song song) {
        if(allSongs.getSongs().contains(song)) {
            if(songHistory.contains(song)) {
                System.out.println("History already contains " + song.getTitle());
                clearHistory();
            }
            songHistory.addLast(song);
            int size = songHistory.size();
            currentHistoryIndex = size - 1;
            System.out.println("Added " + song.getTitle() + " to history");
        }
        // Print song history in the console
        System.out.print("Song history: ");
        for(Song hSong : songHistory) {
            System.out.print(hSong.getArtist() + " - " + hSong.getTitle() + "; ");
        }
        System.out.println("");
    }

    /**
     * Clears the playback history.
     */
    public void clearHistory() {
        System.out.println("Clearing history...");
        songHistory.clear();
        currentHistoryIndex = 0;
    }

    /**
     * Returns the next song in the history.
     * @return next song
     */
    public Song getNextSong() {
        if(songHistory.size() > 1 && currentHistoryIndex + 1 < songHistory.size()) {
            currentHistoryIndex++;
            return songHistory.get(currentHistoryIndex);
        }
        return null;
    }

    /*
    Returns the previous song in the history.
     */
    public Song getPreviousSong() {
        if(currentHistoryIndex > 0) {
            currentHistoryIndex--;
            return songHistory.get(currentHistoryIndex);
        }
        return null;
    }
}
