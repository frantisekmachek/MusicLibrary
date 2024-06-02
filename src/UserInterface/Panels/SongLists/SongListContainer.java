package UserInterface.Panels.SongLists;

import javax.swing.*;
import java.awt.*;

public class SongListContainer extends JPanel {
    private Dimension size = new Dimension(550, 530);
    private SongListPanel currentSongList = null;

    public SongListContainer() {
        super();
        loadProperties();
    }

    /**
     * Loads some of the panel properties.
     */
    private void loadProperties() {
        setBackground(new Color(79, 79, 79));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(size);
        setSize(size);
    }

    /**
     * Resizes the panel based on the preferred size of all the components inside the currently
     * selected section.
     */
    public void resize() {
        Component[] components = currentSongList.getComponents();
        int totalSize = 0;
        for (Component component : components) {
            totalSize += (int)component.getPreferredSize().getHeight();
        }
        size = new Dimension(550, totalSize);
        setPreferredSize(size);
    }

    /**
     * Opens a song list panel.
     * @param songListPanel song list panel to be opened
     */
    public void openSongList(SongListPanel songListPanel) {
        if(currentSongList != null) {
            remove(currentSongList);
        }
        if(songListPanel != null) {
            currentSongList = songListPanel;
            add(songListPanel);
        }
        resize();
        revalidate();
        repaint();
    }
}
