package MusicClasses;

import UserInterface.UserInterface;
import UtilityClasses.FileLoader;
import UtilityClasses.SoundPlayer;

import javax.sound.sampled.AudioInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Contains all songs, albums and song lists and allows for simple manipulation
 * with those objects.
 */
public class Library implements Serializable {
    private static Library instance;
    private SongList allSongs = new SongList("All Songs");
    private ArrayList<Album> albums = new ArrayList<>();
    private HashSet<SongList> songLists = new HashSet<>();
    private LinkedList<Song> songQueue = new LinkedList<>();
    private LinkedList<Song> songHistory = new LinkedList<>();
    private int currentHistoryIndex;

    private Library() {
    }

    /**
     * When loading the library file, it is possible that the sound file associated with the song
     * won't be found. If that's the case, the song is removed.
     */
    private void legitimizeSongs() {
        ArrayList<Song> badSongs = new ArrayList<>();
        for(Song song : allSongs.getSongs()) {
            String filePath = song.getFilePath();
            try {
                AudioInputStream sound = FileLoader.loadSoundFromFile(filePath);
            } catch (Exception e) {
                badSongs.add(song);
            }
        }
        for(Song song : badSongs) {
            allSongs.getSongs().remove(song);
        }
    }

    /**
     * When loading the library file, it is possible that some of the songs might've been removed
     * due to not existing anymore. It is important to also remove them from their albums.
     */
    private void legitimizeAlbums() {
        for(Album album : albums) {
            ArrayList<Song> badSongs = new ArrayList<>();
            ArrayList<Song> songs = album.getSongs();
            for(Song song : songs) {
                if(!allSongs.getSongs().contains(song)) {
                    badSongs.add(song);
                }
            }
            for(Song badSong : badSongs) {
                album.getSongs().remove(badSong);
            }
        }
    }

    /**
     * Returns the singleton instance of Library. It is loaded from the files, or initialized if it can't be loaded.
     * @return instance of Library
     */
    public static Library getInstance() {
        if(instance == null) {
            Library loadedLibrary = FileLoader.loadLibrary();
            if(loadedLibrary != null) {
                loadedLibrary.legitimizeSongs();
                loadedLibrary.legitimizeAlbums();
                instance = loadedLibrary;
            } else {
                instance = new Library();
            }
        }
        return instance;
    }

    /**
     * Returns the list of all songs.
     * @return list of all songs
     */
    public SongList getAllSongs() {
        return allSongs;
    }

    /**
     * Returns the ArrayList of all albums.
     * @return ArrayList of all albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * Adds a new song to the library. Also creates the UI component for the song.
     * @param song song to be added
     */
    public void addNewSong(Song song) {
        allSongs.addSong(song);
        UserInterface.getInstance().createSongElement(song); // create UI component for song
        FileLoader.saveLibrary();
        System.out.println("New song called " + song.getTitle() + " by " + song.getArtist() + " was imported.");
    }

    /**
     * Removes a song from the library as well as the files.
     * Also removes the song UI element and removes the song from its album panel.
      * @param song song to be removed
     */
    public void removeSong(Song song) {
        allSongs.removeSong(song);
        UserInterface.getInstance().removeSongElement(song);
        UserInterface.getInstance().removeSongFromAlbumPanel(song, song.getAlbum());
        removeSongFromQueue(song);
        try {
            SoundPlayer.getInstance().setCurrentSong(null, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Song called " + song.getTitle() + " by " + song.getArtist() + " was removed.");
    }

    /**
     * Saves the library and updates the song on its album panel.
     * @param song song to be saved
     */
    public void updateSong(Song song) {
        FileLoader.saveLibrary();
        UserInterface.getInstance().updateSongInAlbumPanel(song);
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
     * Removes an album from the library. Also removes its UI elements.
     * @param album the album to be removed
     */
    public void removeAlbum(Album album) {
        if(albums.contains(album)) {
            albums.remove(album);
            UserInterface.getInstance().removeAlbumElement(album);
            UserInterface.getInstance().removeAlbumPanel(album);
            removeSongsFromAlbum(album);
            System.out.println("Album called " + album.getTitle() + " by " + album.getArtist() + " was removed.");
        } else {
            System.out.println("Album doesn't exist.");
        }
    }

    /**
     * Removes all songs from an album. Also updates the songs' covers to the default.
     * @param album album
     */
    private void removeSongsFromAlbum(Album album) {
        for(Song song : allSongs.getSongs()) {
            if(song.getAlbum() != null) {
                if(song.getAlbum().equals(album)) {
                    song.setAlbum(null);
                    FileLoader.saveLibrary();
                    UserInterface.getInstance().updateSongCovers(null);
                }
            }
        }
    }

    /**
     * Adds an album to the library and the library is saved. Also creates the album UI element.
     * @param album the new album
     */
    public void addAlbum(Album album) throws Exception {
        if(!albums.contains(album)) {
            albums.add(album);
            UserInterface.getInstance().createAlbumElement(album);
            FileLoader.saveLibrary();
            System.out.println("New album called " + album.getTitle() + " by " + album.getArtist() + " was created.");
        } else {
            throw new Exception("Album couldn't be created, because it already exists.");
        }
    }

    /**
     * Updates the title in the album panel. Also saves the library.
     * @param album album to be updated
     */
    public void updateAlbum(Album album) {
        FileLoader.saveLibrary();
        UserInterface.getInstance().updateTitleInAlbumPanel(album);
    }

    /**
     * Adds a song to the queue so it can be played later.
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
                clearHistory();
            }
            songHistory.addLast(song);
            int size = songHistory.size();
            currentHistoryIndex = size - 1;
        }
    }

    /**
     * Clears the playback history.
     */
    public void clearHistory() {
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
