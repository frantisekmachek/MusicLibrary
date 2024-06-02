package UserInterface.Dialogs;

import MusicClasses.Album;
import MusicClasses.Library;
import MusicClasses.Song;
import UserInterface.Buttons.SongSaveButton;
import UserInterface.Panels.Songs.SongPanel;
import UserInterface.UserInterface;

import javax.swing.*;

/**
 * Dialog which allows the user to change a song's attributes.
 */
public class SongDialog extends BasicDialog {
    private Song song;
    private String filePath;
    private SongPanel songPanel;
    private JTextField songNameField;
    private JTextField artistNameField;
    private JComboBox<String> albumComboBox;

    /**
     * Constructor called when a song is being edited.
     * @param window main app window
     * @param songPanel song panel of song being edited
     * @param song song being edited
     */
    public SongDialog(JFrame window, SongPanel songPanel, Song song) {
        super(window, "Song details", 4, 200);
        this.song = song;
        this.songPanel = songPanel;
        preloadValues();
        setVisible(true);
    }

    /**
     * Constructor called when a song is being created.
     * @param window main app window
     * @param filePath file path of the new imported song
     */
    public SongDialog(JFrame window, String filePath) {
        super(window, "Song details", 4, 200);
        this.filePath = filePath;
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

        String albumChoice = (String)albumComboBox.getSelectedItem();
        Album album = Library.getInstance().getAlbumFromString(albumChoice);

        if(song != null) {
            song.setTitle(title);
            song.setArtist(artist);
            song.setAlbum(album);

            songPanel.updateCover(album);
            songPanel.updateLabels(title, artist);
            UserInterface.getInstance().updatePlaybackLabels();
            Library.getInstance().updateSong(song);
        } else {
            try {
                song = Song.create(title, artist, filePath);
                song.setAlbum(album);
                Library.getInstance().addNewSong(song);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(UserInterface.getInstance().getWindow(), "Song couldn't be imported.");
            }
        }
        return true;
    }

    @Override
    protected void loadContent() {
        JLabel songNameLabel = new JLabel("Song Name:");
        songNameField = new JTextField();

        JLabel artistNameLabel = new JLabel("Artist Name:");
        artistNameField = new JTextField();

        JLabel albumLabel = new JLabel("Album:");
        albumComboBox = loadAlbumComboBox();

        SongSaveButton saveButton = new SongSaveButton(this);

        panel.add(songNameLabel);
        panel.add(songNameField);
        panel.add(artistNameLabel);
        panel.add(artistNameField);
        panel.add(albumLabel);
        panel.add(albumComboBox);
        panel.add(new JLabel());  // Empty cell
        panel.add(saveButton);
    }

    @Override
    protected void preloadValues() {
        if(song != null) {
            songNameField.setText(song.getTitle());
            artistNameField.setText(song.getArtist());
            Album currentAlbum = song.getAlbum();
            if(currentAlbum != null) {
                String albumString = currentAlbum.toString();
                albumComboBox.setSelectedItem(albumString);
            }
        }
    }

    /**
     * Checks if the dialog user input is valid.
     * @return true if valid, false otherwise
     */
    protected boolean inputIsValid() {
        String title = songNameField.getText().trim();
        String artist = artistNameField.getText().trim();
        return (title != null && !title.isEmpty() && !title.isBlank() && artist != null && !artist.isEmpty() && !artist.isBlank());
    }

    /**
     * Loads the options in the album combo box.
     * @return album options
     */
    private String[] loadAlbumOptions() {
        String[] albumStrings = Library.getInstance().getAlbumStrings();
        String[] options = new String[albumStrings.length + 1];

        options[0] = "None"; // First option is always "None"

        for(int i = 1; i < options.length; i++) {
            options[i] = albumStrings[i - 1];
        }

        return options;
    }

    /**
     * Loads the combo box of album options.
     * @return album combo box
     */
    private JComboBox<String> loadAlbumComboBox() {
        String[] options = loadAlbumOptions();
        JComboBox<String> comboBox = new JComboBox<>(options);
        return comboBox;
    }
}
