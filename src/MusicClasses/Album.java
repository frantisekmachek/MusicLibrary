package MusicClasses;

import javax.swing.*;

public class Album extends SongList {
    private String artist;
    private ImageIcon cover;

    //region Getters and setters
    /**
     * Sets the artist name.
     * @param artist the artist name
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Returns the artist name.
     * @return the artist name
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets the cover of the album.
     * @param cover the new album cover
     */
    public void setCover(ImageIcon cover) {
        this.cover = cover;
    }

    /**
     * Returns the album cover.
     * @return the album cover
     */
    public ImageIcon getCover() {
        return cover;
    }
    //endregion

    /**
     * An Album constructor.
     *
     * @param title the title of the album
     */
    public Album(String title, String artist) {
        super(title);
        this.artist = artist;
    }
}
