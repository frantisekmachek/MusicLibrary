package UserInterface.Panels.Songs;

import javax.swing.*;
import java.awt.*;

/**
 * Panel which contains a title and artist label.
 */
public class SongInfoPanel extends JPanel {
    private JLabel titleLabel;
    private JLabel artistLabel;

    public SongInfoPanel(String title, String artist) {
        super();
        loadProperties();
        loadLabels(title, artist);
    }

    /**
     * Loads some of the panel properties.
     */
    private void loadProperties() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0,0,0,0));
    }

    /**
     * Loads the title and artist label.
     * @param title song title
     * @param artist song artist
     */
    private void loadLabels(String title, String artist) {
        titleLabel = new JLabel();
        titleLabel.setText(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Urbana", Font.BOLD, 16));
        add(titleLabel);

        artistLabel = new JLabel();
        artistLabel.setText(artist);
        artistLabel.setForeground(new Color(199, 199, 199));
        artistLabel.setFont(new Font("Urbana", Font.BOLD, 12));
        add(artistLabel);
    }

    /**
     * Updates the title and artist labels.
     * @param title new title
     * @param artist new artist
     */
    public void updateLabels(String title, String artist) {
        titleLabel.setText(title);
        artistLabel.setText(artist);
    }

}
