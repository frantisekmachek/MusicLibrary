package UserInterface.Panels.Albums;

import MusicClasses.Album;
import MusicClasses.Library;
import UserInterface.Buttons.CreateAlbumButton;
import UserInterface.Panels.SectionPanel;

import java.awt.*;
import java.util.ArrayList;

public class AlbumSectionPanel extends SectionPanel {

    public AlbumSectionPanel() {
        super();
        loadCreateButton();
        loadAlbums();
    }

    /**
     * Loads the album create button.
     */
    private void loadCreateButton() {
        CreateAlbumButton button = new CreateAlbumButton();
        add(button);
    }

    /**
     * Loads panels for all albums.
     */
    private void loadAlbums() {
        ArrayList<Album> albums = Library.getInstance().getAlbums();
        if(albums.size() > 0) {
            for(Album album : albums) {
                createAlbumPanel(album);
            }
        }
    }

    /**
     * Creates a new panel which represents a given album.
     * @param album the album the new panel represents
     */
    public void createAlbumPanel(Album album) {
        AlbumPanel panel = new AlbumPanel(album, this);
        add(panel);
        revalidate();
        repaint();
    }

    /**
     * Removes the panel of a given album.
     * @param album album being removed
     */
    public void removeAlbumPanel(Album album) {
        for(Component component : getComponents()) {
            if(component instanceof AlbumPanel) {
                if(((AlbumPanel)component).getAlbum().equals(album)) {
                    this.remove(component);
                    revalidate();
                    repaint();
                }
            }
        }
    }
}
