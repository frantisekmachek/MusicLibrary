package UserInterface.Panels;

import MusicClasses.Song;
import UserInterface.Buttons.ImportButton;
import UtilityClasses.FileLoader;

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

    // For testing purposes, will be changed later.
    private void loadSongs() {
        HashSet<Song> songs = FileLoader.loadSongsFromResources();
        if(songs.size() > 0) {
            for(Song song : songs) {
                SongPanel panel = new SongPanel(song, this);
                add(panel);
            }
        }
    }

}
