package UserInterface;

import MusicClasses.Album;
import MusicClasses.Song;
import MusicClasses.SongList;
import UserInterface.Panels.*;
import UserInterface.Panels.Albums.AlbumSectionPanel;
import UserInterface.Panels.SongLists.SongListContainer;
import UserInterface.Panels.SongLists.SongListPanel;
import UserInterface.Panels.Songs.SongSectionPanel;
import UserInterface.Player.ControlLabels.PlayButton;
import UserInterface.Player.Sliders.PlaybackSlider;
import UtilityClasses.FileLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the user interface the user interacts with while using the application. There
 * can only be one instance of UserInterface at a time.
 */
public class UserInterface {
    private static UserInterface instance;
    private static boolean loaded = false;
    private JFrame window;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private PlaybackControlPanel playbackPanel;
    private JPanel sectionChoicePanel;
    private SectionContainerPanel sectionPanel;
    private CustomVerticalScrollPane sectionScrollPane;
    private ArrayList<SectionPanel> sections = new ArrayList<>();
    private PlayButton playButton;
    private SongListContainer songListContainer;
    private CustomVerticalScrollPane songListScrollPane;
    private HashMap<String, SongListPanel> albumPanels = new HashMap<>();

    /**
     * Starts the UI.
     */
    public void start() {
        loadInterface();
    }

    /**
     * A private constructor which forbids instance creation outside of this class.
     */
    private UserInterface() {
    }

    /**
     * Returns the UserInterface instance. If it hasn't been initialized yet, it is created and returned.
     * @return UserInterface instance
     */
    public static UserInterface getInstance() {
        if(instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }

    /**
     * Loads the UI (window, panels, etc.).
     */
    private void loadInterface() {
        if (!loaded) {
            // UIManager.put( "ScrollBar.maximumThumbSize", new Dimension(15, 100));
            UIManager.put("ScrollBar.width", 15);
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (ClassNotFoundException e) {
                System.err.println("Windows Look and Feel class not found");
                e.printStackTrace();
            } catch (InstantiationException e) {
                System.err.println("Failed to instantiate Windows Look and Feel");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                System.err.println("Illegal access while setting Windows Look and Feel");
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                System.err.println("Windows Look and Feel is not supported on this platform");
                e.printStackTrace();
            }

            loadWindow();
            loadMainPanel();
            loadLeftPanel();
            loadRightPanel();
            window.setVisible(true);
        }
        loaded = true;
    }

    /**
     * Loads the main application window (JFrame).
     */
    private void loadWindow() {
        JFrame window = new JFrame("MusicLibrary");
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.getContentPane().setLayout(null);

        try {
            ImageIcon icon = FileLoader.loadImageFromFile("res\\icons\\disc.png");
            window.setIconImage(icon.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Dimension windowSize = new Dimension(800,600);
        window.getContentPane().setPreferredSize(windowSize);
        window.pack();
        this.window = window;
    }

    /**
     * Loads the main panel, being the main content container.
     */
    private void loadMainPanel() {
        JPanel mainPanel = new JPanel(null);
        mainPanel.setSize(800, 600);
        mainPanel.setBackground(Color.BLACK);
        this.mainPanel = mainPanel;
        window.add(mainPanel);
    }

    /**
     * Loads the left panel where you can see songs, albums and playlists.
     */
    private void loadLeftPanel() {
        JPanel leftPanel = new JPanel(null);
        leftPanel.setPreferredSize(new Dimension(250, 600));
        leftPanel.setBounds(0,0,250, 600);
        this.leftPanel = leftPanel;
        mainPanel.add(leftPanel);

        loadSectionPanel();
        loadSections();
        loadSectionChoicePanel();
        loadSectionScrollPane();
    }

    /**
     * Loads the right panel.
     */
    private void loadRightPanel() {
        JPanel rightPanel = new JPanel(null);
        rightPanel.setPreferredSize(new Dimension(550, 600));
        rightPanel.setBounds(250,0,550, 600);
        rightPanel.setBackground(Color.BLACK);
        this.rightPanel = rightPanel;
        mainPanel.add(rightPanel);

        loadSongListContainer();
        loadPlaybackPanel();
    }

    /**
     * Loads the playback panel.
     */
    private void loadPlaybackPanel() {
        this.playbackPanel = new PlaybackControlPanel();
        rightPanel.add(playbackPanel);
    }

    /**
     * Loads the song list container.
     */
    private void loadSongListContainer() {
        Dimension prefSize = new Dimension(550,530);
        Point location = new Point(0,0);
        songListContainer = new SongListContainer();
        songListScrollPane = new CustomVerticalScrollPane(songListContainer, prefSize, location, 20);

        rightPanel.add(songListScrollPane);
    }

    /**
     * Gets the play button in the playback panel.
     * @return play button
     */
    public PlayButton getPlayButton() {
        return playbackPanel.getPlayButton();
    }

    /**
     * Loads the panel at the top of the left panel where you can navigate between different sections
     * via buttons.
     */
    private void loadSectionChoicePanel() {
        sectionChoicePanel = new SectionChoicePanel(sections);
        leftPanel.add(sectionChoicePanel);
    }

    // Placeholder section loader method, to be reworked
    private void loadSections() {
        SongSectionPanel songSection = new SongSectionPanel();
        AlbumSectionPanel albumSection = new AlbumSectionPanel();
        SectionPanel section3 = new SectionPanel();
        sections.add(songSection);
        sections.add(albumSection);
        sections.add(section3);
    }

    /**
     * Loads the panel which contains a selected section.
     */
    private void loadSectionPanel() {
        this.sectionPanel = new SectionContainerPanel();
    }

    /**
     * Opens a section on the left panel.
     * @param section the section to be opened
     */
    public void openSection(SectionPanel section) {
        sectionPanel.openSection(section);
    }

    /**
     * Loads the section scroll pane where you can see different songs, albums and playlists.
     */
    private void loadSectionScrollPane() {
        Dimension prefSize = new Dimension(250, 560);
        Point location = new Point(0, 40);
        sectionScrollPane = new CustomVerticalScrollPane(sectionPanel, prefSize, location, 20);
        leftPanel.add(sectionScrollPane);
    }

    /**
     * Returns the main app window.
     * @return main app window
     */
    public JFrame getWindow() {
        return window;
    }

    /**
     * Creates a new panel for a song.
     * @param song the song being added
     */
    public void createSongElement(Song song) {
        if(sections.size() > 0) {
            SongSectionPanel songSectionPanel = (SongSectionPanel)sections.get(0);
            songSectionPanel.createSongPanel(song);
            sectionPanel.resize();
        }
    }

    /**
     * Removes a panel belonging to a given song.
     * @param song the song being removed
     */
    public void removeSongElement(Song song) {
        if(sections.size() > 0) {
            SongSectionPanel songSectionPanel = (SongSectionPanel)sections.get(0);
            songSectionPanel.removeSongPanel(song);
            sectionPanel.resize();
        }
    }

    /**
     * Creates a new panel for an album.
     * @param album the album being added
     */
    public void createAlbumElement(Album album) {
        AlbumSectionPanel albumSectionPanel = (AlbumSectionPanel)sections.get(1);
        albumSectionPanel.createAlbumPanel(album);
        sectionPanel.resize();
    }

    /**
     * Removes a panel belonging to a given album.
     * @param album the album being removed
     */
    public void removeAlbumElement(Album album) {
        AlbumSectionPanel albumSectionPanel = (AlbumSectionPanel)sections.get(1);
        albumSectionPanel.removeAlbumPanel(album);
        sectionPanel.resize();
    }

    /**
     * Updates the song covers in a given album.
     * @param album album
     */
    public void updateSongCovers(Album album) {
        SongSectionPanel songSectionPanel = (SongSectionPanel)sections.get(0);
        songSectionPanel.updateSongCovers(album);
    }

    /**
     * Resizes the cover so that it fits in the label.
     * @param icon cover (icon)
     * @param size desired cover size
     * @return resized cover
     */
    public static ImageIcon resizeIcon(ImageIcon icon, Dimension size) {
        Image originalImage = icon.getImage();
        Image resizedImage = originalImage.getScaledInstance((int)size.getWidth(), (int)size.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }

    /**
     * Opens and album on the song list panel.
     * @param album album to be opened
     */
    public void openAlbum(Album album) {
        SongListPanel albumPanel;
        String id = album.getId();
        if(albumPanels.containsKey(id)) {
            albumPanel = albumPanels.get(id);
        } else {
            albumPanel = new SongListPanel(album);
            albumPanels.put(id, albumPanel);
        }
        songListContainer.openSongList(albumPanel);
    }

    /**
     * Programmatically sets the slider value (not user input).
     * @param value new slider value (double, converted to int inside)
     */
    public void setSliderValue(double value) {
        PlaybackSlider slider = playbackPanel.getSlider();
        slider.setValueProgrammatically((int)value);
    }

    /**
     * Loads the new sound on the slider and also updates labels on the left playback panel.
     */
    public void loadSoundOnSlider() {
        PlaybackSlider slider = playbackPanel.getSlider();
        slider.loadNewSound();
        playbackPanel.updateLabels();
    }

    /**
     * Checks if the playback slider is adjusting.
     * @return true if the slider is adjusting, false otherwise
     */
    public boolean isSliderAdjusting() {
        return playbackPanel.getSlider().getValueIsAdjusting();
    }

    /**
     * Updates the playback panel labels.
     */
    public void updatePlaybackLabels() {
        playbackPanel.updateLabels();
    }

    /**
     * Removes a given song from its album panel.
     * @param song song being removed
     * @param album album where the song is being removed
     */
    public void removeSongFromAlbumPanel(Song song, Album album) {
        SongListPanel panel = getAlbumPanel(album);
        if(panel != null) {
            panel.removeSong(song);
        }
    }

    /**
     * Adds a song to its album panel.
     * @param song song being added
     */
    public void addSongToAlbumPanel(Song song) {
        Album album = song.getAlbum();
        SongListPanel panel = getAlbumPanel(album);
        if(panel != null) {
            panel.addSong(song);
        }
    }

    /**
     * Finds the panel belonging to an album. The panels are stored in a HashMap where the
     * keys are the album UUID and the value is the panel.
     * @param album album
     * @return album panel
     */
    private SongListPanel getAlbumPanel(Album album) {
        if(album != null) {
            if(albumPanels.containsKey(album.getId())) {
                SongListPanel panel = albumPanels.get(album.getId());
                return panel;
            }
        }
        return null;
    }

    /**
     * Removes an album panel.
     * @param album album whose panel is being removed
     */
    public void removeAlbumPanel(Album album) {
        SongListPanel panel = getAlbumPanel(album);
        if(panel != null) {
            albumPanels.remove(album);
            songListContainer.openSongList(null);
        }
    }

    /**
     * Updates a song panel in its album panel.
     * @param song song being updated
     */
    public void updateSongInAlbumPanel(Song song) {
        Album album = song.getAlbum();
        SongListPanel panel = getAlbumPanel(album);
        if(panel != null) {
            panel.updateSong(song);
        }
    }

    /**
     * Updates the title in an album panel.
     * @param album album being updated
     */
    public void updateTitleInAlbumPanel(Album album) {
        SongListPanel panel = getAlbumPanel(album);
        if(panel != null) {
            panel.updateTitle();
        }
    }

}
