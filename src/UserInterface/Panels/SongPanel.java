package UserInterface.Panels;

import MusicClasses.Album;
import MusicClasses.Song;
import UserInterface.Dialogs.SongDialog;
import UserInterface.UserInterface;
import UtilityClasses.FileLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A panel which shows some information about a song such as the title and artist.
 */
public class SongPanel extends JPanel {
    private ImageIcon icon;
    private JPanel labelPanel;
    private JLabel titleLabel;
    private JLabel artistLabel;
    private Song song;
    private SongSectionPanel songSection;

    public SongPanel(Song song, SongSectionPanel songSection) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.song = song;
        this.songSection = songSection;
        load();
        loadPopupMenu();
        revalidate();
        repaint();
    }

    /**
     * Loads the panel properties, components and mouse listeners.
     */
    private void load() {
        setPreferredSize(new Dimension(250,60));
        setMaximumSize(new Dimension(250,60));
        setBackground(new Color(0, 0, 0, 0));
        setBorder(null);
        setAlignmentX(Component.LEFT_ALIGNMENT);

        loadIcon();
        loadLabelPanel();
        loadLabels();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(45, 45, 45));
                ((JComponent) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                songSection.revalidate();
                songSection.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(0,0,0,0));
                ((JComponent) e.getSource()).setCursor(Cursor.getDefaultCursor());
                songSection.revalidate();
                songSection.repaint();
            }
        });
    }

    /**
     * Loads the popup menu that appears when you right-click on the panel.
     */
    private void loadPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("Edit");
        JMenuItem menuItem2 = new JMenuItem("Remove");
        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);

        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame mainWindow = UserInterface.getInstance().getWindow();
                SongDialog dialog = new SongDialog(mainWindow, SongPanel.this, song);
            }
        });

        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileLoader.removeSong(song);
            }
        });

        setComponentPopupMenu(popupMenu);
    }

    /**
     * Loads the panel which contains the title and artist labels.
     */
    private void loadLabelPanel() {
        labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBackground(new Color(0,0,0,0));
        add(labelPanel);
    }

    /**
     * Loads the song title and artist labels.
     */
    private void loadLabels() {
        titleLabel = new JLabel();
        titleLabel.setText(song.getTitle());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Urbana", Font.BOLD, 20));
        labelPanel.add(titleLabel);

        artistLabel = new JLabel();
        artistLabel.setText(song.getArtist());
        artistLabel.setForeground(Color.WHITE);
        artistLabel.setFont(new Font("Urbana", Font.BOLD, 12));
        labelPanel.add(artistLabel);
    }

    /**
     * Loads the song icon. If the song doesn't have one, a default icon is loaded.
     */
    private void loadIcon() {
        Album album = song.getAlbum();
        if(album != null) {
            icon = album.getCover();
            if(icon == null) {
                loadDefaultIcon();
            }
        } else {
            loadDefaultIcon();
        }

        Dimension size = new Dimension(50,50);
        ImageIcon resizedIcon = resizeIcon(icon, size);

        JLabel iconLabel = new JLabel(resizedIcon);
        iconLabel.setPreferredSize(size);
        iconLabel.setMaximumSize(size);
        iconLabel.setBorder(new LineBorder(Color.WHITE));
        add(iconLabel);
    }

    /**
     * Resizes the song icon so that it fits the label.
     * @param icon song cover (icon)
     * @param size desired icon size
     * @return resized icon
     */
    private ImageIcon resizeIcon(ImageIcon icon, Dimension size) {
        Image originalImage = icon.getImage();
        Image resizedImage = originalImage.getScaledInstance((int)size.getWidth(), (int)size.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }

    /**
     * Loads the default song cover icon.
     */
    private void loadDefaultIcon() {
        try {
            icon = FileLoader.loadImageFromFile("res\\covers\\placeholder.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the panel labels.
     * @param title new title
     * @param artist new artist
     */
    public void updateLabels(String title, String artist) {
        titleLabel.setText(title);
        artistLabel.setText(artist);
        songSection.revalidate();
        songSection.repaint();
    }

    /**
     * Returns the song the panel represents.
     * @return song the panel represents
     */
    public Song getSong() {
        return song;
    }
}