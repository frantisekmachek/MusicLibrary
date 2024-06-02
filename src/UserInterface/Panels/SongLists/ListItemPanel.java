package UserInterface.Panels.SongLists;

import MusicClasses.Song;
import UserInterface.Panels.Songs.SongInfoPanel;
import UtilityClasses.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents a song in the song list UI panel.
 */
public class ListItemPanel extends JPanel {
    private SongInfoPanel songInfoPanel;
    private Song song;
    private Color bgColor = new Color(0,0,0,0);
    private Color hoverBgColor = new Color(255, 255, 255, 73);

    public ListItemPanel(Song song) {
        super();
        this.song = song;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(550,60));
        setMinimumSize(new Dimension(550,60));
        setMaximumSize(new Dimension(550,60));
        setBackground(new Color(0,0,0,0));
        loadContent();
        addListeners();
    }

    /**
     * Loads the panel content.
     */
    private void loadContent() {
        add(Box.createHorizontalStrut(20));
        songInfoPanel = new SongInfoPanel(song.getTitle(), song.getArtist());
        add(songInfoPanel);
        revalidate();
        repaint();
    }

    /**
     * Adds the listeners. When entered, the mouse cursor is changed and the background is changed.
     */
    protected void addListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBgColor);
                ((JComponent) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(bgColor);
                ((JComponent) e.getSource()).setCursor(Cursor.getDefaultCursor());
                getParent().repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    SoundPlayer.getInstance().setCurrentSong(song, true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Returns the song this item represents.
     * @return song
     */
    public Song getSong() {
        return song;
    }

    /**
     * Updates the song info.
     */
    public void updateSong() {
        songInfoPanel.updateLabels(song.getTitle(), song.getArtist());
        getParent().repaint();
    }
}
