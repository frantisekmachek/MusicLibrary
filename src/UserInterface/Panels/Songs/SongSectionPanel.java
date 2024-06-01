package UserInterface.Panels.Songs;

import MusicClasses.Album;
import MusicClasses.Library;
import MusicClasses.Song;
import MusicClasses.SongList;
import UserInterface.Buttons.ImportButton;
import UserInterface.Panels.SectionPanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Panel which displays songs via SongPanel components.
 */
public class SongSectionPanel extends SectionPanel {

    public SongSectionPanel() {
        super();
        loadImportButton();
        loadSongs();
    }

    /**
     * Loads the song import button.
     */
    private void loadImportButton() {
        ImportButton button = new ImportButton();
        add(button);
    }

    /**
     * Loads panels for all songs.
     */
    private void loadSongs() {
        SongList songList = Library.getInstance().getAllSongs();
        ArrayList<Song> songs = songList.getSongs();
        if(songs.size() > 0) {
            for(Song song : songs) {
                createSongPanel(song);
            }
        }
    }

    /**
     * Creates a new panel which represents a given song.
     * @param song the song the new panel represents
     */
    public void createSongPanel(Song song) {
        SongPanel panel = new SongPanel(song, this);
        add(panel);
        revalidate();
        repaint();
    }

    /**
     * Removes the panel of a given song.
     * @param song song being removed
     */
    public void removeSongPanel(Song song) {
        for(Component component : getComponents()) {
            if(component instanceof SongPanel) {
                if(((SongPanel)component).getSong().equals(song)) {
                    this.remove(component);
                    revalidate();
                    repaint();
                }
            }
        }
    }

    /**
     * Updates the covers on song panels so that they correspond with the actual album cover.
     * @param album album whose songs need to be updated
     */
    public void updateSongCovers(Album album) {
        Component[] components = getComponents();
        for(Component component : components) {
            if(component instanceof SongPanel) {
                if(((SongPanel)component).getSong().getAlbum() != null) {
                    if(((SongPanel)component).getSong().getAlbum().equals(album)) {
                        ((SongPanel)component).updateCover(album);
                        repaint();
                    }
                } else {
                    if(album == null) {
                        ((SongPanel)component).updateCover(null);
                        repaint();
                    }
                }
            }
        }
    }

}
