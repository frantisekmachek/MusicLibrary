package UserInterface.Dialogs;

import MusicClasses.Library;
import MusicClasses.Song;
import UserInterface.Buttons.SongSaveButton;
import UserInterface.Panels.SongPanel;
import UserInterface.UserInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog which allows the user to change a song's attributes.
 */
public class SongDialog extends JDialog {
    private Song song;
    private String filePath;
    private SongPanel songPanel;
    private JTextField songNameField;
    private JTextField artistNameField;

    /**
     * Constructor called when a song is being edited.
     * @param window main app window
     * @param songPanel song panel of song being edited
     * @param song song being edited
     */
    public SongDialog(JFrame window, SongPanel songPanel, Song song) {
        super(window, "Song details", true);
        this.song = song;
        this.songPanel = songPanel;
        load(window);
    }

    /**
     * Constructor called when a song is being created.
     * @param window main app window
     * @param filePath file path of the new imported song
     */
    public SongDialog(JFrame window, String filePath) {
        super(window, "Song details", true);
        this.filePath = filePath;
        load(window);
    }

    /**
     * Loads some dialog properties and components.
     * @param window
     */
    private void load(JFrame window) {
        setSize(350, 200);
        setResizable(false);
        setLocationRelativeTo(window);

        JPanel panel = new JPanel();
        panel.setSize(350, 200);
        panel.setLayout(new GridLayout(3, 2, 0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.setBackground(new Color(37, 37, 37));
        add(panel);

        JLabel songNameLabel = new JLabel("Song Name:");
        songNameLabel.setForeground(Color.WHITE);
        songNameLabel.setFont(new Font("Urbana", Font.BOLD, 16));
        songNameField = new JTextField();
        songNameField.setFont(new Font("Urbana", Font.BOLD, 16));
        JLabel artistNameLabel = new JLabel("Artist Name:");
        artistNameLabel.setForeground(Color.WHITE);
        artistNameLabel.setFont(new Font("Urbana", Font.BOLD, 16));
        artistNameField = new JTextField();
        artistNameField.setFont(new Font("Urbana", Font.BOLD, 16));
        SongSaveButton saveButton = new SongSaveButton(this);

        panel.add(songNameLabel);
        panel.add(songNameField);
        panel.add(artistNameLabel);
        panel.add(artistNameField);
        panel.add(new JLabel());  // Empty cell
        panel.add(saveButton);

        if(song != null) {
            songNameField.setText(song.getTitle());
            artistNameField.setText(song.getArtist());
        }

        setVisible(true);
    }

    /**
     * Updates or creates the song with the user's dialog input.
     * @return true if successful, false otherwise
     */
    public boolean updateSong() {
        String title = songNameField.getText().trim();
        String artist = artistNameField.getText().trim();

        if(!inputIsValid()) {
            JOptionPane.showMessageDialog(UserInterface.getInstance().getWindow(), "Invalid input.");
            return false;
        }

        if(song != null) {
            song.setTitle(title);
            song.setArtist(artist);
            songPanel.updateLabels(title, artist);
            Library.getInstance().updateSong(song);
        } else {
            try {
                song = Song.create(title, artist, filePath);
                Library.getInstance().addNewSong(song);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Checks if the dialog user input is valid.
     * @return true if valid, false otherwise
     */
    private boolean inputIsValid() {
        String title = songNameField.getText().trim();
        String artist = artistNameField.getText().trim();
        return (title != null && !title.isEmpty() && !title.isBlank() && artist != null && !artist.isEmpty() && !artist.isBlank());
    }
}
