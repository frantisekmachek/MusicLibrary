package UserInterface.Panels.Songs;

import MusicClasses.Album;
import MusicClasses.Song;
import UserInterface.Dialogs.SongDialog;
import UserInterface.Panels.ItemPanel;
import UtilityClasses.FileLoader;
import UserInterface.UserInterface;

import javax.swing.*;
import java.awt.*;

/**
 * A panel which shows some information about a song such as the title and artist.
 */
public class SongPanel extends ItemPanel {
    private JLabel artistLabel;
    private Song song;

    public SongPanel(Song song, SongSectionPanel songSection) {
        super(songSection);
        this.song = song;
        loadAll();
    }

    /**
     * Returns the song the panel represents.
     * @return song the panel represents
     */
    public Song getSong() {
        return song;
    }

    /**
     * Loads the song title and artist labels.
     */
    protected void loadLabels() {
        titleLabel = new JLabel();
        titleLabel.setText(song.getTitle());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Urbana", Font.BOLD, 16));
        labelPanel.add(titleLabel);

        artistLabel = new JLabel();
        artistLabel.setText(song.getArtist());
        artistLabel.setForeground(new Color(199, 199, 199));
        artistLabel.setFont(new Font("Urbana", Font.BOLD, 12));
        labelPanel.add(artistLabel);
    }

    /**
     * Loads the song icon. If the song doesn't have one, a default icon is loaded.
     */
    protected void loadIcon() {
        iconLabel = loadIconLabel();

        Album album = song.getAlbum();
        updateCover(album);

        add(iconLabel);
    }

    /**
     * Updates the panel labels.
     * @param title new title
     * @param artist new artist
     */
    public void updateLabels(String title, String artist) {
        titleLabel.setText(title);
        artistLabel.setText(artist);
        section.repaint();
    }

    /**
     * Updates the cover label to show the new album cover.
     * @param album the new album
     */
    public void updateCover(Album album) {
        if(album != null) {
            String coverFilePath = album.getCoverFilePath();
            if(coverFilePath != null) {
                try {
                    ImageIcon cover = FileLoader.loadImageFromFile(coverFilePath);
                    setIcon(cover);
                } catch (Exception e) {
                    loadDefaultIcon();
                }
            } else {
                loadDefaultIcon();
            }
        } else {
            loadDefaultIcon();
        }
    }

    /**
     * Opens the edit dialog.
     */
    protected void editAction() {
        JFrame mainWindow = UserInterface.getInstance().getWindow();
        SongDialog dialog = new SongDialog(mainWindow, SongPanel.this, song);
    }

    /**
     * Removes the song.
     */
    protected void removeAction() {
        FileLoader.removeSong(song);
    }
}