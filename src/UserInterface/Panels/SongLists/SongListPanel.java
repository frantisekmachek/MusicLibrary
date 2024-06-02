package UserInterface.Panels.SongLists;

import MusicClasses.Song;
import MusicClasses.SongList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a song list where you can see all the songs belonging to it.
 */
public class SongListPanel extends JPanel {
    private Dimension size = new Dimension(550, 0);
    private SongList songList;
    private HashMap<Song, ListItemPanel> songPanels = new HashMap<>();
    private JLabel titleLabel;

    public SongListPanel(SongList songList) {
        super();
        this.songList = songList;
        loadProperties();
        loadTitleLabel();
        loadListSongs();
    }

    /**
     * Loads some of the panel properties.
     */
    private void loadProperties() {
        setPreferredSize(size);
        setBackground(new Color(79, 79, 79));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Loads the title label.
     */
    private void loadTitleLabel() {
        String title = songList.getTitle();

        JPanel titleWrapper = new JPanel();
        titleWrapper.setLayout(new BoxLayout(titleWrapper, BoxLayout.X_AXIS));
        titleWrapper.setBackground(new Color(0,0,0,0));

        Dimension wrapperSize = new Dimension(size.width, 60);
        titleWrapper.setPreferredSize(wrapperSize);
        titleWrapper.setMaximumSize(wrapperSize);

        titleWrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));

        titleLabel = new JLabel();
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Urbana", Font.BOLD, 30));
        titleLabel.setText(title);

        titleWrapper.add(titleLabel);
        add(titleWrapper);
    }

    /**
     * Loads the songs belonging to the list.
     */
    private void loadListSongs() {
        ArrayList<Song> songs = songList.getSongs();
        for(int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            ListItemPanel songPanel = new ListItemPanel(song);
            songPanels.put(song, songPanel);
            add(songPanel);
        }
    }

    /**
     * Removes a given song from the UI.
     * @param song song to be removed
     */
    public void removeSong(Song song) {
        if(songPanels.containsKey(song)) {
            ListItemPanel songPanel = songPanels.get(song);
            songPanels.remove(song);
            remove(songPanel);
            if(getParent() != null) {
                SongListContainer cont = (SongListContainer) getParent();
                cont.resize();
                getParent().revalidate();
                getParent().repaint();
            }
        }
    }

    /**
     * Adds a given song to the UI.
     * @param song song to be added
     */
    public void addSong(Song song) {
        ListItemPanel panel = new ListItemPanel(song);
        songPanels.put(song, panel);
        add(panel);
        if(getParent() != null) {
            SongListContainer cont = (SongListContainer) getParent();
            cont.resize();
            getParent().revalidate();
            getParent().repaint();
        }
    }

    /**
     * Updates a song's item panel.
     * @param song song being updated
     */
    public void updateSong(Song song) {
        if(songPanels.containsKey(song)) {
            ListItemPanel songPanel = songPanels.get(song);
            songPanel.updateSong();
        }
    }

    /**
     * Updates the song list title label.
     */
    public void updateTitle() {
        titleLabel.setText(songList.getTitle());
        getParent().revalidate();
        getParent().repaint();
    }
}
