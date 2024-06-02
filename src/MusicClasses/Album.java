package MusicClasses;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Album extends SongList implements Serializable {
    private String artist;
    private String coverFilePath;
    private String id = UUID.randomUUID().toString();

    //region Getters and setters
    /**
     * Sets the artist name.
     * @param artist the artist name
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Sets the path to the file where the album is stored.
     * @param filePath path to the file where the album is stored
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the artist name.
     * @return the artist name
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets the path to the file where the cover image is stored.
     * @param coverFilePath path to the file where the cover image is stored
     */
    public void setCoverFilePath(String coverFilePath) {
        this.coverFilePath = coverFilePath;
    }

    /**
     * Returns the path to the file where the cover image is stored.
     * @return path to the file where the cover image is stored
     */
    public String getCoverFilePath() {
        return coverFilePath;
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

    /**
     * Returns a String in this format: ARTIST - TITLE
     * @return human-readable representation of the album
     */
    @Override
    public String toString() {
        return artist + " - " + title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(artist, album.artist) && Objects.equals(title, album.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, title);
    }

    /**
     * Returns the album UUID. Important for HashMaps.
     * @return album UUID
     */
    public String getId() {
        return id;
    }
}
