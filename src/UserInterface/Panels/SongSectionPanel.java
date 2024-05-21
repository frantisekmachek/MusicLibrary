package UserInterface.Panels;

import MusicClasses.Library;
import MusicClasses.Song;
import MusicClasses.SongList;
import UserInterface.Buttons.ImportButton;
import UtilityClasses.FileLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

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

}
