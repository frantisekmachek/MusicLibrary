package MusicClasses;

import UserInterface.UserInterface;
import UtilityClasses.FileLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class SongList implements Serializable {
    protected String title;
    protected ArrayList<Song> songs = new ArrayList<>();
    protected String filePath;

    //region Getters and setters
    /**
     * Returns the song list title.
     * @return the song list title
     */
    public String getTitle() {
        return title;
    }

    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the song list title.
     * @param title the new song list title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the list of songs.
     * @return the list of songs
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }
    //endregion

    /**
     * A SongList constructor.
     * @param title the title of the song list
     */
    public SongList(String title) {
        this.title = title;
    }

    /**
     * Adds a song to the list if it isn't in the list yet.
     * @param song the song being added
     * @return true if the song was added, false if not
     */
    public boolean addSong(Song song) {
        if(!songs.contains(song)) {
            songs.add(song);
            FileLoader.saveLibrary();
            if(this instanceof Album) {
                UserInterface.getInstance().addSongToAlbumPanel(song);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a song from the list if it's part of the list.
     * @param song the song being removed
     * @return true if the song was removed, false if not
     */
    public boolean removeSong(Song song) {
        if(songs.contains(song)) {
            songs.remove(song);
            if(this instanceof Album) {
                FileLoader.saveLibrary();
                // Remove the song from this album
                UserInterface.getInstance().removeSongFromAlbumPanel(song, (Album)this);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Shuffles the song list.
     */
    public void shuffle() {
        Collections.shuffle(songs);
    }
}
