package UserInterface.Dialogs;

import MusicClasses.Album;
import MusicClasses.Library;
import UserInterface.Buttons.AlbumSaveButton;
import UserInterface.Panels.Albums.AlbumPanel;
import UserInterface.UserInterface;

import javax.swing.*;

/**
 * Dialog which allows the user to edit some of the album's properties.
 */
public class AlbumDialog extends BasicDialog {
    private Album album;
    private AlbumPanel albumPanel;
    private JTextField albumTitleField;
    private JTextField artistField;

    public AlbumDialog(JFrame window, AlbumPanel albumPanel, Album album) {
        super(window, "Album details", 3, 150);
        this.album = album;
        this.albumPanel = albumPanel;
        preloadValues();
        setVisible(true);
    }

    public AlbumDialog(JFrame window) {
        super(window, "Album details", 3, 150);
        preloadValues();
        setVisible(true);
    }

    /**
     * Creates or updates the album.
     * @return true if the dialog can be closed, false otherwise (for example when the input was invalid)
     */
    public boolean updateAlbum() {
        String title = albumTitleField.getText().trim();
        String artist = artistField.getText().trim();

        if(!inputIsValid()) {
            JOptionPane.showMessageDialog(UserInterface.getInstance().getWindow(), "Invalid input.");
            return false;
        }

        if(album == null) {
            // Create new album
            Album newAlbum = new Album(title, artist);
            try {
                Library.getInstance().addAlbum(newAlbum);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(UserInterface.getInstance().getWindow(), "Album already exists.");
                return false;
            }
        } else {
            // Update the album
            Album temp = new Album(title, artist);
            if(Library.getInstance().getAlbums().contains(temp)) {
                JOptionPane.showMessageDialog(UserInterface.getInstance().getWindow(), "Album already exists.");
                return false;
            } else {
                album.setTitle(title);
                album.setArtist(artist);
                albumPanel.updateLabels(title, artist);
                albumPanel.updateCover();
                Library.getInstance().updateAlbum(album);
            }
        }

        return true;
    }

    @Override
    protected void loadContent() {
        JLabel albumTitleLabel = new JLabel("Album Title:");
        albumTitleField = new JTextField();

        JLabel artistLabel = new JLabel("Artist Name:");
        artistField = new JTextField();

        AlbumSaveButton saveButton = new AlbumSaveButton(this);

        panel.add(albumTitleLabel);
        panel.add(albumTitleField);
        panel.add(artistLabel);
        panel.add(artistField);
        panel.add(new JLabel());  // Empty cell
        panel.add(saveButton);
    }

    @Override
    protected void preloadValues() {
        if(album != null) {
            albumTitleField.setText(album.getTitle());
            artistField.setText(album.getArtist());
        }
    }

    @Override
    protected boolean inputIsValid() {
        String title = albumTitleField.getText().trim();
        String artist = artistField.getText().trim();
        return (title != null && !title.isEmpty() && !title.isBlank() && artist != null && !artist.isEmpty() && !artist.isBlank());
    }
}
