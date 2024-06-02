package UserInterface.Panels;

import MusicClasses.Song;
import UserInterface.Player.ControlLabels.BackwardButton;
import UserInterface.Player.ControlLabels.ForwardButton;
import UserInterface.Player.ControlLabels.PlayButton;
import UserInterface.Player.Sliders.PlaybackSlider;
import UtilityClasses.SoundPlayer;

import javax.swing.*;
import java.awt.*;

/**
 * The panel at the bottom of the window where you can find the playback buttons, slider, current song name etc.
 */
public class PlaybackControlPanel extends JPanel {
    private PlayButton playButton;
    private ForwardButton forwardButton;
    private BackwardButton backwardButton;
    private PlaybackSlider slider;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel centerPanel;
    private JLabel songTitleLabel;
    private JLabel artistLabel;

    public PlaybackControlPanel() {
        super();
        loadProperties();
        loadContent();
    }

    /**
     * Loads some of the panel properties.
     */
    private void loadProperties() {
        setPreferredSize(new Dimension(550, 70));
        setBounds(0,530,550, 70);
        setBackground(new Color(156, 156, 156));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    /**
     * Loads the panel content.
     */
    private void loadContent() {
        loadLeftPanel();
        loadCenterPanel();
        loadRightPanel();
    }

    /**
     * Loads the left panel where you can see the current song title and artist name.
     */
    private void loadLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0,0,0,0));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(150,70));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        loadLabels();
        add(leftPanel);
    }

    /**
     * Loads the labels on the left panel.
     */
    private void loadLabels() {
        songTitleLabel = new JLabel();
        songTitleLabel.setText("Song name");
        songTitleLabel.setForeground(Color.WHITE);
        songTitleLabel.setFont(new Font("Urbana", Font.BOLD, 14));

        artistLabel = new JLabel();
        artistLabel.setText("Artist");
        artistLabel.setForeground(new Color(220, 220, 220));
        artistLabel.setFont(new Font("Urbana", Font.BOLD, 10));

        leftPanel.add(songTitleLabel);
        leftPanel.add(artistLabel);

        songTitleLabel.setVisible(false);
        artistLabel.setVisible(false);
    }

    /**
     * Loads the right panel. Nothing there currently.
     */
    private void loadRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setBackground(new Color(0,0,0,0));
        rightPanel.setPreferredSize(new Dimension(150,70));

        add(rightPanel);
    }

    /**
     * Loads the panel in the center where you can find the playback buttons and slider.
     */
    private void loadCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(0,0,0,0));
        centerPanel.setPreferredSize(new Dimension(250,70));
        loadButtonPanel();

        slider = new PlaybackSlider();
        centerPanel.add(slider);

        add(centerPanel);
    }

    /**
     * Loads the panel which contains the playback buttons.
     */
    private void loadButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0,0,0,0));

        backwardButton = new BackwardButton();
        buttonPanel.add(backwardButton);

        buttonPanel.add(Box.createRigidArea(new Dimension(2, 0)));

        playButton = new PlayButton();
        buttonPanel.add(playButton);

        buttonPanel.add(Box.createRigidArea(new Dimension(2, 0)));

        forwardButton = new ForwardButton();
        buttonPanel.add(forwardButton);

        centerPanel.add(buttonPanel);
    }

    /**
     * Returns the playback button.
     * @return playback button
     */
    public PlayButton getPlayButton() {
        return playButton;
    }

    /**
     * Returns the slider.
     * @return slider
     */
    public PlaybackSlider getSlider() {
        return slider;
    }

    /**
     * Updates the labels to correctly display the current song.
     */
    public void updateLabels() {
        try {
            SoundPlayer player = SoundPlayer.getInstance();
            Song song = player.getCurrentSong();
            if(song != null) {
                songTitleLabel.setText(song.getTitle());
                artistLabel.setText(song.getArtist());
                songTitleLabel.setVisible(true);
                artistLabel.setVisible(true);
            } else {
                songTitleLabel.setVisible(false);
                artistLabel.setVisible(false);
            }
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
