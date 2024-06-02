package UserInterface.Panels.Albums;

import MusicClasses.Album;
import MusicClasses.Library;
import UserInterface.Dialogs.AlbumDialog;
import UserInterface.Panels.ItemPanel;
import UserInterface.UserInterface;
import UtilityClasses.CustomFileFilter;
import UtilityClasses.FileLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

public class AlbumPanel extends ItemPanel {
    private JLabel artistLabel;
    private Album album;

    public AlbumPanel(Album album, AlbumSectionPanel albumSection) {
        super(albumSection);
        this.album = album;
        loadAll();
        addExtraPopupOptions();
    }

    public Album getAlbum() {
        return album;
    }

    protected void loadLabels() {
        titleLabel = new JLabel();
        titleLabel.setText(album.getTitle());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Urbana", Font.BOLD, 16));
        labelPanel.add(titleLabel);

        artistLabel = new JLabel();
        artistLabel.setText(album.getArtist());
        artistLabel.setForeground(new Color(199, 199, 199));
        artistLabel.setFont(new Font("Urbana", Font.BOLD, 12));
        labelPanel.add(artistLabel);
    }

    protected void loadIcon() {
        iconLabel = loadIconLabel();
        updateCover();
        add(iconLabel);
    }

    public void updateLabels(String title, String artist) {
        titleLabel.setText(title);
        artistLabel.setText(artist);
        section.repaint();
    }

    public void updateCover() {
        String coverFilePath = album.getCoverFilePath();
        if(coverFilePath != null) {
            try {
               ImageIcon cover = FileLoader.loadImageFromFile(coverFilePath);
               setIcon(cover);
            } catch (Exception e) {
                loadDefaultIcon();
            }
        } else {
            loadDefaultIcon();
        }
    }

    /**
     * Opens the edit dialog.
     */
    protected void editAction() {
        JFrame mainWindow = UserInterface.getInstance().getWindow();
        AlbumDialog dialog = new AlbumDialog(mainWindow, this, album);
    }

    /**
     * Removes the album.
     */
    protected void removeAction() {
        Library.getInstance().removeAlbum(album);
    }

    @Override
    protected void clickAction() {
        UserInterface.getInstance().openAlbum(album);
    }

    /**
     * Adds some extra popup options (change cover, add song, remove song)
     */
    private void addExtraPopupOptions() {
        JMenuItem changeCover = new JMenuItem("Change cover");
        JMenuItem addSong = new JMenuItem("Add song");
        JMenuItem removeSong = new JMenuItem("Remove song");
        popupMenu.add(changeCover);
        // Add later with functionality:
        // popupMenu.add(addSong);
        // popupMenu.add(removeSong);

        changeCover.addActionListener(e -> changeCover());
        addSong.addActionListener(e -> addSong());
        removeSong.addActionListener(e -> removeSong());
    }

    /**
     * Prompts the user to change the album cover. Opens a file chooser.
     */
    private void changeCover() {
        openCoverFileChooser();
    }

    // TODO: implement this method
    private void addSong() {

    }

    // TODO: implement this method
    private void removeSong() {

    }

    /**
     * Opens a file chooser where the user has to choose an image file (the new album cover).
     * The file is copied to the 'res' directory.
     */
    private void openCoverFileChooser() {
        FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Image Files (*.jpg, *.jpeg, *.png)", "jpg", "jpeg", "png");
        CustomFileFilter filter = new CustomFileFilter(extFilter);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showOpenDialog(UserInterface.getInstance().getWindow());
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getPath();
            try {
                Path newPath = FileLoader.copyCover(filePath);
                album.setCoverFilePath(newPath.toString());
                Library.getInstance().updateAlbum(album);
                updateCover();                                         // Change this panel cover
                UserInterface.getInstance().updateSongCovers(album);   // update covers on songs that belong to this album
                JOptionPane.showMessageDialog(UserInterface.getInstance().getWindow(), "Album cover changed.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(UserInterface.getInstance().getWindow(), "Album cover couldn't be changed.");
                ex.printStackTrace();
            }
        }
    }
}
